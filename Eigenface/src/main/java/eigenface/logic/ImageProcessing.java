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
     * Metodilla voidaan tuoda kuvia kansiosta "./images/InputImages"
     * Nämä ovat harjoituskuvia ja pitäisi olla vain kasvoja.
     * @return Palauttaa listan kuvista.
     */
    public File[] getTrainingImages() {
        File directory = new File("./images/InputImages");
        return directory.listFiles();
    }
    /**
     * Metodilla voidaan tuoda kuvia kansiosta "./images/DetectFaces".
     * Nämä kuvat ovat tunnistettavia kuvia.
     * @return Palauttaa listan kuvista.
     */
    public File[] getDetectableImages() {
        File directory = new File("./images/DetectFaces");
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