package com.example.flowerrec;

import java.io.BufferedReader;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;  
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/*************************************************************************
 *  Compilation:  javac Matrix.java
 *  Execution:    java Matrix
 *
 *  A bare-bones collection of static methods for manipulating
 *  matrices.
 *
 *************************************************************************/

public class Matrix {
	
	static String TAG = "TEST";

    // return a random m-by-n matrix with values between 0 and 1
    public static double[][] random(int m, int n) {
        double[][] C = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = Math.random();
        return C;
    }

    // return n-by-n identity matrix I
    public static double[][] identity(int n) {
        double[][] I = new double[n][n];
        for (int i = 0; i < n; i++)
            I[i][i] = 1;
        return I;
    }

    // return x^T y
    public static double dot(double[] x, double[] y) {
        if (x.length != y.length) throw new RuntimeException("Illegal vector dimensions.");
        double sum = 0.0;
        for (int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }

    // return C = A^T
    public static double[][] transpose(double[][] A) {
        int m = A.length;
        int n = A[0].length;
        double[][] C = new double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[j][i] = A[i][j];
        return C;
    }

    // return C = A + B
    public static double[][] add(double[][] A, double[][] B) {
        int m = A.length;
        int n = A[0].length;
        double[][] C = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    // return C = A - B
    public static double[][] subtract(double[][] A, double[][] B) {
        int m = A.length;
        int n = A[0].length;
        double[][] C = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    // return C = A * B
    public static double[][] multiply(double[][] A, double[][] B) {
        int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = A[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] C = new double[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++)
                    C[i][j] += (A[i][k] * B[k][j]);
        return C;
    }

    // matrix-vector multiplication (y = A * x)
    public static double[] multiply(double[][] A, double[] x) {
    	
    	
    	
    	//double [] x_n = Matrix.normalise(x);
    	
    	//Log.d(TAG,"************");
    	//Log.d(TAG,"The x 0 is: "+x[0]);
		//Log.d(TAG,"The x 1 is: "+x[1]);
		//Log.d(TAG,"************");
    	
    	
        int m = A.length;
        int n = A[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += (A[i][j] * x[j]);
        return y;
    }


    // vector-matrix multiplication (y = x^T A)
    public static double[] multiply(double[] x, double[][] A) {
        int m = A.length;
        int n = A[0].length;
        if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                y[j] += (A[i][j] * x[i]);
        return y;
    

   
    }
    
    
 public static double [][] loadFromFile(String fname) throws Exception {
    	
    	BufferedReader in = null;  
        int rows = 0;  
        int columns = 0;  
        double [][] matrix = null; 
    	
        try {  
            String filepath = fname;  
            int lineNum = 0;  
            int row=0;  
            in = new BufferedReader(new FileReader(filepath));  
            String line = null;  
            while((line=in.readLine()) !=null) {  
                lineNum++;  
                if(lineNum==1) {  
                    rows = Integer.parseInt(line);  
                } else if(lineNum==2) {  
                    columns = Integer.parseInt(line);  
                    matrix = new double[rows][columns];  
                } else {  
                    String [] tokens = line.split(" ");  
                    for (int j=0; j<tokens.length; j++) {  
                        matrix[row][j] = Double.parseDouble(tokens[j]);  
                    }  
                    row++;  
                }  
            }  
        } catch (Exception ex) {  
            System.out.println("The code throws an exception");  
            System.out.println(ex.getMessage());  
        } finally {  
            if (in!=null) in.close();  
        }
        return matrix;
    }
 
 
 
 
 public static int theRows = 0;
 
 
 
 public static double [][] loadFromFileXML(String fname) throws Exception {
	 
	 double [][] matrix = null;
	 
	 
	 InputStream inputStream = new FileInputStream(new File(fname));
	 String s = readTextFile(inputStream);
	 
	 String rows_string = FlowerUtils.getWithinTags(s,"rows");
	 int rows = Integer.parseInt(rows_string);
	 theRows = rows;
	 
	 
	 String cols_string = FlowerUtils.getWithinTags(s,"cols");
	 int cols = Integer.parseInt(cols_string);
	 
	 matrix = new double[rows][cols]; 
	 
	 String line = FlowerUtils.getWithinTags(s,"data");
	 String [] tokens = line.substring(1, line.length()).split(" ");
	 
	 int row = 0;
	 
	 int cont = 0;
	 
	 while(row < rows){
	 
	 
	 for (int j=0; j<cols; j++) {  
         matrix[row][j] = Double.parseDouble(tokens[cont]);
         cont++;
     }  
     row++;  
	 }
	 
	 
 	
 	return matrix;
 }
 
 
 
 
 public static String readTextFile(InputStream fis) {
	 
	 
	
	 StringBuilder fileContent = new StringBuilder("");

	 BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
	 String readS = new String();;
	 
	
	 try {
		while ((readS = br.readLine()) != null) 
		 { 
		   fileContent.append(readS);
		   readS = new String();
		 }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 String t =  fileContent.toString();
	 
	 String s = t.replaceAll("\\s+"," ");
	 
	 return s;
	 
	 
	 
	}
 
 
 
 
 public static double []  normalise (double [] orig_vec){

		double [] ret_vec = new double[orig_vec.length];

		double mag = calc_mag(orig_vec);
		//Log.d(TAG,"The mag is: "+mag);
		//Log.d(TAG,"The ori is: "+orig_vec[0]);

		for (int i = 0; i< orig_vec.length;i++){
			
			ret_vec[i] = orig_vec[i] / mag ;
			//Log.d(TAG,""+ret_vec[i]);
			

		}

		return ret_vec;

	}


public static double calc_mag (double [] orig_vec){

		double mag = 0;

		for (int i = 0; i<orig_vec.length; i++){

			mag += orig_vec[i] * orig_vec[i];
		}
		
		mag = Math.sqrt(mag);
		
		
		return mag;


	}
 
 
	
 
 
 
 
 
    
    
 // getting the maximum value
    public static int getMaxValue(double[] array){
    	  int maxIndex = 0;
          double maxValue = array[0];  
          for(int i=1;i < array.length;i++){  
          if(array[i] > maxValue){  
          maxValue = array[i];
          maxIndex = i;

             }  
         }  
                 return maxIndex;  
    }  
    
    
    public static double[][] transposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
    
    
}