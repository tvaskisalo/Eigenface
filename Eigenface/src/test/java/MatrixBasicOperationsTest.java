
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
        double[][] matrixA = new double[3][3];
        double[][] matrixB = new double[3][3];
        double[][] result = new double[3][3];
        
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                matrixA[i][j]=i+j;
                matrixB[i][j]=i-j;
                result[i][j]=j+j;
            }
        }
        
        try {
            double[][] add = matop.subtract(matrixA, matrixB);
            assertTrue(matop.matrixEquals(add, result));
        } catch (Exception e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
    
    @Test 
    public void matrixSubtractionReturnsCorrectAnswer2() {
        double[][] matrixA = new double[100][100];
        double[][] matrixB = new double[100][100];
        double[][] result = new double[100][100];
        
        for(int i = 0; i<100; i++) {
            for(int j = 0; j<100; j++) {
                matrixA[i][j]=(i+1)/(j+1);
                matrixB[i][j]=i*j;
                result[i][j]=(i+1)/(j+1) -i*j;
            }
        }
        try {
            double[][] addition = matop.subtract(matrixA, matrixB);
            assertTrue(matop.matrixEquals(addition, result));            
        } catch(Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
    
    
    @Test
    public void matrixSubtractionThrowsExceptionWithBadMatrixes() {
        double[][] matrixA = new double[10][100];
        double[][] matrixB = new double[100][10];
        boolean caught = false;
        try {
            matop.subtract(matrixA, matrixB);
        } catch(Exception e) {
            caught = true;
        }
        assertTrue(caught);
    }
    
    @Test
    public void matrixMultiplicationThrowsExceptionWithBadMatrixes() {
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
    public void matrixMeanReturnsCorrectAnswer1() {
        double[][] matrix ={{1,2,3},{4,5,6}};
        double correct = 3.5;
        double result = matop.meanOfMatrix(matrix);
        assertTrue(result == correct);
    }
    
    @Test
    public void matrixMeanReturnsCorrectAnswer2() {
        double[][] matrix ={{9,12,15},{19,26,33},{29,40,51}};
        double correct = 26;
        double result = matop.meanOfMatrix(matrix);
        assertTrue(result == correct);
    }
}
