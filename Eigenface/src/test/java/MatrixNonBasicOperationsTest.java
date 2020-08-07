/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eigenface.logic.MatrixOperations;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Tapan
 */
public class MatrixNonBasicOperationsTest {
    private MatrixOperations matop;
    
    @Before
    public void setUp() {
        matop = new MatrixOperations();
    }
    
    @Test
    public void matrixToVectorByRowReturnsCorrectVector1() {
        double[][] matrix = {{1,2},
                             {3,4},
                             {5,6}};
        double[] correct = {1,3,5,2,4,6};
        double[] result = matop.reshapeToVectorByRow(matrix);
        assertTrue(Arrays.equals(correct, result));
    }
    
    @Test
    public void matrixToVectorByRowReturnsCorrectVector2() {
        double[][] matrix = {{1,4},{2,5},{3,6}};
        double[] correct = {1,2,3,4,5,6};
        double[] result = matop.reshapeToVectorByRow(matrix);
        assertTrue(Arrays.equals(correct, result));
    }
    
    @Test
    public void vectorSubtractMeanReturnsCorrectVector1() {
        double[] vector = {1,2,3,4,5,6};
        double[] correct = {-2.5, -1.5, -0.5, 0.5, 1.5, 2.5};
        double[] result = matop.vectorSubtractMean(vector);
        assertTrue(Arrays.equals(correct, result));
    }
    
    @Test
    public void vectorSubtractMeanReturnsCorrectVector2() {
        double[] vector = {10,1,9,2,8,3,7,4,6,5};
        double[] correct = {4.5, -4.5, 3.5, -3.5, 2.5, -2.5, 1.5, -1.5, 0.5, -0.5};
        double[] result = matop.vectorSubtractMean(vector);
        assertTrue(Arrays.equals(correct, result));
    }
    
    @Test
    public void getDiagonalReturnsCorrectVector1() {
        double[][] matrix = {{1,0,0},{0,1,0},{0,0,1}};
        double[] correct = {1,1,1};
        double[] result = matop.getDiagonal(matrix);
        assertTrue(Arrays.equals(result, correct));
    }
    
    @Test
    public void getDiagonalReturnsCorrectVector2() {
        double[][] matrix = {{2,7,45},{121,24,12.5093},{12.341,0,121.23}};
        double[] correct = {2,24,121.23};
        double[] result = matop.getDiagonal(matrix);
        assertTrue(Arrays.equals(result, correct));
    }
    
    @Test
    public void calculatePrincipalReturnsCorrectInt1(){
        double[] eigenValues = {10,9,8,7,6,5,4,3,2,1};
        double thresHold = 0.95;
        int correct = 9;
        int result = matop.calculatePrincipal(eigenValues, thresHold);
        assertEquals(correct, result);
    }
    
    @Test
    public void calculatePrincipalReturnsCorrectInt2(){
        double[] eigenValues = {10,9,8,7,6,5,4,3,2,1};
        double thresHold = 0.80;
        int correct = 6;
        int result = matop.calculatePrincipal(eigenValues, thresHold);
        assertEquals(correct, result);
    }
    
    @Test
    public void calculatePrincipalReturnsCorrectInt3(){
        double[] eigenValues = {8,7,6,5,4,3,2,1};
        double thresHold = 0.95;
        int correct = 7;
        int result = matop.calculatePrincipal(eigenValues, thresHold);
        assertEquals(correct, result);
    }
    
    @Test
    public void calculatePrincipalReturnsCorrectInt4(){
        double[] eigenValues = {10,9,8,7,6,5,4,3,2,1};
        double thresHold = 1;
        int correct = 10;
        int result = matop.calculatePrincipal(eigenValues, thresHold);
        assertEquals(correct, result);
    }
    
    @Test
    public void normalizeVectorsReturnsCorrectAnswer() {
        double[][] vectors = {{1,2,3,4},{5,6,7,8}};
        double[][] correct = {{1/Math.sqrt(30),2/Math.sqrt(30), 3/Math.sqrt(30), 4/Math.sqrt(30)},
                              {5/Math.sqrt(174),6/Math.sqrt(174),7/Math.sqrt(174),8/Math.sqrt(174)}};
        double[][] result = matop.normalizeVectors(vectors);
        assertTrue(matop.matrixEquals(correct, result));
    }
    
    @Test
    public void normalizeVectorsColumnsAreLengthOfOne() {
        double[][] vectors = {{1,2,3,4},{5,6,7,8}};
        double[][] result = matop.normalizeVectors(vectors);
        boolean t = true;
        for (int i = 0; i < result.length; i++) {
            double length = 0;
            for (int j = 0; j < result[0].length; j++) {
                length += result[i][j] * result[i][j];
            }
            if (Math.round(length) != 1) {
                t=false;
            }
        }
        assertTrue(t);
    }
    
}
