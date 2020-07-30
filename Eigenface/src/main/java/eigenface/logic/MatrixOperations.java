/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;

/**
 * Luokalla voidaan laskea joitain matriisien peruslaskutoimituksia, sekä muuttamaan
 * matriisin vektoriksi ja vähentämään vektorista keskiarvon.
 * 
 */
public class MatrixOperations {
    
    /**
     * Metodiin annetaan kaksi matriisia ja se palauttaa matriisien summan.
     * Metodi heittää poikkeuksen, jos matriisien rivien tai sarakkeiden määrä on eri.
     * 
     * @param matrixA Ensimmäinen matriisi
     * @param matrixB Toinen matriisi
     * @return Metodi palauttaa matriisien summan
     * @throws Exception Toistaiseksi heittää geneerisen poikkeuksen, jos matriisit ovat eri muotoisia.
     */
    public double[][] add(double matrixA[][], double matrixB[][]) throws Exception {
        if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
            throw new Exception("Incorrect matrix dimensions");
        }
        double[][] addition = new double[matrixA.length][matrixA[0].length];
        
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                addition[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        
        return addition;
    }
    
    /**
     * Metodilla voidaan tarkistaa, onko matriisit samat. Pääosin vain testienkäyttöön
     * @param matrixA Ensimmäinen tarkasteltava matriisi
     * @param matrixB Toinen tarkasteltava matriisi
     * @return Palauttaa true, jos matriisit ovat täysin samat, muulloin false 
     */
    public boolean matrixEquals(double[][] matrixA, double[][] matrixB) {
        
        if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
            return false;
        }
        
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Metodi laskee annettujen matriisien matriisi tulon kertomalla matriisin A
     * matriisilla B. 
     * @param matrixA Ensimmäinen matriisi
     * @param matrixB Toinen matriisi
     * @return Palauttaa matriisitulon matrixA*matrixB
     * @throws Exception Heittää poikkeuksen, jos matriisin A sarakkeiden määrä ja matriisin B rivien määrä on eri.
     */
    
    public double[][] multiply(double matrixA[][], double matrixB[][]) throws Exception {
        if (matrixA[0].length != matrixB.length) {
            throw new Exception("Incorrect matrix dimensions");
        }
        double[][] result = new double[matrixA.length][matrixB[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                double value = 0;
                for (int k = 0; k < matrixB.length; k++) {
                    value += matrixA[i][k] * matrixB[k][j];
                }
                result[i][j] = value;
            }
        }
        return result;
    }
    
    /**
     * Metodi muuttaa matriisista vektorin littämällä rivit yhteen.
     * Palautettavan vektorin pituus on matriisien rivien määrä kerrottuna sarakkeiden määrällä
     * @param matrix Muutettava matriisi
     * @return Palauttaa vektorin, jonka termit ovat matriisin termit riveittäin.
     */
    
    public double[] reshapeToVectorByRow(double[][] matrix) {
        double[] vector = new double[matrix.length * matrix[0].length];
        int index = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                vector[index] = matrix[i][j];
                index++;
            }
        }
        return vector;
    }
    
    /**
     * Metodi vähentää annetun vektorin termeistä vektroin termien keskiarvon.
     * @param vector Laskentaan käytetty vektori
     * @return Palauttaa annetun vektorin, josta jokaisesta termistä on vähennetty vektorien termien keskiarvo.
     */
    public double[] vectorSubtractMean(double[] vector) {
        double[] returnVector = new double[vector.length];
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i];
        }
        double mean = sum / vector.length;
        for (int j = 0; j < vector.length; j++) {
            returnVector[j] = vector[j] - mean;
        }
        return returnVector;
    }
}


