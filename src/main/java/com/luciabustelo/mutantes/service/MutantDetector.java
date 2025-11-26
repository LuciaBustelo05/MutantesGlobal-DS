package com.luciabustelo.mutantes.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    public boolean isMutant(String[] dna) {
        char[][] matrix = toMatrix(dna);
        int count = 0;

        int n = matrix.length;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {

                if (c + 3 < n &&
                        matrix[r][c] == matrix[r][c+1] &&
                        matrix[r][c] == matrix[r][c+2] &&
                        matrix[r][c] == matrix[r][c+3]) {
                    if (++count > 1) return true;
                }

                if (r + 3 < n &&
                        matrix[r][c] == matrix[r+1][c] &&
                        matrix[r][c] == matrix[r+2][c] &&
                        matrix[r][c] == matrix[r+3][c]) {
                    if (++count > 1) return true;
                }

                if (r + 3 < n && c + 3 < n &&
                        matrix[r][c] == matrix[r+1][c+1] &&
                        matrix[r][c] == matrix[r+2][c+2] &&
                        matrix[r][c] == matrix[r+3][c+3]) {
                    if (++count > 1) return true;
                }

                if (r - 3 >= 0 && c + 3 < n &&
                        matrix[r][c] == matrix[r-1][c+1] &&
                        matrix[r][c] == matrix[r-2][c+2] &&
                        matrix[r][c] == matrix[r-3][c+3]) {
                    if (++count > 1) return true;
                }
            }
        }

        return false;
    }

    private char[][] toMatrix(String[] dna) {
        int n = dna.length;
        char[][] matrix = new char[n][n];

        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        return matrix;
    }
}
