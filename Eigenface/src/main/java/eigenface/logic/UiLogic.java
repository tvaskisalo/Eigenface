/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Luokalla voidaan suorittaa eigenface-prosessin eri vaiheita, sekä näyttää niitä käyttöliittymälle.
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
    }
    /**
     * Tunnistaa annetuista kuvista montako kasvoja on ja kuinka monta ei kasvoja on.
     * @param faces File-lista, jossa on tunnistettavat kuvat
     * @return Palauttaa listan, jossa ensimmäinen lakio on tunnistettujen naamojen määrä ja toinen on määrä, joita ei tunnistettu kasvoiksi.
     */
    public int[] recognizeFaces(File[] faces) {
        int numberOfFaces = 0;
        int numberOfNotFaces = 0;
        double sum = 0;
        for (int i = 0; i < faces.length; i++) {
            File f = faces[i];
            double[] faceVector = matop.reshapeToVectorByRow(imgProcess.imageToMatrix(imgProcess.processImage(f, size, size)));
            double b = imageIsAFace(principalEigenvectors, faceVector, meanface, 1714433);
            sum += b;
            if (b < 1714433) {
                numberOfFaces++;
            } else {
                numberOfNotFaces++;
            }
        }
        return new int[] {numberOfFaces, numberOfNotFaces};
    }
    /**
     * Luokan "pää"-metodi, jonka avulla voidaan suorittaa kaikki eigenfacen vaiheet.
     * @param info Palauttaa BorderPane-olion, jossa on kyseisestä vaiheesta tietoa.
     */
    public void generateEigenface(BorderPane info) {
        Label processInfo = new Label("Processing...");
        info.setTop(processInfo);
        imageToMatrixProgress();
        meanFaceProgress();
        innerProductProgress();
        eigenvaluesAndVectors();
        principalEigenvectorsProcess();
    }
    /**
     * Metodi prosessoi jokaisen kuvan luokan ImageProcessing avulla, sekä muuttaa ne matriisiksi luokan MatixOperations avulla
     * @param processInfo Metodi muuttaa infoa prosessin mukaan.
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
     * Metodi laskee keskiarvonaaman ja vähentää sen kaikista naama-vektoreista
     * @param processInfo Metodi muuttaa infoa prosessin mukaan.
     */
    private void meanFaceProgress() {
        meanface = matop.meanOfMatrixByRow(dataMatrix);
        dataMatrix = matop.subtract(dataMatrix, meanface);
        double[][] meanf = new double[size][size];
        int row = -1;
        int col = 0;
        for (int i = 0; i < meanface.length; i++) {
            if (i % size == 0) {
                row++;
                col = 0;
            }
            meanf[col][row] = meanface[i];
            col++;
        }
        imgProcess.matrixToImage(meanf, size, size, "meanface");
        
    }
    /**
     * Metodi laskee kuvamatriisin transpoosin ja kuvamatriisin tulon ominaisarvot ja -vektorit
     * @param processInfo Metodi muuttaa infoa prosessin mukaan.
     */
    private void eigenvaluesAndVectors() {
        double[][][] values = matop.getEigenpairs(innerproductData, files.length);
        //Otetaan ominaisarvot diagonaalista.
        eigenvalues = values[0][0];
        innerEigenvectors = values[1];
        try {
            //Kerrotaan ominaisvektorit alkuperäisellä matriisilla, jotta saadaan kovarianssimatriisin ominaisarvot.
            covEigenvectors = matop.multiply(dataMatrix, innerEigenvectors);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
    /**
     * Metodi laskee kuvamatriisin transpoosin ja kuvamatriisin tulon
     * @param processInfo Metodi muuttaa infoa prosessin mukaan.
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
     * @param processInfo Metodi muuttaa infoa prosessin mukaan.
     */    
    private void normalizeEigenvectorsProcess() {
        covEigenvectors = matop.normalizeVectors(covEigenvectors);
    }
    
    /**
     * Metodi järjestää covarianssimatriisin ominaisvektorit vastaamaan niiden ominaisarvojen suuruusjärjestystä laskevasti.
     * @param processInfo Metodi muuttaa infoa prosessin mukaan.
     */
    private void principalEigenvectorsProcess() {
        int count = matop.calculatePrincipal(eigenvalues, 0.95);        
        principalEigenvectors = new double[count][size * size];
        for(int i = 0; i < count; i++ ) {
            principalEigenvectors[i] = covEigenvectors[i];
        }
    }
    
    /**
     * Metodilla voidaan tutkia onko annetussa kuvavektorissa naama vai ei. 
     * Tämän se tekee käyttämällä faktaa, että yleensä ottaen, jos kuvassa on kasvot, niin siitä tehty matriisi ei muutu paljoa, 
     * kun se projektoidaan "naama"-avaruuten kuvamatriisin kovarianssimatriisin ominaisarvojen perusteella.
     * Tällä hetkellä metodin tunnistaa kasvot noin 80% ajasta, kun kuvassa on naama ja
     * tunnistaa noin 70% ajasta, että kuvassa ei ole naamaa, jos siinä ei ole naama silloin, kun threshold on 85 ja kuvankoko on 100x100
     * @param eigenFaces Kuvamatriisin kovarianssimatriisin ominaisarvot
     * @param imageVector Tutkittava kuvavektori
     * @param meanFace Keskiverto naamavektori
     * @param threshold Määrä, jonka perusteella määritellään onko kasvot vai ei. 
     * @return 
     */
    private double imageIsAFace(double[][] eigenFaces, double[] imageVector, double[] meanFace, double threshold) {
        try {
            double[] meanAdjustedFace = matop.vectorSubtract(imageVector, meanFace);
            double[] weightVector = matop.projectionToFace(eigenFaces, meanAdjustedFace);
            double[] subtraction = matop.vectorSubtract(meanAdjustedFace, weightVector);
            double length = matop.vectorLength(subtraction);
            return length;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    
}
