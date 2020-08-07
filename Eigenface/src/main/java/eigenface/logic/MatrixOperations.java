/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import Jama.QRDecomposition;
import java.lang.Math;

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
    public double[][] subtract(double matrixA[][], double matrixB[][]) throws Exception {
        if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
            throw new Exception("Incorrect matrix dimensions");
        }
        double[][] substraction = new double[matrixA.length][matrixA[0].length];
        
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                substraction[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
        
        return substraction;
    }
    
    public double[][] subtract(double matrix[][], double value) {
        double[][] substraction = new double[matrix.length][matrix[0].length];
        
        for(int i =0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                substraction[i][j] = matrix[i][j]-value;
            }
        }
        
        return substraction;
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
    
    public boolean vectorEquals(double[] vectorA, double[] vectorB) {
        if(vectorA.length != vectorB.length) {
            return false;
        }
        for (int i=0; i<vectorA.length; i++) {
            if (vectorA[i]!=vectorB[i]) {
                return false;
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
        if (matrixA.length != matrixB[0].length) {
            throw new Exception("Incorrect matrix dimensions");
        }
        double[][] result = new double[matrixA[0].length][matrixB.length];
        for (int i = 0; i < matrixA[0].length; i++) {
            for (int j = 0; j < matrixB.length; j++) {
                double value = 0;
                for (int k = 0; k < matrixB[0].length; k++) {
                    value += matrixA[k][i] * matrixB[j][k];
                }
                result[j][i] = value;
            }
        }
        return result;
    }
    
    public double[] multiply(double matrix[][], double vector[]) throws Exception{
        if (matrix.length != vector.length) {
            throw new Exception("Incorrect dimensions");
        }
        double[] result = new double[matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            double value = 0;
            for (int k = 0; k < vector.length; k++) {
                value += matrix[k][i] * vector[k];
            }
            result[i] = value;
            
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
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                vector[index] = matrix[j][i];
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
    
    public double meanOfMatrix(double[][] matrix) {
        int count = matrix.length * matrix[0].length;
        double sum =0;
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                sum += matrix[i][j];
            }
        }
        return sum/count;
    }
    
    public double[][] transpose(double[][] matrix) {
        double[][] transpose = new double[matrix[0].length][matrix.length];
        
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        
        return transpose;
    }
    
    public double[][][] iterate(double[][] f, int i) {
        Matrix e = new Matrix(f);
        QRDecomposition d = new QRDecomposition(e);
        Matrix R = d.getR();
        Matrix Q = d.getQ();
        e = R.times(Q);
        for(int j =0; j<i; j++) {
            d = new QRDecomposition(e);
            R = d.getR();
            Q = d.getQ();
            e = R.times(Q);
        }
        double[][][] values = {transpose(R.getArray()), transpose(Q.getArray())}; 
        return values;
    }
    
    public double[][][] eigen(double[][] matrix) {
        EigenvalueDecomposition eig = new EigenvalueDecomposition(new Matrix(matrix));
        double[][] eigValues = transpose(eig.getD().getArray());
        double[][] eigVectors = transpose(eig.getV().getArray());
        double[][][] values = {eigValues, eigVectors};
        return values;
    }
    
    public double[] getDiagonal(double[][] matrix) {
        double[] diagonal = new double[matrix.length];
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                if(i==j) {
                    diagonal[i] = matrix[i][j];
                }
            }
        }
        return diagonal;
    }
    
    public double[][][] sortEigenvalue(double[][] matrix, double[] vector) {
        double[] sortedV = vector;
        for (int i=1; i<vector.length; i++) {
            int j= i-1;
            while(j>=0 && sortedV[j]<sortedV[j+1]) {
                double a = sortedV[j];
                sortedV[j]=sortedV[j+1];
                sortedV[j+1]=a;
                j-=1;
            }
        }
        int principal = calculatePrincipal(sortedV, 0.95);
        double[][] sortedM = new double[principal+1][matrix[0].length];
        double[] eigen = new double[principal+1];
        for(int k =0; k<=principal; k++) {
            double value = sortedV[k];
            eigen[k]=value;
            int index = 0;
            for(int g =0; g<vector.length; g++) {
                if(vector[g] == value) {
                    index = g;
                    break;
                }
            }
            sortedM[k]=matrix[index];
        }
        
        return new double[][][] {sortedM, {eigen}};
    }
    
    public int calculatePrincipal(double[] sortedVector, double thresHold) {
        double sumAll =0;
        for(int i =0; i<sortedVector.length; i++) {
            sumAll+=sortedVector[i];
        }
        double sumPartial =0;
        for(int j=0; j<sortedVector.length; j++) {
            sumPartial += sortedVector[j];
            if(sumPartial/sumAll > thresHold) {
                return j;
            }
        }
        
        return sortedVector.length-1;
    }
    
    public double[][] normalizeVectors(double[][] matrix) {
        double[][] normalized = new double[matrix.length][matrix[0].length];
        for (int i=0; i<matrix.length; i++) {
            double sum = 0;
            for (int j=0; j<matrix[0].length; j++) {
                sum += matrix[i][j]*matrix[i][j];
            }
            double length = Math.sqrt(sum);
            for (int k=0; k<matrix[0].length; k++) {
                normalized[i][k] = matrix[i][k]/length;
            }
        }
        
        return normalized;
    }
    
   
}


