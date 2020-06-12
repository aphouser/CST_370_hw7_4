/*
 * HackerRank link:https://www.hackerrank.com/contests/cst370-su20-hw7/challenges/coin-collection-1-1/submissions/code/1324230678
 * Title: hw7_4.java
 * Abstract: Reads in board with coins from user, outputs max coin collection and path
 * Author: Adam Houser
 * ID: 1144
 * Date: 6/11/2020
 */

import java.util.ArrayList;
import java.util.Scanner;

public class hw7_4 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // read in the number of columns and rows
        int cols = in.nextInt();
        int rows = in.nextInt();

        // create coin matrix (cMatrix) and value matrix (vMatrix)
        int cMatrix[][] = new int[rows][cols];
        int vMatrix[][] = new int[rows][cols];

        // read in cMatrix values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cMatrix[i][j] = in.nextInt();
            }
        }

        in.close();

        // calculate coin values and populate into vMatrix
        findValues(cMatrix, vMatrix, rows, cols);

        // printing
        System.out.println("Max coins:" + vMatrix[rows-1][cols-1]);
        printPath(vMatrix, rows, cols);
    }

    // helper function for debugging
    static void print(int[][] matrix, int rows, int cols)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // this function populates the vMatrix for values of coins picked up
    static void findValues(int[][] cMatrix, int[][] vMatrix, int rows, int cols)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // if a square in cMatrix has 1, add 1 to value in vMatrix
                // if a square in cMatrix has 2, cannot get to square, add a -1 to vMatrix
                // if a square in cMatrix has 0, empty square.  Better than 2, not as good as 1

                // if cMatrix is 2, set vMatrix to -1
                if (cMatrix[i][j] == 2) {
                    vMatrix[i][j] = -1;
                }

                // if i and j are both 0, starting position is 0, there is no upper row
                else if (i == 0 && j == 0) {
                    vMatrix[i][j] = cMatrix[i][j];
                }

                // if i is 0, there is no upper row also applied if i-1 was 2
                else if ((i == 0 && j != 0) || cMatrix[i-1][j] == 2){
                    vMatrix[i][j] = cMatrix[i][j] + vMatrix[i][j-1];

                    // if the prior cell was a 2, can't get here, so override to -1
                    if (vMatrix[i][j-1] == -1) {
                        vMatrix[i][j] = -1;
                    }
                }

                // if j is 0, there is no left row also applied if j-1 was 2
                else if ((i != 0 && j == 0) || cMatrix[i][j-1] == 2) {
                    vMatrix[i][j] = cMatrix[i][j] + vMatrix[i-1][j];
                }

                // otherwise check the greater between "home" square and the other two
                else {
                    vMatrix[i][j] = Math.max(cMatrix[i][j] + vMatrix[i][j-1], cMatrix[i][j] + vMatrix[i-1][j]);
                }

                // if both top and side are -1, can't get here, so set to -1
                if (i > 0 && j > 0 && vMatrix[i-1][j] == -1 && vMatrix[i][j-1] == -1) {
                    vMatrix[i][j] = -1;
                }
            }
        }
    }

    // for output working backwards from bottom right to top left
    static void printPath(int[][] vMatrix, int rows, int cols)
    {
        ArrayList<String> outputList = new ArrayList();

        int i = rows-1;
        int j = cols-1;

        String output = ("(" + cols + "," + rows + ")");
        outputList.add(output);

        while (i >= 0 && j >= 0) {
            // if i and j are both 0, starting position is 0, there is no upper row
            if (i == 0 && j == 0) {
                i--;
                j--;
            }

            // if i is 0, there is no upper row also applied if i-1 was 2
            else if (i == 0 && j != 0){
                String temp = "(" + j + "," + (i+1) + ")->";
                outputList.add(temp);
                j--;
            }

            // if j is 0, there is no left row also applied if j-1 was 2
            else if (i != 0 && j == 0){
                String temp = "(" + (j+1) + "," + i + ")->";
                outputList.add(temp);
                i--;
            }

            // if upper square has higher value than left square
            else if (vMatrix[i-1][j] > vMatrix[i][j-1]){
                String temp = "(" + (j+1) + "," + i + ")->";
                outputList.add(temp);
                i--;
            }

            // otherwise check the greater between "home" square and the other two
            else {
                String temp =  "(" + j + "," + (i+1) + ")->";
                outputList.add(temp);
                j--;
            }
        }
        // add "Path:" to the beginning
        outputList.add("Path:");

        // print from the end to front since we added things backwards
        for (int x = outputList.size()-1; x >= 0; x--) {
            System.out.print(outputList.get(x));
        }
    }
}
