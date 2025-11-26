package com.luciabustelo.mutantes.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    void horizontalMutation() {
        String[] dna = {
                "AAAA",
                "CGTA",
                "CGTA",
                "CGTA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void verticalMutation() {
        String[] dna = {
                "ACGT",
                "ACGT",
                "ACGT",
                "ACGT"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void diagonalDownMutation() {
        String[] dna = {
                "AAAA",
                "CAAA",
                "TCAA",
                "AGCA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void diagonalUpMutation() {
        String[] dna = {
                "AAAC",
                "AACA",
                "ACAA",
                "CAAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void noMutationHuman() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGACGG",
                "CACCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void exactlyOneMutationHuman() {
        String[] dna = {
                "AAAA",
                "TGCT",
                "TGCT",
                "TGCT"
        };
        assertFalse(detector.isMutant(dna)); // solo 1 → humano
    }

    @Test
    void twoMutationsMutant() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTAT",
                "CCCC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void bigMatrixMutant() {
        String[] dna = {
                "ATGCGATCGA",
                "CAGTGCGTCA",
                "TTATGTACGT",
                "AGAAAGGTCT",
                "CCGCTAGGTA",
                "TCACTGACCC",
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTGGGT",
                "AGAAAGCCCA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void smallMatrixHuman() {
        String[] dna = {
                "AT",
                "CA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void matrix3x3Human() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void edgeCaseAllSameButNotEnough() {
        String[] dna = {
                "AAA",
                "AAA",
                "AAA"
        };
        assertFalse(detector.isMutant(dna)); // no hay 4
    }

    @Test
    void edgeCaseRandomHuman() {
        String[] dna = {
                "TGCA",
                "GCAT",
                "CATG",
                "ATGC"
        };
        assertFalse(detector.isMutant(dna));
    }
}
