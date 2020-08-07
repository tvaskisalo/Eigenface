/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface;

import eigenface.logic.ImageProcessing;
import Jama.Matrix;
import Jama.QRDecomposition;
import eigenface.logic.MatrixOperations;
import java.io.StringWriter;
import java.io.PrintWriter;


/**
 *  Luokka ei tee mitään tällä hektellä, mutta se on projektin Main-luokka. 
 *  Sitä tullaan käyttämään kutsuakseen käyttöliittymää, eikä muuta. Tämä kikka tehdään siksi,
 *  että kyseinen compiler ei toimi, jos Main-luokasta kutsutaan javafx-kirjastoa.
 * 
 */
public class Main {
    public static void main(String[] args) {
        ImageProcessing im = new ImageProcessing();
        im.a();
    }
    public static String tostring(Matrix m) {
        StringWriter sw = new StringWriter();
        m.print(new PrintWriter(sw, true), 8, 6);
        return sw.toString();
    }
}