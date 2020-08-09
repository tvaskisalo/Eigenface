/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface;

import eigenface.logic.ImageProcessing;
import eigenface.ui.Ui;
import java.util.Scanner;



/**
 *  Luokka ei tee mitään tällä hetkellä, mutta se on projektin Main-luokka. 
 *  Sitä tullaan käyttämään kutsuakseen käyttöliittymää, eikä muuta. Tämä kikka tehdään siksi,
 *  että kyseinen compiler ei toimi, jos Main-luokasta kutsutaan javafx-kirjastoa.
 * 
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Type 1 for text ui and 2 for graphical ui");
        String uiChoice = input.nextLine();
        if(uiChoice.equals("1")) {
            ImageProcessing im = new ImageProcessing();
            im.a();
        } else if (uiChoice.equals("2")) {
            Ui.main(args);            
        }
    }
}