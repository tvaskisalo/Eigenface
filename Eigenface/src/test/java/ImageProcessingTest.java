
import eigenface.logic.ImageProcessing;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tapan
 */
public class ImageProcessingTest {
    private ImageProcessing img;
    private File testimg;
    
    @Before
    public void setUp() {
        img = new ImageProcessing();
        testimg = new File("./images/TestImage").listFiles()[0];
    }
    
    
    @Test
    public void getTrainingImagesReturnsCorrectAnswer() {
        File[] files = img.getTrainingImages();
        File[] correct = new File("./images/InputImages").listFiles();
        Assert.assertArrayEquals(files, correct);
    }
    
    @Test 
    public void getDetectableImagesReturnsCorrectAnswer() {
        File[] files = img.getDetectableImages();
        File[] correct = new File("./images/DetectFaces").listFiles();
        Assert.assertArrayEquals(files, correct);
    }
    
    @Test
    public void processImageReturnsCorrectSize() {
        BufferedImage bufImg = img.processImage(testimg, 100, 100);
        int height = bufImg.getHeight();
        int width = bufImg.getWidth();
        assertEquals(height, width, 100);
    }
    
    @Test
    public void processImageReturnsGrayScaleImage() {
        BufferedImage bufImg = img.processImage(testimg, 100, 100);
        Boolean t = true;
        for(int i = 0; i<100; i++ ) {
            for(int j = 0; j<100; j++) {
                Color pixel = new Color(bufImg.getRGB(i, j));
                int blu = pixel.getBlue();
                int red = pixel.getRed();
                int green = pixel.getGreen();
                if (blu != red || blu != green || red != green) {
                    t = false;
                }
            }
        }
        
        assertTrue(t);
    }
}
