package oop6.object2;

import java.util.Random;

public class MatrixGenerator {
    private static final Random RND = new Random();

    public static int[][] generate(int n, int min, int max) {
        long range = (long) max - min + 1;
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = (int) (min + (long) (RND.nextDouble() * range));
        return a;
    }

    public static String toText(int[][] a) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : a) {
            for (int j = 0; j < row.length; j++) {
                if (j > 0) sb.append('\t');
                sb.append(row[j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}