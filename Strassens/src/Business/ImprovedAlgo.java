/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.Random;

/**
 *
 * @author sojit
 */
public class ImprovedAlgo {
    public void run(int brkPoint){
		int size = 1;
		Random rand = new Random();		
		while(size < 2048){
			
			size *= 2;
			
			int A[][] = new int[size][size];
			int B[][] = new int[size][size];
			int C[][];
			for(int i = 0; i < A.length; i++){
				for(int j = 0; j < A.length; j++){
					A[i][j] = rand.nextInt(200)+100;
					B[i][j] = rand.nextInt(200)+100;
				}
			}
			
			long time = System.currentTimeMillis();
			C = traditionalMatrixMultiplication(A, B);
			long t1 = System.currentTimeMillis() - time;
			
			time = System.currentTimeMillis();
			int reqLength = checkReqLength(A.length);
			
				if(reqLength > A.length){
					A = convertMatrixInPowerOf2(A, reqLength);
					B = convertMatrixInPowerOf2(B, reqLength);
				}
				C = strassenForMatrix(A, B, size, brkPoint);

			long t3 = System.currentTimeMillis() - time;
			
			time = System.currentTimeMillis();
			C = strassenForMatrix(A, B, size, -1);
			long t2 = System.currentTimeMillis() - time;
			
			System.out.println("_________________\nat: "+size+"\ntime by traditional method: "+t1+"ms\ntime by Strassen's Algorithm: "+t2+"ms\ntime by Improved Algorithm: "+t3+"ms");
		}
		
	}

    private int[][] traditionalMatrixMultiplication(int[][] A, int[][] B) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int checkReqLength(int length) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    private int[][] strassenForMatrix(int[][] A, int[][] B, int size, int breakPoint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int[][] convertMatrixInPowerOf2(int[][] A, int requiredLength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
