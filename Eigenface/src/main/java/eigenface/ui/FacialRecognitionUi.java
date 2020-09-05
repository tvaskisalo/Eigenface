/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.ui;

import eigenface.logic.ImageProcessing;
import eigenface.logic.UiLogic;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *  Luokka on vastuussa kasvojentunnistukseen liityvän käyttöliittymän rakentamisesta
 *  Luokassa on indeksin kasvattamiseen ja vähentämiseen erilliset metodit, jotta voidaan kiertää lambda-funktioiden rajoituksia.
 */
public class FacialRecognitionUi {
    private int index;
    private int maxIndex;
    /**
     * Metodi rakentaa kasvojentunnistuken käyttöliittymän.
     * @param logic UiLogic-olio, jossa on tehty tarvittavat asiat käyttämään ominaiskasvoja ja keskiarvo kasvoa
     * @param time Ominaiskasvojen generointiin kulunut aika. Jos käytetään tiedostoa, niin tiedoston lukuun kulunut aika
     * @return Palauttaa GridPane-olion, jossa on käyttöliittymä.
     */
    public GridPane setRecognitionUi(UiLogic logic, long time) {
        GridPane gp = new GridPane();
        ImageProcessing img = new ImageProcessing();
        Label warning = new Label("");
        String[] detectedFaces = new String[0];
        gp.add(warning, 0, 0);
        if (time > -1) {
            gp.add(new Label("Time to generate eigenfaces: " + time / 1000000000.0), 0, 1);
        }
        File[] faces = img.getDetectableImages();
        if (faces.length == 0) {
            warning.setText("Please put images to folder Eigenface/images/DetectFaces and restart");
        } else {
            long start = System.nanoTime();
            detectedFaces = logic.recognizeFaces(faces);
            long end = System.nanoTime();
            gp.add(new Label("Time to recognize faces: " + (end - start) / 1000000000.0), 0, 2);
            Label stats = new Label("Faces found: " + detectedFaces.length 
                    + "\nOthers found: " + (faces.length - detectedFaces.length) + "\nDetected faces:");
            gp.add(stats, 0, 0);
        }
        BorderPane images = createImageViewerUi(detectedFaces);
        gp.add(images, 0, 3);
        return gp;
    }
    /**
     * Metodi luo annetusta listasta käyttöliittymän, jota voidaan selata ja katsoa, mitkä kuvat tunnistettiin kasvoiksi
     * @param imageName Lista, jossa on kaikkien tunnistettujen kasvojen tiedoston nimet.
     * @return Palauttaa BorderPane-olion, jossa on "dia-esitys" kasvoista.
     */
    public BorderPane createImageViewerUi(String[] imageName) {
        if (imageName.length == 0) {
            return new BorderPane();
        }
        BorderPane returnValue = new BorderPane();
        Button next = new Button("Next");
        Button previous = new Button("Previous");
        Image[] images = new Image[imageName.length];
        index = 0;
        maxIndex = imageName.length - 1;
        for (int i = 0; i < imageName.length; i++) {
            images[i] = new Image("file:./images/DetectFaces/" + imageName[i]);
        }
        ImageView imageViewer = new ImageView();
        imageViewer.setImage(images[0]);
        returnValue.setTop(imageViewer);
        returnValue.setRight(next);
        returnValue.setLeft(previous);
        
        next.setOnMouseClicked((event) -> {
            imageViewer.setImage(images[nextIndex()]);
        });
        previous.setOnMouseClicked((event) -> {
            imageViewer.setImage(images[previousIndex()]);
        });
        return returnValue;
    }
    /**
     * Kasvattaa kuvan indeksiä. Jos se kasvaa yli listan pituuden asettaa se indeksin nollaksi.
     * @return Palauttaa indeksin 
     */
    private int nextIndex() {
        index++;
        if (index > maxIndex) {
            index = 0;
        }
        return index;
    }
    /**
     * Vähentää kuvan indeksiä. Jos se vähenee negatiivisesksi asettaa se indeksin suurimmaksi mahdolliseksi.
     * @return Palauttaa indeksin
     */
    private int previousIndex() {
        index--;
        if (index < 0) {
            index = maxIndex;
        }
        return index;
    }
}

