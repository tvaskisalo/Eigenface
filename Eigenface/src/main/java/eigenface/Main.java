/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface;
import eigenface.ui.Ui;



/**
 *  Luokka ei tee mitään tällä hetkellä, mutta se on projektin Main-luokka. 
 *  Sitä tullaan käyttämään kutsuakseen käyttöliittymää, eikä muuta. Tämä kikka tehdään siksi,
 *  että kyseinen compiler ei toimi, jos Main-luokasta kutsutaan javafx-kirjastoa.
 * 
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Ui.main(args);
    }
}