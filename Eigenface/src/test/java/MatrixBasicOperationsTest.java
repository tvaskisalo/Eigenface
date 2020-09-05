
import eigenface.logic.MatrixOperations;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tapan
 */
public class MatrixBasicOperationsTest {
    private MatrixOperations matop;
    
    @Before
    public void setUp() {
        matop = new MatrixOperations();
    }
    
    @Test
    public void matrixEqualsReturnsTrueCorrectly1() {
        double[][] matrixA = new double[3][3];
        double[][] matrixB = new double[3][3];
        
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                matrixA[i][j]=i+j;
                matrixB[i][j]=i+j;
            }
        }
        
        assertTrue(matop.matrixEquals(matrixA, matrixB));
    }
    
    @Test
    public void matrixEqualsReturnsTrueCorrectly2() {
        double[][] matrixA = new double[100][100];
        double[][] matrixB = new double[100][100];
        
        for(int i = 0; i<100; i++) {
            for(int j = 0; j<100; j++) {
                matrixA[i][j]=i+j;
                matrixB[i][j]=i+j;
            }
        }
        
        assertTrue(matop.matrixEquals(matrixA, matrixB));
    }
    
    
    @Test
    public void matrixEqualsReturnsFalseCorrectyl() {
        double[][] matrixA = { {1,2,3}, {4,5,6 }, {7,8,9} };
        double[][] matrixB = { {1,2}, {3,4} };
        assertFalse(matop.matrixEquals(matrixA, matrixB));
    }
    
    @Test
    public void matrixEqualsReturnsFalseCorrectly2() {
        double[][] matrixA = new double[3][3];
        double[][] matrixB = new double[3][3];
        
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                matrixA[i][j]=i-j;
                matrixB[i][j]=i+j;
            }
        }
        
        assertFalse(matop.matrixEquals(matrixA, matrixB));
    }
    
    @Test
    public void matrixEqualsReturnsFalseCorrectly3() {
        double[][] matrixA = new double[100][100];
        double[][] matrixB = new double[100][100];
        
        for(int i = 0; i<100; i++) {
            for(int j = 0; j<100; j++) {
                matrixA[i][j]=i+j;
                matrixB[i][j]=i+j;
            }
        }
        matrixA[50][50]=0;
        
        assertTrue(!matop.matrixEquals(matrixA, matrixB));
    }
    
    @Test
    public void vectorEqualsReturnsFalseCorrectly1() {
        double[] vectorA = {1,2,3};
        double[] vectorB = {1,2};
        assertFalse(matop.vectorEquals(vectorA, vectorB));
    }
    
    @Test
    public void vectorEqualsReturnsFalseCorrectly2() {
        double[] vectorA = {4,5,6};
        double[] vectorB = {7,8,9};
        assertFalse(matop.vectorEquals(vectorA, vectorB));
    }
    
    @Test
    public void vectorEqualsReturnsTrueCorrecyly() {
        double[] vectorA = {1,2,3,4,5,6,7,8,9};
        double[] vectorB = vectorA;
        assertTrue(matop.vectorEquals(vectorA, vectorB));
    }
    
    
    @Test 
    public void matrixSubtractionReturnsCorrectAnswer1() {
        double[][] matrixA = new double[100][100];
        double[] value = new double[100];
        double[][] result = new double[100][100];
        
        for(int i = 0; i<100; i++) {
            for(int j = 0; j<100; j++) {
                matrixA[i][j]=(i+1)/(j+1);
                result[i][j]=(i+1)/(j+1) -21;
            }
            value[i] = 21;
        }
        try {
            double[][] subtraction = matop.subtract(matrixA, value);
            assertTrue(matop.matrixEquals(subtraction, result));            
        } catch(Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
    
    @Test 
    public void matrixSubtractionReturnsCorrectAnswer2() {
        double[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        double value = 2;
        
        double[][] correct = {{-1,2,3},{4,3,6},{7,8,7}};
        
        double[][] substraction = matop.subtract(matrix, value);
        assertTrue(matop.matrixEquals(correct, substraction));
    }
    
    @Test 
    public void matrixSubtractionReturnsCorrectAnswer3() {
        double[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        double value = -5;
        
        double[][] correct = {{6,2,3},{4,10,6},{7,8,14}};
        
        double[][] substraction = matop.subtract(matrix, value);
        assertTrue(matop.matrixEquals(correct, substraction));
    }
    
    
    @Test
    public void matrixMultiplicationThrowsExceptionWithBadMatrixes1() {
        double[][] matrixA = new double[10][100];
        double[][] matrixB = matrixA;
        
        boolean caught = false;
        try {
            matop.multiply(matrixA, matrixB);
        } catch (Exception e) {
            caught = true;
        }
        assertTrue(caught);
    }
    
    @Test
    public void matrixMultiplicationThrowsExceptionWithBadMatrixes2() {
        double[][] matrixA = new double[10][100];
        double[] vector = new double[5];
        
        boolean caught = false;
        try {
            matop.multiply(matrixA, vector);
        } catch (Exception e) {
            caught = true;
        }
        assertTrue(caught);
    }
    
    @Test
    public void matrixMultiplicationThrowsExceptionWithBadMatrixes3() {
        double[][] matrixA = new double[10][100];
        double[] vector = new double[5];
        
        boolean caught = false;
        try {
            matop.multiply(vector, matrixA);
        } catch (Exception e) {
            caught = true;
        }
        assertTrue(caught);
    }
    
    
    @Test
    public void matrixMultiplicationWithZeroMatrixReturnsZeroMatrix1() {
        double[][] matrixA = {{0,0}, {0,0}};
        double[][] matrixB = {{1,2},{3,4}};
        try {
            double[][] result = matop.multiply(matrixA, matrixB);
            assertTrue(matop.matrixEquals(matrixA, result));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
    
    @Test
    public void matrixMultiplicationWithZeroMatrixReturnsZeroMatrix2() {
        double[][] matrix = {{1,2},{3,4},{5,6}};
        double[] vector = {0,0,0};
        try {
            double[] result = matop.multiply(matrix, vector);
            double[] correct = {0,0};
            assertTrue(matop.vectorEquals(correct, result));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
    
    @Test
    public void matrixMultiplicationReturnsCorrectAnswer1() {
        double[][] matrixA = {{1,2},{3,4}};
        double[][] matrixB = matrixA;
        double[][] correct = {{7,10},{15,22}};
        try {
            double[][] result = matop.multiply(matrixA, matrixB);
            assertTrue(matop.matrixEquals(correct, result));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
    
    @Test
    public void matrixMultiplicationReturnsCorrectAnswer2() {
        double[][] matrixA = {{1,2,3},{4,5,6}};
        double[][] matrixB = {{1,2},{3,4},{5,6}};
        double[][] correct = {{9,12,15},{19,26,33},{29,40,51}};
        boolean t = false;
        try {
            double[][] result = matop.multiply(matrixA, matrixB);
            t=matop.matrixEquals(correct, result);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        assertTrue(t);
    }
    
    @Test
    public void matrixMultiplicationReturnsCorrectAnswer3() {
        double[][] matrixA = {{1,2,3},{4,5,6}};
        double[] vector = {1,2};
        double[] correct = {9,12,15};
        boolean t = false;
        try {
            double[] result = matop.multiply(matrixA, vector);
            t=matop.vectorEquals(correct, result);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        assertTrue(t);
    }
    
    @Test
    public void matrixMultiplicationReturnsCorrectAnswer4() {
        double[][] matrixA = {{1,2,3},{4,5,6}};
        double[] vector = {1,2,3};
        double[] correct = {14,32};
        boolean t = false;
        try {
            double[] result = matop.multiply(vector,matrixA);
            t=matop.vectorEquals(correct, result);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        assertTrue(t);
        
    }
    
    @Test
    public void matrixMeanReturnsCorrectAnswer1() {
        double[][] matrix ={{1,2,3},{4,5,6}};
        double[] correct = {2.5, 3.5, 4.5};
        double[] result = matop.meanOfMatrixByRow(matrix);
        assertTrue(matop.vectorEquals(correct, result));
    }
    
    @Test
    public void matrixMeanReturnsCorrectAnswer2() {
        double[][] matrix ={{9,12,15},{19,26,33},{29,40,51}};
        double[] correct = {19,26, 33};
        double[] result = matop.meanOfMatrixByRow(matrix);
        assertTrue(matop.vectorEquals(correct, result));
    }
    
    @Test
    public void matrixTransposeReturnsCorrectAnswer1() {
        double[][] matrix ={{9,12,15},{19,26,33},{29,40,51}};
        double[][] correct = {{9,19,29},{12,26,40},{15,33,51}};
        double[][] result = matop.transpose(matrix);
        assertTrue(matop.matrixEquals(correct, result));
    }
    
    @Test
    public void matrixTransposeReturnsCorrectAnswer2() {
        double[][] matrix = {{1,2,3},{4,5,6}};
        double[][] correct = {{1,4},{2,5},{3,6}};
        double[][] result = matop.transpose(matrix);
        assertTrue(matop.matrixEquals(correct, result));        
    }
    
    @Test
    public void dotproductReturnsCorrectAnswer1() {
        double[] vectorA = {1,2,3};
        double[] vectorB = {4,5,6};
        double correct = 32;
        try {
            double result = matop.dotproduct(vectorA, vectorB);
            assertTrue(correct == result);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void dotproductReturnsCorrectAnswer2() {
        double[] vectorA = {1,2,3,4,5,6,7,8,9};
        double[] vectorB = vectorA;
        double correct = 285;
        try {
            double result = matop.dotproduct(vectorA, vectorB);
            assertTrue(correct == result);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void dotproductThrowsException() {
        double[] vectorA={1,2};
        double[] vectorB={1};
        boolean t = false;
        try {
            matop.dotproduct(vectorA, vectorB);
        } catch(Exception e) {
            t = true;
        } finally {
            assertTrue(t);
        }
    }
    
    @Test
    public void vectorLengthReturnsCorrectAnswer1() {
        double[] vectorA = {1,2,3};
        double correct = Math.sqrt(14);
        try {
            double result = matop.vectorLength(vectorA);
            assertTrue(correct == result);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void vectorLengthReturnsCorrectAnswer2() {
        double[] vectorA = {1,2,3,4,5,6,7,8,9};
        double correct = Math.sqrt(285);
        try {
            double result = matop.vectorLength(vectorA);
            assertTrue(correct == result);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void vectorMultiplyReturnsCorrectAnswer1() {
        double[] vector = {1,2,3,4,5,6};
        double value = 0;
        double[] correct = {0,0,0,0,0,0};
        double[] result = matop.vectorMultiply(vector, value);
        assertTrue(matop.vectorEquals(correct, result));
    }
    
    @Test
    public void vectorMultiplyReturnsCorrectAnswer2() {
        double[] vector = {1,2,3,4,5,6};
        double value = 2;
        double[] correct = {2,4,6,8,10,12};
        double[] result = matop.vectorMultiply(vector, value);
        assertTrue(matop.vectorEquals(correct, result));
    }
    
    @Test
    public void vectorAddReturnsCorrectAnswer1() {
        double[] vectorA = {1,2,3};
        double[] vectorB = {4,5,6};
        double[] correct = {5,7,9};
        try {
            double[] result = matop.vectorAdd(vectorA, vectorB);
            assertTrue(matop.vectorEquals(result, correct));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void vectorAddReturnsCorrectAnswer2() {
        double[] vectorA = {1,2,3,4,5,6,7,8,9};
        double[] vectorB = vectorA;
        double[] correct = {2,4,6,8,10,12,14,16,18};
        try {
            double[] result = matop.vectorAdd(vectorA, vectorB);
            assertTrue(matop.vectorEquals(result, correct));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void vectorAddThrowsException() {
        double[] vectorA={1,2};
        double[] vectorB={1};
        boolean t = false;
        try {
            matop.vectorAdd(vectorA, vectorB);
        } catch(Exception e) {
            t = true;
        } finally {
            assertTrue(t);
        }
    }
    
    @Test
    public void vectorSubtractReturnsCorrectAnswer1() {
        double[] vectorA = {1,2,3};
        double[] vectorB = {4,5,6};
        double[] correct = {-3,-3,-3};
        try {
            double[] result = matop.vectorSubtract(vectorA, vectorB);
            assertTrue(matop.vectorEquals(result, correct));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void vectorSubtractReturnsCorrectAnswer2() {
        double[] vectorA = {1,2,3,4,5,6,7,8,9};
        double[] vectorB = vectorA;
        double[] correct = {0,0,0,0,0,0,0,0,0};
        try {
            double[] result = matop.vectorSubtract(vectorA, vectorB);
            assertTrue(matop.vectorEquals(result, correct));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void vectorSubtractThrowsException() {
        double[] vectorA={1,2};
        double[] vectorB={1};
        boolean t = false;
        try {
            matop.vectorSubtract(vectorA, vectorB);
        } catch(Exception e) {
            t = true;
        } finally {
            assertTrue(t);
        }
    }
    
    @Test
    public void divideVectorWithValueReturnsCorrectAnswer1() {
        double[] vector = {1,2,3};
        double value = -2;
        double[] correct = {-0.5,-1,-1.5};
        try {
            double[] result = matop.divideVectorWithValue(vector, value);
            assertTrue(matop.vectorEquals(result, correct));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void divideVectorWithValueReturnsCorrectAnswer2() {
        double[] vector = {1,2,3,4,5,6,7,8,9};
        double value = 2;
        double[] correct = {0.5,1,1.5,2,2.5,3,3.5,4,4.5};
        try {
            double[] result = matop.divideVectorWithValue(vector, value);
            assertTrue(matop.vectorEquals(result, correct));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void divideVectorWithValueThrowsException() {
        double[] vector = {1,2};
        double value = 0;
        boolean t = false;
        try {
            matop.divideVectorWithValue(vector, value);
        } catch(Exception e) {
            t = true;
        } finally {
            assertTrue(t);
        }
    }
    
}
