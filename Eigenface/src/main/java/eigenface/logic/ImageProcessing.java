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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        int r = 25;
        File[] files = getTrainingImages();
        double[][] wholeMatrix = new double[files.length][r*r];
        MatrixOperations matop = new MatrixOperations();
        for(int i=0; i<files.length; i++) {
            BufferedImage image = processImage(files[i],r,r);
            double[][] imageMatrix = imageToMatrix(image);
            double[] vector = matop.reshapeToVectorByRow(imageMatrix);
            wholeMatrix[i]=vector;
        } 
        double mean = matop.meanOfMatrix(wholeMatrix);
        wholeMatrix = matop.subtract(wholeMatrix, mean);
        try {
            System.out.println(wholeMatrix.length);
            System.out.println(wholeMatrix[0].length);
            wholeMatrix = matop.multiply(wholeMatrix,matop.transpose(wholeMatrix));
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            double[][][] values = matop.eigen(wholeMatrix);
            double[] eigenvalues = matop.getDiagonal(values[0]);
            double[][] eigenvectors = values[1];
            try {
                eigenvectors = matop.multiply(wholeMatrix, eigenvectors);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            eigenvectors = matop.normalizeVectors(eigenvectors);
            double[][][] sortedValues = matop.sortEigenvalue(eigenvectors, eigenvalues);
            double[] principalEigenvalues = sortedValues[1][0];
            System.out.println(principalEigenvalues.length);
            double[][] principalEigenvectors = sortedValues[0];
            System.out.println(Arrays.toString(principalEigenvalues));
            System.out.println(Arrays.deepToString(principalEigenvectors));
            double[][] eigenface = new double[r][r];
            for(int f=0; f<5; f++) {
                int j=0;
                int k =-1;
                for(int i=0; i<r*r; i++) {
                    if (i%r==0) {
                        j = 0;
                        k++;
                    }
                    eigenface[j][k] = principalEigenvectors[f][i]*100 + mean;
                    j++;
                }
                matrixToImage(eigenface,r,r, ""+f);
            }
            

            
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
                Color pixel = new Color(image.getRGB(i,j));
                pixelMatrix[i][j] = pixel.getBlue();
            }
        }
        return pixelMatrix;
    }
    
    public void matrixToImage(double[][] matrix, int width, int height, String name) {
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int i =0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                int value = (int)matrix[i][j];
                Color c = new Color(value,value,value);
                output.setRGB(i, j, c.getRGB());
            }
        }
        try {
            ImageIO.write(output, "png", new File("./images/ProcessedImages/"+name+".png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}