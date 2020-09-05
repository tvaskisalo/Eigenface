/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;
import eigenface.dao.Dao;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Luokalla voidaan suorittaa eigenface-prosessin eri vaiheita.
 * 
 */
public class UiLogic {
    private File[] files;
    private ImageProcessing imgProcess;
    private MatrixOperations matop;
    private double[][] dataMatrix;
    private double[][] innerproductData;
    private double[] meanface;
    private double[] eigenvalues;
    private double[][] innerEigenvectors;
    private double[][] covEigenvectors;
    private int size;
    private double[][] principalEigenvectors;
    private Dao dao;

    /**
     * Konstuktorille annetaan kuvan koko ja se asettaa tarvittavat muuttujat sen mukaan.
     * @param size Kuvan koko (size x size)
     */
    public UiLogic(int size) {
        this.size = size;
        imgProcess = new ImageProcessing();
        files = imgProcess.getTrainingImages();
        matop = new MatrixOperations();
        dataMatrix = new double[files.length][size * size];
        innerproductData = new double[files.length][files.length];
        dao = new Dao(size);
    }
    
    /**
     * Metodilla voidaan asettaa valmiista .csv tiedostoista ominaisvetorit ja keskiarvo kasvot.Tällöin ei tarvitse generoida aina uudelleen kasvoja.
     * @return Palauttaa kuluneen ajan
     */
    public long useExisting() {
        long start = System.nanoTime();
        principalEigenvectors = dao.readMatrix("eigen");
        meanface = dao.readMatrix("mean")[0];
        long end = System.nanoTime();
        return (end - start);
    }
    
    /**
     * Luokan "pää"-metodi, jonka avulla voidaan suorittaa kaikki eigenfacen vaiheet.
     * @return palauttaa kuluneen ajan nanosekunneissa
     */
    public long generateEigenface() {
        long start = System.nanoTime();
        imageToMatrixProgress();
        meanFaceProgress();
        innerProductProgress();
        eigenvaluesAndVectors();
        normalizeEigenvectorsProcess();
        principalEigenvectorsProcess();
        long end = System.nanoTime();
        dao.writeMatrix("eigen", principalEigenvectors);
        dao.writeMatrix("mean", new double[][] {meanface});
        return (end - start);
    }
    
    
    /**
     * Metodi prosessoi jokaisen kuvan luokan ImageProcessing avulla, sekä muuttaa ne matriisiksi luokan MatixOperations avulla
     */
    private void imageToMatrixProgress() {
        for (int i = 0; i < files.length; i++) {
            //Muutetaan kuva kokoon r x r, sekä mustavalkoiseksi
            BufferedImage image = imgProcess.processImage(files[i], size, size);
            //Muutetaan kuva matriisiksi ja matriisi vektoriksi
            double[][] imageMatrix = imgProcess.imageToMatrix(image);
            double[] vector = matop.reshapeToVectorByRow(imageMatrix);
            //Lisätään vektori osaksi matriisia, jossa on kaikki kuvat
            dataMatrix[i] = vector;
        }
    }
    /**
     * Metodi laskee keskiarvonaaman ja vähentää sen kaikista kuva-vektoreista
     */
    private void meanFaceProgress() {
        meanface = matop.meanOfMatrixByRow(dataMatrix);
        dataMatrix = matop.subtract(dataMatrix, meanface);
    }
    /**
     * Metodi laskee kuvamatriisin transpoosin ja kuvamatriisin tulon ominaisarvot ja -vektorit
     */
    private void eigenvaluesAndVectors() {
        double[][][] values = matop.getEigenpairs(innerproductData, files.length);
        eigenvalues = values[0][0];
        innerEigenvectors = values[1];
        try {
            //Kerrotaan ominaisvektorit alkuperäisellä matriisilla, jotta saadaan kovarianssimatriisin ominaisarvot.
            covEigenvectors = matop.multiply(dataMatrix, innerEigenvectors);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Metodi laskee kuvamatriisin transpoosin ja kuvamatriisin tulon.
     */
    private void innerProductProgress() {
        try {
            innerproductData = matop.multiply(matop.transpose(dataMatrix), dataMatrix);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Metodi normalisoi kaikki covarianssimatriisin ominaisvektorit
     */    
    private void normalizeEigenvectorsProcess() {
        covEigenvectors = matop.normalizeVectors(covEigenvectors);
    }
    
    /**
     * Metodi laskee, kuinka monta ominaisvektoria tarvitaan, jotta se vastaa 95% ominaisarvojen summasta.
     * Tämä tehdään, sillä pienet ominaisarvot ja niiden ominaisvektorit vaikuttavat kasvojentunnistukseen erittäin vähän.
     */
    private void principalEigenvectorsProcess() {
        int count = matop.calculatePrincipal(eigenvalues, 0.95);        
        principalEigenvectors = new double[count][size * size];
        for (int i = 0; i < count; i++) {
            principalEigenvectors[i] = covEigenvectors[i];
        }
    }
    
    /**
     * Metodilla voidaan tutkia onko annetussa kuvavektorissa naama vai ei. 
     * Tämän se tekee käyttämällä faktaa, että yleensä ottaen, jos kuvassa on kasvot, niin siitä tehty matriisi ei muutu paljoa, 
     * kun se projektoidaan "naama"-avaruuten, eli kovarianssimatriisin ominaisavaruuteen.
     * Tällä hetkellä metodin tunnistaa kasvot noin 80% ajasta, kun kuvassa on naama ja
     * tunnistaa noin 30% ajasta, että kuvassa ei ole naamaa, jos siinä ei ole naama.
     * Empiirisen testauksen nojalla, se on oikeassa noin 60-65% ajasta, riipuen harjoitusaineistosta
     * @param eigenFaces Kuvamatriisin kovarianssimatriisin ominaisarvot
     * @param imageVector Tutkittava kuvavektori
     * @param meanFace Keskiverto naamavektori
     * @return Palauttaa kuvavektorin ja sen projektion erotuksen pituuden.
     */
    private double imageIsAFace(double[][] eigenFaces, double[] imageVector, double[] meanFace) {
        try {
            double[] meanAdjustedFace = matop.vectorSubtract(imageVector, meanFace);
            double[] weightVector = matop.multiply(matop.transpose(eigenFaces), meanAdjustedFace);
            double[] sum = new double[meanFace.length];
            for (int i = 0; i < weightVector.length; i++) {
                sum = matop.vectorAdd(sum, matop.vectorMultiply(eigenFaces[i], weightVector[i]));
            }
            return matop.vectorLength(sum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    /**
     * Tunnistaa annetuista kuvista montako kasvoja on.
     * @param faces File-lista, jossa on tunnistettavat kuvat
     * @return Palauttaa listan, jossa on kaikkien tunnistettujen kuvien kasvot.
     */
    public String[] recognizeFaces(File[] faces) {
        File[] detectedFaces = new File[faces.length];
        int index = 0;
        int max = 66;
        for (int i = 0; i < faces.length; i++) {
            File f = faces[i];
            double[] faceVector = matop.reshapeToVectorByRow(imgProcess.imageToMatrix(imgProcess.processImage(f, size, size)));
            double b = imageIsAFace(principalEigenvectors, faceVector, meanface) / size;
            if (b < max) {
                detectedFaces[index] = f;
                index++;
            } 
        }
        String[] returnValue = new String[index];
        for (int j = 0; j < index; j++) {
            returnValue[j] = detectedFaces[j].getName();
        }
        return returnValue;
    }
}
