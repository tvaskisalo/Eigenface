/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *  
 * @author Tapan
 */
public class Dao {
    private int size;

    public Dao(int size) {
        this.size = size;
    }
    
    
    /**
     * Metodi kirjoittaa matriisin .csv-tiedostoon
     * @param name Tiedoston nimi. Tämä on kovakoodattu olemaan joko "eigen" tai "mean", viitaten joko ominaisvektoreihin tai keskiarvo kasvoihin.
     * @param matrix Matriisi, joka kirjoitettaan tiedostoksi
     */
    public void writeMatrix(String name, double[][] matrix) {
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("./existingData/" + name + size + ".csv"));
            for (int i = 0; i < matrix[0].length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (j + 1 == matrix.length) {
                        buffWriter.write(matrix[j][i] + "");
                    } else {
                        buffWriter.write(matrix[j][i] + ",");
                    }
                }
                if (i + 1 != matrix[0].length) {
                    buffWriter.newLine();
                }
            }
            buffWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
    }    
    /**
     * Metodi lukee .csv-tiedoston ja palauttaa matriisin.
     * @param name Tiedoston nimi. Tämä on kovakoodattu olemaan joko "eigen" tai "mean", viitaten joko ominaisvektoreihin tai keskiarvo kasvoihin.
     * @return Palauttaa matriisin, jonka alkiot ovat joko ominaisvektorit tai keskiarvo kasvot.
     */
    public double[][] readMatrix(String name) {
        double[][] returnValue = new double[0][0];
        int j = 0;
        try {
            BufferedReader buffReader = Files.newBufferedReader(Paths.get("./existingData/" + name + size + ".csv"), StandardCharsets.US_ASCII);
            String line = buffReader.readLine();
            if (line != null) {
                String[] numbers = line.split(",");
                if (name.equals("mean")) {
                    returnValue = new double[1][size * size];
                } else {
                    returnValue = new double[numbers.length][size * size];
                }
            }
            while (line != null) {
                String[] numbers = line.split(",");
                for (int i = 0; i < numbers.length; i++) {
                    returnValue[i][j] = Double.valueOf(numbers[i]);
                }
                j++;
                line = buffReader.readLine();
            }
            buffReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return returnValue;
    }
}
