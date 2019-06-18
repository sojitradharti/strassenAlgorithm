/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kamini Prakash
 */
public class Algorithms {

    public int[][] traditionalMatrixMultiplication(int[][] A, int[][] B) {
        int C[][] = new int[A.length][A.length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                for (int k = 0; k < A.length; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }

    public void run() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int n = scanner.nextInt();
        int A[][] = new int[n][n];
        int B[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                B[i][j] = scanner.nextInt();
            }
        }
        scanner.close();

        long millis = System.nanoTime();
        int C[][] = traditionalMatrixMultiplication(A, B);
        System.out.println("Time taken by Traditional Multiplication : " + (System.nanoTime() - millis) + " ns\n");
        millis = System.nanoTime();
        int requiredLength = checkReqLength(A.length);

        if (requiredLength > A.length) {
            A = convertMatrixInPowerOf2(A, requiredLength);
            B = convertMatrixInPowerOf2(B, requiredLength);
        }

        C = strassenForMatrix(A, B, C.length, -1);//passing -1 as breakpoint for Pure strassen's algorithm
        System.out.println("Time taken by Strassen's Multiplication : " + (System.nanoTime() - millis) + " ns\n");

        System.out.print("Multiplication Result: \n");
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                System.out.print(String.format(" %5d", C[i][j]));
            }
            System.out.print("\n");
        }
        int breakPoint = findBreakPoint();

        ImprovedAlgo newObj = new ImprovedAlgo();
        newObj.run(breakPoint);

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

    public int[][] strassenForMatrix(int[][] A, int[][] B, int orignalLength, int brkPoint) {
        int D[][] = new int[orignalLength][orignalLength];

        int C[][] = strassen(A, B, A.length / 2, brkPoint);
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D.length; j++) {
                D[i][j] = C[i][j];
            }
        }

        return D;
    }
    
    private int findBreakPoint() {
        Queue<Integer> bpQueue = new LinkedList<>();
        int size = 1;
        Random rand = new Random();
        while (size <= 16384) {
            size *= 2;
            int A[][] = new int[size][size];
            int B[][] = new int[size][size];
            int C[][];
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A.length; j++) {
                    A[i][j] = rand.nextInt(200) + 100;
                    B[i][j] = rand.nextInt(200) + 100;
                }
            }

            String readings = String.format("%10d units", size);
            long nanos = System.currentTimeMillis();
            if (size <= 2048) {
                C = traditionalMatrixMultiplication(A, B);
                long t1 = System.currentTimeMillis() - nanos;
                String tradTime = String.format("%10d ms", t1);
                readings += tradTime;
            } else {
                String tradTime = String.format("%10s   ", "NA");
                readings += tradTime;
            }

            int brkPoint = 16;

            int reqLength = checkReqLength(A.length);
            if (reqLength > A.length) {
                A = convertMatrixInPowerOf2(A, reqLength);
                B = convertMatrixInPowerOf2(B, reqLength);
            }

            while (brkPoint < 1024) {
                nanos = System.currentTimeMillis();
                C = strassenForMatrix(A, B, size, brkPoint);
                long t2 = System.currentTimeMillis() - nanos;
                String time = String.format("%10d ms", t2);
                readings += time;
                brkPoint *= 2;
            }

            System.out.println(readings);
        }
        int breakPoint = 0;
        while (!bpQueue.isEmpty()) {
            breakPoint += bpQueue.remove();
        }
        return breakPoint / 5;

    }

    

}
