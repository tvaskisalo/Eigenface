/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *  Luokalla voidaan tuoda kuvia kansiosta "./images/training", muuttaa kuvan väriä ja resoluutiota
 *  ja muuttaa kuvat matriiseiksi. Tällä hetkellä se tekee myös kaikkiea sekalaista.
 *  Tämän on tarkoitus muuttua refaktoroinnin yhteydessä
 */
public class ImageProcessing {
    
    /**
     * Tämä metodi tällä hetkellä on niin sanotusti spagettia, jonka avulla olen tarkistanut, että asiat toimivat halutusti.
     * Metodin nimi tulee muuttumaan, sekä se täytyy refaktoroida moneen eri osaan, sillä se on liian sekava ja pitkä.
     * Kuitenkin metodi tällä hetkellä ottaa kaikki kuvat kansiosta "./images/InputImages/" ja muuttaa niistä eigenfaceja.
     * Sekä tallentaa ne kansioon "./images/ProcessedImages/".
     * Metodi on myös todella tehoton, mutta toimii.
     * Koodi on kommentoitua, jotta siitä ymmärtää jotain.
     */
    public void a() {
        // Määritellään, kuinka suureksi kuvat muutetaan, eli muutetaan kuvat kokoon r x r
        int r = 25;
        //Tuodaan kuvat
        File[] files = getTrainingImages();
        //Luodaan matriisi, johon voidaan asettaa kuvat sarakkeina.
        double[][] wholeMatrix = new double[files.length][r * r];
        MatrixOperations matop = new MatrixOperations();
        //Käydään kaikki kuvat läpi
        for (int i = 0; i < files.length; i++) {
            //Muutetaan kuva kokoon r x r, sekä mustavalkoiseksi
            BufferedImage image = processImage(files[i], r, r);
            //Muutetaan kuva matriisiksi ja matriisi vektoriksi
            double[][] imageMatrix = imageToMatrix(image);
            double[] vector = matop.reshapeToVectorByRow(imageMatrix);
            //Lisätään vektori osaksi matriisia, jossa on kaikki kuvat
            wholeMatrix[i] = vector;
        } 
        //Vähenetään matriisista matriisin keskiarvo, jotta saadaan pois kaikki, joka on kuvien välillä täysin yhteistä.
        double mean = matop.meanOfMatrix(wholeMatrix);
        wholeMatrix = matop.subtract(wholeMatrix, mean);
        try {
            //Kerrotaan matriisi sen transpoosilla. Halutaan oikeasti tutkia matriisin transpoosin ja matriisin tuloa.
            //Kuitenkin asetan tähän sen "väärin päin", sillä kuvien käsittelyssä ja valmiiksi toteutetussa Jama-kirjastossa rivit ja sarakkeet ovat eripäin.
            //Tällöin Jama-kirjastoa kutuessa, se käyttää tässä tapauksessa annetun matriisin transpoosia.
            //Näin voidaan tehdä, koska (AA^T)*T = A^T A
            //Kikka tehdään, jotta ei tarvitsisi laskea kovarianssimatriisia.
            wholeMatrix = matop.multiply(wholeMatrix, matop.transpose(wholeMatrix));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            double[][][] values = matop.eigen(wholeMatrix);
            //Otetaan ominaisarvot diagonaalista.
            double[] eigenvalues = matop.getDiagonal(values[0]);
            double[][] eigenvectors = values[1];
            try {
                //Kerrotaan ominaisvektorit alkuperäisellä matriisilla, jotta saadaan kovarianssimatriisin ominaisarvot.
                eigenvectors = matop.multiply(wholeMatrix, eigenvectors);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            //Normitetaan ominaisvektorit. Nämä ovat jo niin sanottuja eigenfaceja.
            eigenvectors = matop.normalizeVectors(eigenvectors);
            //Järjestetään ominaisvektorit ominaisarvojen perusteella laskevaan järjestykseen ja otetaan huomioon vain osa suurimmista ominaisarvoista.
            double[][][] sortedValues = matop.sortEigenvalue(eigenvectors, eigenvalues);
            double[] principalEigenvalues = sortedValues[1][0];
            double[][] principalEigenvectors = sortedValues[0];
            double[][] eigenface = new double[r][r];
            //Muutetaan ensimmäiset kuusi saraketta kuviksi. Ne löytyvät kansiosta "./images/ProcessedImages/"
            for (int f = 0; f < 5; f++) {
                int j = 0;
                int k = -1;
                for (int i = 0; i < r * r; i++) {
                    if (i % r == 0) {
                        j = 0;
                        k++;
                    }
                    //Skaalataan eigenfacet huonosti välille [0-255] ja lisätään niihin alkuperäisen matriisin keskiarvo.
                    eigenface[j][k] = principalEigenvectors[f][i] * 100 + mean;
                    j++;
                }
                matrixToImage(eigenface, r, r, "" + f);
            }
            

            
        }
    }
    
    /**
     * Metodilla voidaan tuoda kuvia kansiosta "./images/InputImages"
     * @return Palauttaa listan kuvista.
     */
    public File[] getTrainingImages() {
        File directory = new File("./images/InputImages");
        return directory.listFiles();
    }
    
    /**
     * Metodilla voidaan tarkistaa, onko annettu tiedosto kuvatiedosto vai ei.
     * @param file Tarkisteltava tiedosto
     * @return Onko tiedosto kuvatiedosto
     */
    public boolean fileIsImage(File file) {
        String name = file.getName();
        int indexOfExtension = name.lastIndexOf(".");
        if (indexOfExtension == -1) {
            return false;
        }
        String extension = name.substring(indexOfExtension).toLowerCase();
        String[] imageExtensions = {".png", ".jpg", ".jpeg"};
        for (String e:imageExtensions) {
            if (extension.equals(e)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Metodilla muutetaan kuvasta halutun kokoinen mustavalkoinen kuva. Tällä hetkellä se myös tallentaa ne kansioon "./images/ProcessedImages/".
     * Tämä on tarkoitus refaktoroida.
     * @param image Muutettava kuva
     * @param width haluttu kuvan leveys
     * @param height haluttu kuvan korkeus
     * @return Palauttaa BufferedImage-olion, joka on prosessoitu kuva.
     */
    public BufferedImage processImage(File image, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(image);
            Image rescaledImg = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            output.getGraphics().drawImage(rescaledImg, 0, 0, null);
            ImageIO.write(output, "png", new File("./images/ProcessedImages/" + image.getName()));
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Metodi ottaa annetusta mustavalkoisesta BufferedImage-oliosta jokaisen pixelin ja asettaa sen matriisiin
     * @param image muutettava mustavalkoinen kuva
     * @return palauttaa matriisin, jonka koko on sama kuin kuva ja arvot ovat pikseleiden arvot 
     */
    public double[][] imageToMatrix(BufferedImage image) {
        double[][] pixelMatrix = new double[image.getWidth()][image.getHeight()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                //Muutetaan Color-oliolla pikseli arvoiksi 0-255 väliltä.
                Color pixel = new Color(image.getRGB(i, j));
                //Jos kuva on mustavalkoinen kaikki rgb-arvot ovat samat.
                pixelMatrix[i][j] = pixel.getBlue();
            }
        }
        return pixelMatrix;
    }
    /**
     * Metodi muuttaa matriisista mustavalkoisen kuvan. Anntetun matriisin arvot täytyvät olla välillä 0-255.
     * @param matrix Kuvaksi muutettava matriisi
     * @param width Kuvan leveys
     * @param height Kuvan korkeus
     * @param name Väliaikainen parametri, jonka avulla voidaan tallentaa matriisit kuviksi.
     */
    public void matrixToImage(double[][] matrix, int width, int height, String name) {
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int value = (int) matrix[i][j];
                Color c = new Color(value, value, value);
                output.setRGB(i, j, c.getRGB());
            }
        }
        try {
            ImageIO.write(output, "png", new File("./images/ProcessedImages/" + name + ".png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}