/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Tapan
 */
public class SettingsUi {
    
    public void setSettingsUi(Stage stage) {
        BorderPane background = new BorderPane();
        GridPane settings = new GridPane();
        background.setCenter(settings);
        
        Label dimensions = new Label("Input the size of the picture (pictue will be input x input large). \nSizes larger than 100x100 are not recommended");
        Label sizeLabel = new Label("Size");
        Label errorMessage = new Label("");
        
        TextField inputSize = new TextField();
        Button submit = new Button("Generate Face");
        
        background.setTop(dimensions);
        settings.add(sizeLabel, 0, 0);
        settings.add(inputSize, 1, 0);
        settings.add(submit, 0, 1);
        settings.add(errorMessage, 1, 1);
        submit.setOnMouseClicked((event)-> {
            try {
                int size = Integer.parseInt(inputSize.getText());
                if (size <= 0) {
                    errorMessage.setText("Please insert a positive number");
                } else {
                    ProcessingUi process = new ProcessingUi();
                    process.setProcessUi(stage, size);
                }
            } catch (NumberFormatException e) {
                errorMessage.setText("Please insert numbers only");
            } 
        });
        
        stage.setScene(new Scene(background));
    }
}
