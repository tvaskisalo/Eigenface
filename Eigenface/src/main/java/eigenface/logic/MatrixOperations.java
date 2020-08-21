/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eigenface.logic;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/**
 * Luokalla voidaan laskea joitain matriisien peruslaskutoimituksia, sekä muuttamaan
 * matriisin vektoriksi ja vähentämään vektorista keskiarvon, sekä tekemään vähän kaikkea muutakin.
 * 
 */
public class MatrixOperations {
    
    /**
     * Metodiin annetaan kaksi matriisia ja se palauttaa matriisien erotuksen.
     * Metodi heittää poikkeuksen, jos matriisien rivien tai sarakkeiden määrä on eri.
     * 
     * @param matrixA Ensimmäinen matriisi
     * @param matrixB Toinen matriisi
     * @return Metodi palauttaa matriisien erotuksen
     * @throws Exception Toistaiseksi heittää geneerisen poikkeuksen, jos matriisit ovat eri muotoisia.
     */
    public double[][] subtract(double matrixA[][], double matrixB[][]) throws Exception {
        if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
            throw new Exception("Incorrect matrix dimensions");
        }
        double[][] subtraction = new double[matrixA.length][matrixA[0].length];
        
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                subtraction[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
        
        return subtraction;
    }
    /**
     * Metodiin annetaan matriisi ja arvo. Metodi vähentää jokaisesta matriisin solusta annetun arvon.
     * @param matrix Matriisi
     * @param value Vähennettävä arvo
     * @return Metodi palauttaa matriisin, josta on vähennettu annettu arvo.
     */
    public double[][] subtract(double matrix[][], double[] value) {
        double[][] subtraction = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                subtraction[i][j] = matrix[i][j] - value[j];
            }
        }
        
        return subtraction;
    }
    /**
     * Metodi vähentää kaikista matriisin alkioista annetun luvun
     * @param matrix Matriisi
     * @param value Vähennettävä luku
     * @return Palauttaa matriisin, josta on vähennetty annettu luku.
     */
    public double[][] subtract(double matrix[][], double value) {
        double[][] subtraction = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                subtraction[i][j] = matrix[i][j] - value;
            }
        }
        
        return subtraction;
    }
    /**
     * Metodiin annetaan kaksi vektoria ja se palauttaa niiden erotuksen ja heittää poikkeuksen, jos vektorit ovat eri pituisia.
     * @param vectorA Vektori, josta vähennetään
     * @param vectorB Vektori, jolla vähennetään
     * @return Palauttaa erotuksen
     * @throws Exception Vektorit ovat eri pituiset
     */
    
    public double[] vectorSubtract(double vectorA[], double vectorB[]) throws Exception {
        if (vectorA.length != vectorB.length) {
            throw new Exception("incorrect vector lengths");
        }
        
        double[] subtraction = new double[vectorA.length];
        for (int i = 0; i < vectorA.length; i++) {
            subtraction[i] = vectorA[i] - vectorB[i];
        }
        
        return subtraction;
    }
    
    /**
     * Metodiin annetaan kaksi vektoria ja se palauttaa niiden summan ja heittää poikkeuksen, jos vektorit ovat eri pituisia.
     * @param vectorA Ensimmäinen vektori
     * @param vectorB Toinen vektori
     * @return Palauttaa vektorien summan.
     * @throws Exception Vektorit ovat eri pituiset
     */
    public double[] vectorAdd(double vectorA[], double vectorB[]) throws Exception {
        if (vectorA.length != vectorB.length) {
            throw new Exception("incorrect vector lengths");
        }
        
        double[] subtraction = new double[vectorA.length];
        for (int i = 0; i < vectorA.length; i++) {
            subtraction[i] = vectorA[i] + vectorB[i];
        }
        
        return subtraction;
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
     * Metodilla voidaan tarkistaa, ovatko vektorit samat. 
     * Tein sivussa ns. "varmuuden vuoksi", jotta ei tarvitse käyttää Arrays-luokkaa.
     * @param vectorA Ensimmäinen tarkasteltava vektori
     * @param vectorB Toinen tarkasteltava vektori
     * @return Palauttaa true, jos matriisit ovat samat, muulloin false.
     */
    public boolean vectorEquals(double[] vectorA, double[] vectorB) {
        if (vectorA.length != vectorB.length) {
            return false;
        }
        for (int i = 0; i < vectorA.length; i++) {
            if (vectorA[i] != vectorB[i]) {
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
        double[][] result = new double[matrixB.length][matrixA[0].length];
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
    /**
     * Metodi laskee annetun matriisin ja vektorin tulon kertomalla vektorin matriisilla.
     * @param matrix Kerrottava matriisi
     * @param vector Kerrottava vektori
     * @return Palauttaa matriisitulon matrix*vector
     * @throws Exception Heittää poikkeuksen, jos matriisin sarakkeiden määrä ja vektorin pituus on eri.
     */
    public double[] multiply(double matrix[][], double vector[]) throws Exception {
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
     * Metodi laskee annetun vektorin ja matriisin tulon kertomalla vektorin matriisilla.
     * @param vector Kerrottava vektori
     * @param matrix Kerrottava matriisi
     * @return Palauttaa matriisitulon vector*matrix
     * @throws Exception Heittää poikkeuksen, jos matriisin rivien määrä ja vektorin pituus on eri.
     */
    public double[] multiply(double vector[], double matrix[][]) throws Exception {
        if (matrix[0].length != vector.length) {
            throw new Exception("Incorrect dimensions");
        }
        double[] result = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            double value = 0;
            for (int k = 0; k < vector.length; k++) {
                value += matrix[i][k] * vector[k];
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
     * Metodi jakaa jokaisen vektorin alkion annetulla luvulla
     * @param vector Jaettava vektori
     * @param value Jakaja
     * @return Palauttaa jaetun vektorin
     * @throws Exception Heittää poikkeuksen, jos arvo on nolla.
     */
    public double[] divideVectorWithValue(double[] vector, double value) throws Exception {
        if (value == 0) {
            throw new Exception("Can't divide by zero");
        }
        double[] returnVec = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            returnVec[i] = vector[i] / value;
        }
        
        return returnVec;
    }
    
    /**
     * Metodi vähentää annetun vektorin termeistä vektroin termien keskiarvon.
     * Tämä metodi on todennäköisesti turha. 
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
    /**
     * Metodi laskee matriisin arvojen keskiarvon, eli summaa kaikki matriisin alkiot ja jakaa ne alkioiden määrällä.
     * @param matrix Matriisi
     * @return Palauttaa matriisin arvojen keskiarvon.
     */
    public double[] meanOfMatrixByRow(double[][] matrix) {
        int count = matrix.length;
        double[] mean = new double[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                mean[j] += matrix[i][j];
            }
        }
        for (int k = 0; k < mean.length; k++) {
            mean[k] = mean[k] / count;
        }
        return mean;
    }
    /**
     * Metodi ottaa argumenttina matriisin ja palauttaa sen transpoosin.
     * @param matrix Transponoitava matriisi
     * @return Matriisin transpoosi
     */
    public double[][] transpose(double[][] matrix) {
        double[][] transpose = new double[matrix[0].length][matrix.length];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        
        return transpose;
    }
    
    /**
     * Metodilla voidaan laskea annetun matriisin ominaisarvot ja vektorit.
     * Tällä hetkellä käytän valmista kirjastoa, mutta tarkoitus on tehdä oma toeutus seuravilla viikoilla.
     * @param matrix Annettu matriisi
     * @return Palauttaa kolmiulotteisen matriisin, jonka ensimmäinen alkio on diagonaalimatriisi ominaisarvoista 
     * ja toinen alkio on matriisi, jonka sarakkeet ovat ominaisvektorit.
     */
    public double[][][] eigen(double[][] matrix) {
        EigenvalueDecomposition eig = new EigenvalueDecomposition(new Matrix(matrix));
        double[][] eigValues = transpose(eig.getD().getArray());
        double[][] eigVectors = transpose(eig.getV().getArray());
        double[][][] values = {eigValues, eigVectors};
        return values;
    }
    
    /**
     * Metodi ottaa neliömatriisin diagonaalilla olevat arvot ja paluttaa ne vektorina.
     * @param matrix Annettu neliömatriisi
     * @return Diagonaalilla olevat arvot vektorina.
     */
    public double[] getDiagonal(double[][] matrix) {
        double[] diagonal = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == j) {
                    diagonal[i] = matrix[i][j];
                }
            }
        }
        return diagonal;
    }
    /**
     * Metodilla järjestetään ominaisarvot laskevaan järjestykseen.
     * Sitten annettu matriisi, jonka sarakkeet ovat vastaavat ominaisvektorit muutetaan samaan järjestykseen.
     * Metodi vaatii refaktorointia ja optimointia, sillä se on hidas, epäselvä ja pitkä.
     * @param matrix Matriisi, jonka sarakkeet ovat ominaisvektoreita.
     * @param vector Vektori, jonka arvot ovat omainaisarvot.
     * @return Palauttaa kolmiulotteisen matriisin, jonka ensimmäinen alkio on järjestetty matriisi ja toinen on taulukko, jossa on vektori ominaisarvoista.
     */
    public double[][][] sortEigenvalue(double[][] matrix, double[] vector) {
        double[] sortedV = vector;
        for (int i = 1; i < vector.length; i++) {
            int j = i - 1;
            while (j >= 0 && sortedV[j] < sortedV[j + 1]) {
                double a = sortedV[j];
                sortedV[j] = sortedV[j + 1];
                sortedV[j + 1] = a;
                j -= 1;
            }
        }
        int principal = calculatePrincipal(sortedV, 0.95);
        double[][] sortedM = new double[principal][matrix[0].length];
        double[] eigen = new double[principal];
        for (int k = 0; k < principal; k++) {
            double value = sortedV[k];
            eigen[k] = value;
            int index = 0;
            for (int g = 0; g < vector.length; g++) {
                if (vector[g] == value) {
                    index = g;
                    break;
                }
            }
            sortedM[k] = matrix[index];
        }
        
        return new double[][][] {sortedM, {eigen}};
    }
    /**
     * Metodilla voidaan laskea, mitkä suurimmat ominaisarvot tarvitaan, 
     * jotta se vastaa annetun prosentin ominaisarvojen summasta
     * @param sortedEigenvalues Vektori, jossa on halutut ominaisarvot laskevassa järjestyksessä.
     * @param thresHold Suhde, kuinka paljon ominaisarvioista halutaan.
     * @return Palauttaa kokonaisluvun, kuinka monta ominaisarvoa tarvitaan.
     */
    public int calculatePrincipal(double[] sortedEigenvalues, double thresHold) {
        double sumAll = 0;
        for (int i = 0; i < sortedEigenvalues.length; i++) {
            sumAll += sortedEigenvalues[i];
        }
        double sumPartial = 0;
        for (int j = 0; j < sortedEigenvalues.length; j++) {
            sumPartial += sortedEigenvalues[j];
            if (sumPartial / sumAll > thresHold) {
                return j + 1;
            }
        }
        
        return sortedEigenvalues.length;
    }
    /**
     * Metodin avulla voidaan normalisoida matriisin sarakkeet ja palautetaan normitettu matriisi. 
     * Metodi vaatii hieman refaktorointia.
     * @param matrix Matriisi, jonka sarakkeet halutaan normittaa.
     * @return Palauttaa normitetun matriisin.
     */
    public double[][] normalizeVectors(double[][] matrix) {
        double[][] normalized = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                sum += matrix[i][j] * matrix[i][j];
            }
            double length = Math.sqrt(sum);
            for (int k = 0; k < matrix[0].length; k++) {
                normalized[i][k] = matrix[i][k] / length;
            }
        }
        
        return normalized;
    }
    
    /**
     * Metodiin annetaan vektori ja se palauttaa vektorin pituuden
     * @param vector Haluttu vektori
     * @return vektorin pituus
     */
    public double vectorLength(double vector[]) {
        double dotProduct = 0;
        try {
            dotProduct = dotproduct(vector, vector);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return Math.sqrt(dotProduct);
    }
    
    /**
     * Metodi laskee annetun vektorin ja luvun tulon
     * @param vector Haluttu vektori
     * @param value Luku, jolla kerrotaan
     * @return Palauttaa luvulla skaalatun vektorin
     */
    public double[] vectorMultiply(double vector[], double value) {
        double[] multiplication = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            multiplication[i] = vector[i] * value;
        }
        return multiplication;
    }
    
    /**
     * Metodi projektoi vektorin meanAdjustedFace, joka on kuvavektori, josta on otettu pois keskiarvoinen kuvavektori,
     * ominaisvektoreiden viritämälle vektoriavaruudelle.
     * @param eigenvectors Matriisi, jossa on sarakkeina ominaisarvot
     * @param meanAdjustedFace kuvavektori, josta on otettu pois keskiarvoinen kuvavektori
     * @return Palauttaa projektion
     */
    public double[] projectionToFace(double[][] eigenvectors, double[] meanAdjustedFace) {
        double[] value = new double[eigenvectors[0].length];
        for (int i = 0; i < eigenvectors.length; i++) {
            try {
                value = vectorAdd(value, vectorMultiply(eigenvectors[i], meanAdjustedFace[i]));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return value;
    }
    
    
    /**
     * Metodi vähentää tietyn ominaisarvo ja -vektorin vaikutusta matriisissa, 
     * jotta se ei tule vastaan uudelleen power-iteroinnissa.
     * @param matrix Tutkittava matriisi
     * @param eigenvalue Matriisin ominaisarvo
     * @param eigenvector Vastaava ominaisvektori
     * @return Palauttaa matriisin, josta on vähennetty ominaisarvon vaikutusta
     */
    public double[][] reduceEigenPairImpact(double matrix[][], double eigenvalue, double eigenvector[]) {
        double length = vectorLength(eigenvector);
        double dotproduct = length * length;
        return subtract(matrix, eigenvalue * dotproduct);
    }
    /**
     * Metodi etsii suurimman ominaisarvon ja sitä vastaavan ominaisvektorin.
     * @param matrix Tutkittava matriisi
     * @param epsilon Raja, kuinka lähellä ominaisvektorin pitää olla
     * @param maxIterations Maksimi määrä kuinka monta iteraatiota tehdään.
     * @return Palauttaa matriisin, jossa ensimmäisellä sarakkeella on ominaisvektori ja toisella ominaisarvo.
     */
    public double[] powerIterate(double matrix[][], double epsilon, int maxIterations) {
        double[] x0 = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            x0[i] = 1;
        }
        try {
            double[] x1 = divideVectorWithValue(multiply(matrix, x0), vectorLength(x0));
            int iterations = 1;
            while (vectorLength(vectorSubtract(x1, x0)) > epsilon && iterations < maxIterations) {
                x0 = x1;
                x1 = divideVectorWithValue(multiply(matrix, x0), vectorLength(x0));
                iterations++;
            }
            return x1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
        
        return null;
    }
    /**
     * Metodi laskee ominaisarvon matriisin ja vastaavan ominaisvektorin avulla
     * @param matrix Tutkittava matriisi
     * @param eigenvector Ominaisarvon ominaisvektori
     * @return Palauttaa ominaisarvon
     */
    public double calculateEigenvalue(double matrix[][], double eigenvector[]) {
        try {
            double[] xTm = multiply(eigenvector, matrix);
            double eigenValue = dotproduct(xTm, eigenvector);
            return eigenValue;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
    /**
     * Metodi laskee kahden vektorin pistetulon
     * @param vectorA Ensimmäinen vektori
     * @param vectorB Toinen vektori
     * @return Palauttaa pistetulon
     * @throws Exception Heittää poikkeuksen, jos vektorit ovat eri kokoisia.
     */
    public double dotproduct(double vectorA[], double vectorB[]) throws Exception {
        if (vectorA.length != vectorB.length) {
            throw new Exception("Vector lengths are different");
        }
        double dot = 0;
        for (int i = 0; i < vectorA.length; i++) {
            dot += vectorA[i] * vectorB[i];
        }
        return dot;
    }
    /**
     * Metodi pyöristää kaikki annetun vektorin alkiot neljän decimaalin tarkkuuteen
     * @param vector Pyöreistettävä vektori
     * @return Pyöristetty vektori.
     */
    public double[] roundVectorTo4Decimals(double[] vector) {
        double[] roundedVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            roundedVector[i] = Math.round(vector[i] * 10000) / 10000.0;
        }
        
        return roundedVector;
    }
}


