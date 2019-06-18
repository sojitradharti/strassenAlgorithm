/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.File;
import java.io.FileNotFoundException;
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

        System.out.print("Multiplication Result: \n");
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                System.out.print(String.format(" %5d", C[i][j]));
            }
            System.out.print("\n");
        }

    }

}
