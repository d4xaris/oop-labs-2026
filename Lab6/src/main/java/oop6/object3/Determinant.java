package oop6.object3;

import java.math.BigInteger;

public class Determinant {
    public static int[][] parse(String text) {
        String[] lines = text.trim().split("\\R");
        int n = lines.length;
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] t = lines[i].trim().split("\\s+");
            if (t.length != n) throw new IllegalArgumentException("Матриця не квадратна");
            for (int j = 0; j < n; j++) a[i][j] = Integer.parseInt(t[j]);
        }
        return a;
    }

    // Алгоритм Барейса
    public static BigInteger det(int[][] a) {
        int n = a.length;
        BigInteger[][] m = new BigInteger[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) m[i][j] = BigInteger.valueOf(a[i][j]);

        BigInteger prev = BigInteger.ONE;
        int sign = 1;

        for (int k = 0; k < n - 1; k++) {
            if (m[k][k].signum() == 0) {
                int s = -1;
                for (int i = k + 1; i < n; i++) {
                    if (m[i][k].signum() != 0) { s = i; break; }
                }
                if (s == -1) return BigInteger.ZERO;
                BigInteger[] tmp = m[k];
                m[k] = m[s];
                m[s] = tmp;
                sign = -sign;
            }
            for (int i = k + 1; i < n; i++) {
                for (int j = k + 1; j < n; j++) {
                    m[i][j] = m[i][j].multiply(m[k][k])
                            .subtract(m[i][k].multiply(m[k][j]))
                            .divide(prev);
                }
            }
            prev = m[k][k];
        }
        BigInteger d = m[n - 1][n - 1];
        return sign == 1 ? d : d.negate();
    }
}