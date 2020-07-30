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
 *  ja muuttaa kuvat matriiseiksi.
 * 
 */
public class ImageProcessing {
    
    /**
     * Metodilla voidaan tuoda kuvia kansiosta "./images/training".
     * Metodi tällä hetkellä ei toimi kuvatusti, sillä olen opetellut metodin avulla kuvien käsittelyä.
     */
    public void importTrainingImages() {
        File directory = new File("./images/training");
        File[] listOfImages = directory.listFiles();
        for (File image : listOfImages) {
            try {
                BufferedImage img = ImageIO.read(new File("./images/training/" + image.getName()));  
                Image r = img.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                BufferedImage output = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
                output.getGraphics().drawImage(r, 0, 0, null);
                System.out.println(output.getWidth());
                ImageIO.write(output, "png", new File("./images/training/image.png"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    

}
