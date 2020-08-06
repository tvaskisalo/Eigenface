/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eigenface.logic.MatrixOperations;
import java.util.Arrays;
import org.junit.Assert;
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
}
