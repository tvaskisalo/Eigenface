/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private double[][] weightVectors;

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
     * Tunnistaa annetuista kuvista montako kasvoja on.
     * @param faces File-lista, jossa on tunnistettavat kuvat
     * @return Palauttaa listan, jossa ensimmäinen lakio on tunnistettujen naamojen määrä ja toinen on määrä, joita ei tunnistettu kasvoiksi.
     */
    public String[] recognizeFaces(File[] faces) {
        File[] detectedFaces = new File[faces.length];
        int index = 0;
        int[] minMaxThresholds = calculateThresholds();
        double[] facess = new double[100];
        double[] others = new double[100];
        int min = 0;
        int max = 6172;
        int sum =0;
        for (int i = 0; i < faces.length; i++) {
            File f = faces[i];
            double[] faceVector = matop.reshapeToVectorByRow(imgProcess.imageToMatrix(imgProcess.processImage(f, size, size)));
            double b = imageIsAFace(principalEigenvectors, weightVectors, faceVector, meanface);
            sum += b;
            if(i==99) {
                System.out.println("avg: "+sum/100);
                sum = 0;
                System.out.println("others:");
            }
            if ( i<100) {
                facess[i] = b;
            } else {
                others[i-100] = b;
            }
            System.out.println(b);
            if (b < max && b > min) {
                detectedFaces[index] = f;
                index++;
            } 
        }
        System.out.println("avg: "+sum/100);
        System.out.println(Arrays.toString(facess));
        System.out.println(Arrays.toString(others));
        String[] returnList = new String[index];
        for (int i = 0; i<faces.length; i++) {
            if(detectedFaces[i] != null) {
                returnList[i] = detectedFaces[i].getName();
            }
        }
        return returnList;
    }
    /**
     * Metodilla voidaan asettaa valmiista .csv tiedostoista ominaisvetorit ja keskiarvo kasvot.Tällöin ei tarvitse generoida aina uudelleen kasvoja.
     * @return Palauttaa kuluneen ajan
     */
    public long useExisting() {
        long start = System.nanoTime();
        principalEigenvectors = readMatrix("eigen");
        meanface = readMatrix("mean")[0];
        long end = System.nanoTime();
        return (end-start);
    }
    /**
     * Metodi kirjoittaa matriisin .csv-tiedostoon
     * @param name Tiedoston nimi. Tämä on kovakoodattu olemaan joko "eigen" tai "mean", viitaten joko ominaisvektoreihin tai keskiarvo kasvoihin.
     * @param matrix Matriisi, joka kirjoitettaan tiedostoksi
     */
    public void writeMatrix(String name, double[][] matrix) {
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("./existingData/" + name + size + ".csv"));
            for (int i = 0; i < matrix[0].length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (j + 1 == matrix.length) {
                        buffWriter.write(matrix[j][i] + "");
                    } else {
                        buffWriter.write(matrix[j][i] + ",");
                    }
                }
                if (i + 1 != matrix[0].length) {
                    buffWriter.newLine();
                }
            }
            buffWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
    }    
    /**
     * Metodi lukee .csv-tiedoston ja palauttaa matriisin.
     * @param name Tiedoston nimi. Tämä on kovakoodattu olemaan joko "eigen" tai "mean", viitaten joko ominaisvektoreihin tai keskiarvo kasvoihin.
     * @return Palauttaa matriisin, jonka alkiot ovat joko ominaisvektorit tai keskiarvo kasvot.
     */
    public double[][] readMatrix(String name) {
        double[][] returnValue = new double[0][0];
        int j = 0;
        try {
            BufferedReader buffReader = Files.newBufferedReader(Paths.get("./existingData/" + name + size + ".csv"), StandardCharsets.US_ASCII);
            String line = buffReader.readLine();
            if (line != null) {
                String[] numbers = line.split(",");
                if (name.equals("mean")) {
                    returnValue = new double[1][size * size];
                } else {
                    returnValue = new double[numbers.length][size * size];
                }
            }
            while (line != null) {
                String[] numbers = line.split(",");
                for (int i = 0; i < numbers.length; i++) {
                    returnValue[i][j] = Double.valueOf(numbers[i]);
                }
                j++;
                line = buffReader.readLine();
            }
            buffReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(name);
        }
        return returnValue;
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
        createWeightVectors(principalEigenvectors);
        long end = System.nanoTime();
        writeMatrix("eigen", principalEigenvectors);
        writeMatrix("mean", new double[][] {meanface});
        return (end - start);
    }
    
    public void createWeightVectors(double[][] eigenvectors) {
        double[][] weights = new double[eigenvectors.length][eigenvectors.length];
        for (int i = 0; i < eigenvectors.length; i++) {
            double[] weigth = new double[eigenvectors.length];
            for (int j = 0; j < eigenvectors.length; j++) {
                try {
                    weigth[j] = matop.dotproduct(eigenvectors[i], eigenvectors[j]);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            weights[i] = weigth;
        }
        weightVectors = weights;
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
    }
    /**
     * Metodi laskee kuvamatriisin transpoosin ja kuvamatriisin tulon ominaisarvot ja -vektorit
     * @param processInfo Metodi muuttaa infoa prosessin mukaan.
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
        for (int i = 0; i < count; i++) {
            principalEigenvectors[i] = covEigenvectors[i];
        }
    }
    
    /**
     * Metodilla voidaan tutkia onko annetussa kuvavektorissa naama vai ei. 
     * Tämän se tekee käyttämällä faktaa, että yleensä ottaen, jos kuvassa on kasvot, niin siitä tehty matriisi ei muutu paljoa, 
     * kun se projektoidaan "naama"-avaruuten kuvamatriisin kovarianssimatriisin ominaisarvojen perusteella.
     * Tällä hetkellä metodin tunnistaa kasvot noin 80% ajasta, kun kuvassa on naama ja
     * tunnistaa noin 70% ajasta, että kuvassa ei ole naamaa, jos siinä ei ole naama silloin.
     * @param eigenFaces Kuvamatriisin kovarianssimatriisin ominaisarvot
     * @param imageVector Tutkittava kuvavektori
     * @param meanFace Keskiverto naamavektori
     * @param threshold Määrä, jonka perusteella määritellään onko kasvot vai ei. 
     * @return 
     */
    private double imageIsAFace(double[][] eigenFaces, double[][] weightVectors, double[] imageVector, double[] meanFace) {
        try {
            double[] meanAdjustedFace = matop.vectorSubtract(imageVector, meanFace);
            double[] weightVector = matop.multiply(matop.transpose(eigenFaces), meanAdjustedFace);
            double[] sum = new double[meanAdjustedFace.length];
            double min = 0;
            for(int i=0; i<weightVector.length; i++) {
               double length = matop.vectorLength(matop.vectorSubtract(weightVector, weightVectors[i]));
               if(min == 0 || length < min) {
                   min = length;
               }
            }
            return min;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    private double[] calculateWeightVector(double[][] eigenFaces, double[] vector) {
        double[] weightVector = new double[eigenFaces.length];
        for (int i = 0; i<eigenFaces.length; i++) {
            try {
                weightVector[i] = matop.dotproduct(eigenFaces[i], vector);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return weightVector;
    }
    
    /**
     * Metodi laskee minimi ja maksimi rajat tunnistettaville kasvoille. Liian suuret ja liian pienet arvot ovat yleensä ottaen muita kuin kasvoja.
     * Repositoriosta löytyy perusteluita, miksi rajat ovat nämä. 
     * @return Palauttaa listan, jossa ensimmäinen alkio on min ja toinen max.
     */
    private int[] calculateThresholds() {
        int min = 8400 * size;
        int max = 36000 * size;
        return new int[] {min, max};
    }
}
