/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Tapan
 */
public class UiLogic {
    private File[] files;
    private ImageProcessing imgProcess;
    private MatrixOperations matop;
    private double[][] dataMatrix;
    private double[][] innerproductData;
    private double[] meanface;
    private double[] eigenvalues;
    private double[][] innerEigenvectors;
    private double[][] covEigenvectors;
    private int size;
    private double[][] principalEigenvectors;

    public UiLogic(int size) {
        this.size = size;
        imgProcess = new ImageProcessing();
        files = imgProcess.getTrainingImages();
        matop = new MatrixOperations();
        dataMatrix = new double[files.length][size * size];
        innerproductData = new double[files.length][files.length];
    }
    
    
    public void generateEigenface(BorderPane info) {
        Label processInfo = new Label("");
        ProgressBar pb = new ProgressBar(0);
        info.setTop(processInfo);
        info.setCenter(pb);
        imageToMatrixProgress(processInfo);
        System.out.println(dataMatrix.length +" "+dataMatrix[0].length);
        meanFaceProgress(processInfo);
        innerProductProgress(processInfo);
        
        eigenvaluesAndVectors(processInfo);
        normalizeEigenvectorsProcess(processInfo);
        sortEigenvectorsByEigenvaluesProcess(processInfo);
        
        File[] faces = imgProcess.getDetectableImages();
        int t =0;
        for (int i = 0; i<faces.length; i++) {
            File f = faces[i];
            double[] faceVector = matop.reshapeToVectorByRow(imgProcess.imageToMatrix(imgProcess.processImage(f,size,size)));
            boolean b = imageIsAFace(principalEigenvectors,faceVector, meanface, 80);
            if (i == 99) {
                System.out.println("Faces found: "+t/100.0);
                t=0;
            }
            if(b) {
                t++;
            }
        }
        System.out.println("Failed: " + t/56.0);
        
    }
    
    public void imageToMatrixProgress(Label processInfo) {
        processInfo.setText("Converting images to a matrix...");
        double unit = 100/files.length;
        for (int i = 0; i < files.length; i++) {
            //Muutetaan kuva kokoon r x r, sekä mustavalkoiseksi
            BufferedImage image = imgProcess.processImage(files[i], size, size);
            //Muutetaan kuva matriisiksi ja matriisi vektoriksi
            double[][] imageMatrix = imgProcess.imageToMatrix(image);
            double[] vector = matop.reshapeToVectorByRow(imageMatrix);
            //Lisätään vektori osaksi matriisia, jossa on kaikki kuvat
            dataMatrix[i] = vector;
        }
    }
    
    public void meanFaceProgress(Label processInfo) {
        processInfo.setText("Calculating meanface...");
        meanface = matop.meanOfMatrixByRow(dataMatrix);
        dataMatrix = matop.subtract(dataMatrix, meanface);
        
        double[][] meanf = new double[size][size];
        int row =-1;
        int col =0;
        for (int i=0; i<meanface.length; i++) {
            if (i % size == 0) {
                row++;
                col = 0;
            }
            meanf[col][row] = meanface[i];
            col++;
        }
        imgProcess.matrixToImage(meanf, size, size, "meanface");
    }
    
    public void eigenvaluesAndVectors(Label processInfo) {
        double[][][] values = matop.eigen(innerproductData);
        //Otetaan ominaisarvot diagonaalista.
        eigenvalues = matop.getDiagonal(values[0]);
        innerEigenvectors = values[1];
        System.out.println("Eigenvector "+innerEigenvectors[0].length+" "+innerEigenvectors.length);
        System.out.println("Datamatrix " +dataMatrix[0].length+" "+dataMatrix.length);
        try {
            //Kerrotaan ominaisvektorit alkuperäisellä matriisilla, jotta saadaan kovarianssimatriisin ominaisarvot.
            covEigenvectors = matop.multiply(dataMatrix, innerEigenvectors);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
            System.out.println("eigen");
        }
    }
    
    public void innerProductProgress(Label processInfo) {
        processInfo.setText("Calculating innerproduct...");
        try {
            //Kerrotaan matriisi sen transpoosilla. Halutaan oikeasti tutkia matriisin transpoosin ja matriisin tuloa.
            //Kuitenkin asetan tähän sen "väärin päin", sillä kuvien käsittelyssä ja valmiiksi toteutetussa Jama-kirjastossa rivit ja sarakkeet ovat eripäin.
            //Tällöin Jama-kirjastoa kutuessa, se käyttää tässä tapauksessa annetun matriisin transpoosia.
            //Näin voidaan tehdä, koska (AA^T)*T = A^T A
            //Kikka tehdään, jotta ei tarvitsisi laskea kovarianssimatriisia.
            innerproductData = matop.multiply(matop.transpose(dataMatrix),dataMatrix);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("inner");
        }
    }
        
    public void normalizeEigenvectorsProcess(Label processInfo) {
        processInfo.setText("Normalizing eigenvectors...");
        covEigenvectors = matop.normalizeVectors(covEigenvectors);
    }
    
    public void sortEigenvectorsByEigenvaluesProcess(Label processInfo) {
        processInfo.setText("Sorting eigenvectors by eigenvalues");
        double[][][] sortedValues = matop.sortEigenvalue(covEigenvectors, eigenvalues);
        double[] principalEigenvalues = sortedValues[1][0];
        principalEigenvectors = sortedValues[0];
    }
    
    public boolean imageIsAFace(double[][] eigenFaces, double[] imageVector, double[] meanFace, double threshold) {
        try {
            double[] meanAdjustedFace = matop.vectorSubtract(imageVector, meanFace);
            double[] weightVector = matop.projectionToFace(eigenFaces, meanAdjustedFace);
            double[] subtraction = matop.vectorSubtract(meanAdjustedFace, weightVector);
            double length = matop.vectorLength(subtraction);
            if (length < threshold) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
}
