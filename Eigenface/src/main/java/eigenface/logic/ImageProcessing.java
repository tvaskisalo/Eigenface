/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
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
    public void a() {
        File[] files = getTrainingImages();
        if (fileIsImage(files[0])) {
            BufferedImage b = processImage(files[0],100,100);
            double[][] a = imageToMatrix(b);
            System.out.println(Arrays.deepToString(a));
            MatrixOperations matop = new MatrixOperations();
            double[] e = matop.reshapeToVectorByRow(a);
            System.out.println(Arrays.toString(e));
        } else {

        }
    }
    public File[] getTrainingImages() {
        File directory = new File("./images/InputImages");
        return directory.listFiles();
    }
    
    public boolean fileIsImage(File file) {
        String name = file.getName();
        int indexOfExtension = name.lastIndexOf(".");
        if (indexOfExtension == -1) {
            return false;
        }
        String extension = name.substring(indexOfExtension).toLowerCase();
        String[] imageExtensions = {".png", ".jpg", ".jpeg"};
        for (String e:imageExtensions) {
            if(extension.equals(e)) {
                return true;
            }
        }

        return false;
    }
    
    public void a(File image) {
        try {
                BufferedImage img = ImageIO.read(new File("./images/training/" + image.getName()));  
                Image r = img.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                BufferedImage output = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
                output.getGraphics().drawImage(r, 0, 0, null);
                System.out.println(output.getWidth());
                ImageIO.write(output, "png", new File("./images/ProcessedImages/"+image.getName()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
    
    public BufferedImage processImage(File image, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(image);
            Image rescaledImg = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            output.getGraphics().drawImage(rescaledImg, 0, 0, null);
            ImageIO.write(output, "png", new File("./images/ProcessedImages/"+image.getName()));
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public double[][] imageToMatrix(BufferedImage image) {
        double[][] pixelMatrix = new double[image.getWidth()][image.getHeight()];
        for (int i=0; i<image.getWidth(); i++) {
            for (int j=0; j<image.getHeight(); j++) {
                pixelMatrix[i][j] = image.getRGB(i, j);
            }
        }
        return pixelMatrix;
    }
    
    
    

}