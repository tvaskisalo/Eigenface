/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.ui;

import eigenface.logic.UiLogic;
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
        
        Label errorMessage = new Label("");
        Label choice = new Label("Generate new eigenfaces or use an existing eigenfaces? \n If you use existing eigenfaces input size and make sure you have correct data in folder /Eigenface/existingData/");
        Button newEigenfaces = new Button("Generate new eigenfaces");
        Button useExisting = new Button("Use existing eigenfaces");
        TextField inputSize = new TextField();
        
        
        settings.add(choice, 0, 0);
        settings.add(newEigenfaces, 0, 1);
        settings.add(useExisting, 1, 2);
        settings.add(inputSize, 0, 2);
        settings.add(errorMessage, 0, 3);
        
        stage.setScene(new Scene(background));
        newEigenfaces.setOnMouseClicked((event) -> {
            setGenerationSettingsUi(stage);
        });
        useExisting.setOnMouseClicked((event) -> {
            try {
                int size = Integer.parseInt(inputSize.getText());
                if (size <= 0) {
                    errorMessage.setText("Please insert a positive number");
                } else {
                    UiLogic processingLogic = new UiLogic(size);
                    long time = processingLogic.useExisting();
                    FacialRecognitionUi fui = new FacialRecognitionUi();
                    GridPane gp = fui.setRecognitionUi(processingLogic, time);
                    background.setTop(new Label(""));
                    background.setCenter(gp);
                }
            } catch (Exception e) {
                errorMessage.setText("Please insert numbers only");
            }
        });
    }
    
    public void setGenerationSettingsUi(Stage stage) {
        BorderPane background = new BorderPane();
        GridPane settings = new GridPane();
        background.setCenter(settings);
        Label dimensions = new Label("Input the size of the picture (pictue will be input x input large). \nSizes larger than 100x100 are not recommended");
        Label sizeLabel = new Label("Size");
        Label errorMessage = new Label("");
        TextField inputSize = new TextField();
        Button submit = generateSubmitButton(background, errorMessage, inputSize);
        background.setTop(dimensions);
        settings.add(sizeLabel, 1, 0);
        settings.add(inputSize, 2, 0);
        settings.add(submit, 1, 1);
        settings.add(errorMessage, 2, 1);
        stage.setScene(new Scene(background));
    }
    
    public Button generateSubmitButton(BorderPane background, Label errorMessage, TextField inputSize) {
        Button submit = new Button("Generate Face");
        submit.setOnMouseClicked((event)-> {
            try {
                int size = Integer.parseInt(inputSize.getText());
                if (size <= 0) {
                    errorMessage.setText("Please insert a positive number");
                } else {
                    UiLogic processingLogic = new UiLogic(size);
                    long time = processingLogic.generateEigenface();
                    FacialRecognitionUi fui = new FacialRecognitionUi();
                    GridPane gp = fui.setRecognitionUi(processingLogic, time);
                    background.setTop(new Label(""));
                    background.setCenter(gp);
                }
            } catch (NumberFormatException e) {
                errorMessage.setText("Please insert numbers only");
            } 
        });
        return submit;
    } 
}
