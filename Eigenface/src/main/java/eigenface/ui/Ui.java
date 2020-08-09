/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Tapan
 */
public class Ui extends Application {
    public static void main(String[] args) {
        launch();
        
    }  

    @Override
    public void start(Stage stage)  {
        BorderPane background = new BorderPane();
        SettingsUi settingsUi = new SettingsUi();
        settingsUi.setSettingsUi(stage);
        stage.setTitle("Eigenface");
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.show();
    }
}
