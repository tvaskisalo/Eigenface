/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.ui;

import eigenface.logic.UiLogic;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Tapan
 */
public class ProcessingUi {
    
    public void setProcessUi(Stage stage, int size) {
        GridPane background = new GridPane();
        UiLogic processingLogic = new UiLogic(size);
        BorderPane processInfo = new BorderPane();
        background.setHgap(50);
        background.setVgap(50);
        background.add(processInfo, 1, 1);
        stage.setHeight(500);
        stage.setWidth(500); 
        stage.setScene(new Scene(background));
        processingLogic.generateEigenface(processInfo);
    }
}
