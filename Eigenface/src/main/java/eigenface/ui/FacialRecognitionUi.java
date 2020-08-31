/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.ui;

import eigenface.logic.ImageProcessing;
import eigenface.logic.UiLogic;
import java.io.File;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

/**
 *  Luokka on vastuussa kasvojentunnistukseen liityvän käyttöliittymän rakentamisesta
 * 
 */
public class FacialRecognitionUi {
    
    public GridPane setRecognitionUi(UiLogic logic, long time) {
        GridPane gp = new GridPane();
        ImageProcessing img = new ImageProcessing();
        Label warning = new Label("");
        gp.add(warning, 0, 0);
        gp.add(new Label("Time to generate eigenfaces: "+ time/1000000000.0), 0, 1);
        File[] faces = img.getDetectableImages();
        if (faces.length == 0) {
            warning.setText("Please put images to folder Eigenface/images/DetectFaces and restart");
        } else {
            long start = System.nanoTime();
            int[] numbers = logic.recognizeFaces(faces);
            long end = System.nanoTime();
            gp.add(new Label("Time to recognize faces: " + (end-start)/1000000000.0), 0, 2);
            Label stats = new Label("Faces found: " + numbers[0] + "\n Others found: " + numbers[1] +"\n correct: " + numbers[2] +"\n incorrect: "+ numbers[3]);
            gp.add(stats, 0, 0);
        }
        
        return gp;
    }
}
