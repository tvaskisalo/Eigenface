/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;

import java.awt.image.BufferedImage;
import java.io.File;
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
    private double[][] innerProductData;
    private double[] meanface;
    private double[] eigenvalues;
    private double[][] eigenvectors;
    private int size;

    public UiLogic(int size) {
        this.size = size;
        imgProcess = new ImageProcessing();
        files = imgProcess.getTrainingImages();
        matop = new MatrixOperations();
        dataMatrix = new double[files.length][size * size];
        innerProductData = new double[files.length][files.length];
    }
    
    
    public void generateEigenface(BorderPane info) {
        Label processInfo = new Label("");
        ProgressBar pb = new ProgressBar(0);
        info.setTop(processInfo);
        info.setCenter(pb);
        imageToMatrixProgress(processInfo, pb);
        meanFaceProgress(processInfo, pb);
        innerProductProgress(processInfo, pb);
        eigenvaluesAndVectors(processInfo, pb);
        normalizeEigenvectorsProcess(processInfo, pb);
        sortEigenvectorsByEigenvaluesProcess(processInfo, pb);
    }
    
    public void imageToMatrixProgress(Label processInfo, ProgressBar pb) {
        processInfo.setText("Converting images to a matrix...");
        pb.setProgress(0);
        double unit = 100/files.length;
        for (int i = 0; i < files.length; i++) {
            //Muutetaan kuva kokoon r x r, sekä mustavalkoiseksi
            BufferedImage image = imgProcess.processImage(files[i], size, size);
            //Muutetaan kuva matriisiksi ja matriisi vektoriksi
            double[][] imageMatrix = imgProcess.imageToMatrix(image);
            double[] vector = matop.reshapeToVectorByRow(imageMatrix);
            //Lisätään vektori osaksi matriisia, jossa on kaikki kuvat
            dataMatrix[i] = vector;
            pb.setProgress(pb.getProgress()+unit);
        }
    }
    
    public void meanFaceProgress(Label processInfo, ProgressBar pb) {
        processInfo.setText("Calculating meanface...");
        pb.setProgress(0);
        meanface = matop.meanOfMatrixByRow(dataMatrix);
        dataMatrix = matop.subtract(dataMatrix, meanface);
        
        double[][] meanf = new double[size][size];
        int col =-1;
        int row =0;
        for (int i=0; i<meanface.length; i++) {
            if (i % size == 0) {
                col++;
                row = 0;
            }
            meanf[col][row] = meanface[i];
            row++;
        }
        imgProcess.matrixToImage(meanf, size, size, "meanface");
        pb.setProgress(100);
    }
    
    public void eigenvaluesAndVectors(Label processInfo, ProgressBar pb) {
        double[][][] values = matop.eigen(dataMatrix);
        //Otetaan ominaisarvot diagonaalista.
        eigenvalues = matop.getDiagonal(values[0]);
        eigenvectors = values[1];
        try {
            //Kerrotaan ominaisvektorit alkuperäisellä matriisilla, jotta saadaan kovarianssimatriisin ominaisarvot.
            eigenvectors = matop.multiply(dataMatrix, eigenvectors);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        pb.setProgress(100);
    }
    
    public void innerProductProgress(Label processInfo, ProgressBar pb) {
        pb.setProgress(0);
        processInfo.setText("Calculating innerproduct...");
        try {
            //Kerrotaan matriisi sen transpoosilla. Halutaan oikeasti tutkia matriisin transpoosin ja matriisin tuloa.
            //Kuitenkin asetan tähän sen "väärin päin", sillä kuvien käsittelyssä ja valmiiksi toteutetussa Jama-kirjastossa rivit ja sarakkeet ovat eripäin.
            //Tällöin Jama-kirjastoa kutuessa, se käyttää tässä tapauksessa annetun matriisin transpoosia.
            //Näin voidaan tehdä, koska (AA^T)*T = A^T A
            //Kikka tehdään, jotta ei tarvitsisi laskea kovarianssimatriisia.
            innerProductData = matop.multiply(dataMatrix, matop.transpose(dataMatrix));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        pb.setProgress(100);
    }
        
    public void normalizeEigenvectorsProcess(Label processInfo, ProgressBar pb) {
        pb.setProgress(0);
        processInfo.setText("Normalizing eigenvectors...");
        eigenvectors = matop.normalizeVectors(eigenvectors);
        pb.setProgress(100);
    }
    
    public void sortEigenvectorsByEigenvaluesProcess(Label processInfo, ProgressBar pb) {
        pb.setProgress(0);
        processInfo.setText("Sorting eigenvectors by eigenvalues");
        double[][][] sortedValues = matop.sortEigenvalue(eigenvectors, eigenvalues);
        double[] principalEigenvalues = sortedValues[1][0];
        double[][] principalEigenvectors = sortedValues[0];
        pb.setProgress(100);
    }
    
    
}
