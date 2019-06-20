/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import UserInterface.mainJFrame;
import java.util.Random;

/**
 *
 * @author sojit
 */
public class ImprovedAlgo {

    mainJFrame mainframe;
    public ImprovedAlgo(mainJFrame aThis) {
        mainframe = aThis;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void run(int brkPoint){
		int size = 1;
		Random rand = new Random();		
		while(size < 1024){
			
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
                         // graph for traditional
			  mainframe.getdatatraditional().addValue(t1, "By Traditional Algo", String.valueOf(size));
                          mainframe.updateTraditional();
                          // graph for traditional
			time = System.currentTimeMillis();
			int reqLength = checkReqLength(A.length);
			
				if(reqLength > A.length){
					A = convertMatrixInPowerOf2(A, reqLength);
					B = convertMatrixInPowerOf2(B, reqLength);
				}
				C = StrassenMatrixAlgo(A, B, size, brkPoint);

			long t3 = System.currentTimeMillis() - time;
			 // graph for stressen
			  mainframe.getDataImproved().addValue(t3, "By Improved Algorithm", String.valueOf(size));
                          mainframe.updateImproved();
                           // graph for stressen
			time = System.currentTimeMillis();
			C = StrassenMatrixAlgo(A, B, size, -1);
			long t2 = System.currentTimeMillis() - time;
			 // graph for imroved algo
			  mainframe.getDataStrassen().addValue(t2, "By Strassen's Algorithm", String.valueOf(size));
                          mainframe.updateStrassen();
			System.out.println("\nFor Matrix Size "+size+",\nTime by traditional method:          Time by Strassen's Algorithm:          Time by Improved Algorithm:");
                        System.out.println("          "+t1 +"ms                                   "+t2+"ms                                      "+t3+"ms          ");
                         System.out.println("\n");
                         
                        
		}
		
	}

    private int[][] traditionalMatrixMultiplication(int[][] A, int[][] B) {
       int C[][] = new int[A.length][A.length];
		
		for(int i = 0; i < A.length; i++){
			for(int j = 0; j < A.length; j ++){
				for(int k = 0; k < A.length; k++){
					C[i][j] += A[i][k]*B[k][j];
				}
			}
		}
		
		return C;
	
    }

    private int checkReqLength(int length) {
       int reqLength = 0;
		int div = 1;
		while(length % div != length){
			div *= 2;
			if(div == length)
				return div;
		}
		reqLength = div;		
		return reqLength;
    }


    private int[][] StrassenMatrixAlgo(int[][] A, int[][] B, int OrgnlLength, int breakPoint) {
       int R[][] = new int[OrgnlLength][OrgnlLength];
		
		int C[][] = strassen(A, B, A.length/2, breakPoint);
		for(int i = 0; i < R.length; i++){
			for(int j = 0; j < R.length; j++){
				R[i][j] = C[i][j];
			}
		}
		
		return R;
    }

    private int[][] convertMatrixInPowerOf2(int[][] A, int l) {
        int B[][] = new int[l][l];
		
		for(int i = 0; i < B.length; i++){
			for(int j = 0; j< B.length; j++){
				if(i < A.length && j < A.length){
					B[i][j] = A[i][j];
				}else{
					B[i][j] = 0;
				}
			}
		}
		
		return B;
    }

    public int[][] strassen(int[][] A, int[][] B, int divide, int brkPoint) {
        int C[][] = new int[A.length][A.length];

        if (divide == 0) {
            C[0][0] = A[0][0] * B[0][0];
            return C;
        }

        int a[][] = new int[divide][divide];
        int d[][] = new int[divide][divide];

        int e[][] = new int[divide][divide];
        int h[][] = new int[divide][divide];

        int S1[][] = new int[divide][divide];
        int S2[][] = new int[divide][divide];
        int S3[][] = new int[divide][divide];
        int S4[][] = new int[divide][divide];
        int S5[][] = new int[divide][divide];
        int S6[][] = new int[divide][divide];
        int S7[][] = new int[divide][divide];
        int S8[][] = new int[divide][divide];
        int S9[][] = new int[divide][divide];
        int S10[][] = new int[divide][divide];

        for (int i = 0; i < divide; i++) {
            for (int j = 0; j < divide; j++) {
                a[i][j] = A[i][j];
                d[i][j] = A[divide + i][divide + j];
                e[i][j] = B[i][j];
                h[i][j] = B[divide + i][divide + j];

                S1[i][j] = B[i][divide + j] - B[divide + i][divide + j];
                S2[i][j] = A[i][j] + A[i][divide + j];
                S3[i][j] = A[divide + i][j] + A[divide + i][divide + j];
                S4[i][j] = B[divide + i][j] - B[i][j];
                S5[i][j] = A[i][j] + A[divide + i][divide + j];
                S6[i][j] = B[i][j] + B[divide + i][divide + j];
                S7[i][j] = A[i][divide + j] - A[divide + i][divide + j];
                S8[i][j] = B[divide + i][j] + B[divide + i][divide + j];
                S9[i][j] = A[i][j] - A[divide + i][j];
                S10[i][j] = B[i][j] + B[i][divide + j];
            }
        }

        int P[][];
        int Q[][];
        int R[][];
        int S[][];
        int T[][];
        int U[][];
        int V[][];

        if (A.length <= brkPoint) {
            P = traditionalMatrixMultiplication(a, S1);
            Q = traditionalMatrixMultiplication(S2, h);
            R = traditionalMatrixMultiplication(S3, e);
            S = traditionalMatrixMultiplication(d, S4);
            T = traditionalMatrixMultiplication(S5, S6);
            U = traditionalMatrixMultiplication(S7, S8);
            V = traditionalMatrixMultiplication(S9, S10);
        } else {
            P = strassen(a, S1, divide / 2, brkPoint);
            Q = strassen(S2, h, divide / 2, brkPoint);
            R = strassen(S3, e, divide / 2, brkPoint);
            S = strassen(d, S4, divide / 2, brkPoint);
            T = strassen(S5, S6, divide / 2, brkPoint);
            U = strassen(S7, S8, divide / 2, brkPoint);
            V = strassen(S9, S10, divide / 2, brkPoint);
        }

        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P.length; j++) {
                C[i][j] = T[i][j] + S[i][j] - Q[i][j] + U[i][j];
                C[i][P.length + j] = P[i][j] + Q[i][j];
                C[P.length + i][j] = R[i][j] + S[i][j];
                C[P.length + i][P.length + j] = P[i][j] + T[i][j] - R[i][j] - V[i][j];
            }
        }
        return C;
    }
	
}
