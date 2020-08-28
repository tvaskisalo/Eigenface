/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.ui;

import eigenface.logic.ImageProcessing;
import eigenface.logic.UiLogic;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

/**
 *  Luokka on vastuussa kasvojentunnistukseen liityvän käyttöliittymän rakentamisesta
 * 
 */
public class FacialRecognitionUi {
    
    public void setRecognitionUi(Stage stage, UiLogic logic) {

        ImageProcessing img = new ImageProcessing();
        GridPane background = new GridPane();
        BorderPane info = new BorderPane();
        Label warning = new Label("");
        info.setTop(Label);
        
        background.setHgap(50);
        background.setVgap(50);
        background.add(info, 1, 1);
        stage.setHeight(500);
        stage.setWidth(500);
        
        stage.setScene(new Scene(background));
        
        
        File[] faces = img.getDetectableImages();
        if (faces.length == 0) {
            warning.setTextt("Please put images to folder Eigenface/images/DetectFaces and restart");
        } else {
            int[] numbers = logic.recognizeFaces(faces);
            Label stats = new Label("Faces found: " + numbers[0] + "\n Others found: " + numbers[1]);
            info.setCenter(stats);
        }
    }
}
