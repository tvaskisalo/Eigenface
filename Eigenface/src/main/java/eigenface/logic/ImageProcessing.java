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
    
}