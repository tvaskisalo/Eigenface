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
    
    @Test 
    public void roundVectorTo3DecimalsReturnsCorrectVector1() {
        double[] vector = {1/3.0, 1/2.0, 1/8.0};
        double[] correct = {0.333, 0.500, 0.125};
        double[] result = matop.roundVectorTo3Decimals(vector);
        assertTrue(matop.vectorEquals(correct, result));
    }
    
    @Test 
    public void roundVectorTo3DecimalsReturnsCorrectVector2() {
        double[] vector = {1/6.0, 0.12345678, 1};
        double[] correct = {0.167, 0.123, 1};
        double[] result = matop.roundVectorTo3Decimals(vector);
        assertTrue(matop.vectorEquals(correct, result));
    }
    
    @Test
    public void powerIterateFindsEigenVector1() {
        double[][] matrix = {{3,1},{1,3}};
        double[] eigenv = matop.powerIterate(matrix, 0.00001, 100);
        eigenv = matop.normalizeVectors(new double[][] {eigenv})[0];
        eigenv = matop.roundVectorTo3Decimals(eigenv);
        
        double[] result = matop.normalizeVectors(new double[][] {{1.0,1.0}})[0];
        result = matop.roundVectorTo3Decimals(result);
        assertTrue(matop.vectorEquals(eigenv, result));
    }
    
    @Test
    public void powerIterateFindsEigenVector2() {
        double[][] matrix = {{1,0,0,0},{-2,4,0,0},{0,-2,1,0},{1,-1,2,-12}};
        double[] eigenv = matop.powerIterate(matrix, 0.00001, 100);
        eigenv = matop.normalizeVectors(new double[][] {eigenv})[0];
        eigenv = matop.roundVectorTo3Decimals(eigenv);
        double[] result = matop.normalizeVectors(new double[][] {{-0.069219,0.042625,-0.151554,0.985101}})[0];
        result = matop.roundVectorTo3Decimals(result);
        assertTrue(matop.vectorEquals(eigenv, result));
    }
    
    @Test
    public void calculateEigenvalueReturnsCorrectAnswer1() {
        double[][] matrix = {{3,1},{1,3}};
        double[] eigenv = matop.normalizeVectors(new double[][] {{1.0,1.0}})[0];
        eigenv = matop.roundVectorTo3Decimals(eigenv);
        double evalue = matop.calculateEigenvalue(matrix, eigenv);
        assertTrue(Math.round(evalue) == 4.0);
    }
    
    @Test
    public void calculateEigenvalueReturnsCorrectAnswer2() {
        double[][] matrix = {{1,0,0,0},{-2,4,0,0},{0,-2,1,0},{1,-1,2,-12}};
        double[] eigenv = matop.normalizeVectors(new double[][] {{-0.069219,0.042625,-0.151554,0.985101}})[0];
        eigenv = matop.roundVectorTo3Decimals(eigenv);
        double evalue = matop.calculateEigenvalue(matrix, eigenv);
        assertTrue(Math.round(evalue) == -12.0);
    }
    
    @Test
    public void calculateEigenvalueReturnsCorrectAnswer3() {
        double[][] matrix = {{1,0,0,0},{-2,4,0,0},{0,-2,1,0},{1,-1,2,-12}};
        double[] eigenv = matop.normalizeVectors(new double[][] {{-0.5547,0.83205,0.0,0.0}})[0];
        eigenv = matop.roundVectorTo3Decimals(eigenv);
        double evalue = matop.calculateEigenvalue(matrix, eigenv);
        assertTrue(Math.round(evalue) == 4.0);
    }
    
    @Test
    public void calculateEigenvectorsReturnsCorrectAnswer1() {
        double[][] matrix = {{1,0,0,0},{-2,4,0,0},{0,-2,1,0},{1,-1,2,-12}};
        double[][] answer = new double[2][4];
        double[] eigenv1 = matop.normalizeVectors(new double[][] {{-0.069219,0.042625,-0.151554,0.985101}})[0];
        double[] eigenv2 = matop.normalizeVectors(new double[][] {{-0.5547,0.83205,0.0,0.0}})[0];
        eigenv1 = matop.roundVectorTo3Decimals(eigenv1);
        eigenv2 = matop.roundVectorTo3Decimals(eigenv2);
        answer[0] = eigenv1;
        answer[1] = eigenv2;
        double[][] result = matop.getEigenpairs(matrix, 2)[1];
        assertTrue(matop.matrixEquals(answer, result));
    }
    
    @Test
    public void calculateEigenvectorsReturnsCorrectAnswer2() {
        double[][] matrix = {{-6,4},{3,5}};
        double[][] answer = new double[2][2];
        double[] eigenv1 = matop.normalizeVectors(new double[][] {{0.948683, -0.316228}})[0];
        double[] eigenv2 = matop.normalizeVectors(new double[][] {{0.242536, 0.970143}})[0];
        eigenv1 = matop.roundVectorTo3Decimals(eigenv1);
        eigenv2 = matop.roundVectorTo3Decimals(eigenv2);
        answer[0] = eigenv1;
        answer[1] = eigenv2;
        double[][] result = matop.getEigenpairs(matrix, 2)[1];
        assertTrue(matop.matrixEquals(answer, result));
    }
    
    @Test
    public void rewriteInTermsOfTheBasisReturnsCorrectAnswer1() {
        double[][] matrix = {{1,3},{2,4}};
        double value = 4;
        double[] vector = {1,2};
        double[][] returnValue = matop.rewriteInTermsOfTheBasis(matrix, value, vector);
        double[][] correct = {{-3,-5},{-6,-12}};
        assertTrue(matop.matrixEquals(returnValue, correct));
    }
    
    @Test
    public void rewriteInTermsOfTheBasisReturnsCorrectAnswer2() {
        double[][] matrix = {{2,5,-9}, {-6,-1,-7},{0,9,7}};
        double value = -8;
        double[] vector = {-1.5, 2.7, 9.2};
        double[][] returnValue = matop.rewriteInTermsOfTheBasis(matrix, value, vector);
        double[][] correct = {{230.4, -871.2, -708.00}, {-414.72, 1568.16, 1274.40}, {-1413.12, 5242.36, 4342.40}};
    }
}
