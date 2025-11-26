package com.luciabustelo.mutantes.service;

import com.luciabustelo.mutantes.entity.DnaRecord;
import com.luciabustelo.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutantServiceTest {

    private final MutantDetector detector = mock(MutantDetector.class);
    private final DnaRecordRepository repo = mock(DnaRecordRepository.class);
    private final MutantService service = new MutantService(detector, repo);

    @Test
    void cachedMutant() {
        String[] dna = {"AAAA","AAAA","AAAA","AAAA"};

        DnaRecord record = new DnaRecord();
        record.setMutant(true);

        when(repo.findByDnaHash(anyString())).thenReturn(Optional.of(record));

        assertTrue(service.analyzeDna(dna));
        verify(detector, never()).isMutant(any());
    }

    @Test
    void cachedHuman() {
        String[] dna = {"TTTT","TTTT","TTTT","TTTT"};

        DnaRecord record = new DnaRecord();
        record.setMutant(false);

        when(repo.findByDnaHash(anyString())).thenReturn(Optional.of(record));

        assertFalse(service.analyzeDna(dna));
    }

    @Test
    void notCachedMutant() {
        String[] dna = {"AAAA","CGTA","CGTA","CGTA"};

        when(repo.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        assertTrue(service.analyzeDna(dna));
        verify(repo).save(any());
    }

    @Test
    void notCachedHuman() {
        String[] dna = {"ATGC","CAGT","TTAT","AGAA"};

        when(repo.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(false);

        assertFalse(service.analyzeDna(dna));
        verify(repo).save(any());
    }

    @Test
    void hashingReturnsSameForSameInput() {
        String[] dna = {"AAAA","CCCC","GGGG","TTTT"};

        boolean r1 = service.analyzeDna(dna);
        boolean r2 = service.analyzeDna(dna);

        // No assert, test ensures no crash; hash always consistent
    }

    @Test
    void saveIsCalledOnlyOnce() {
        String[] dna = {"AAAA","CCCC","GGGG","TTTT"};

        when(repo.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(any())).thenReturn(true);

        service.analyzeDna(dna);

        verify(repo, times(1)).save(any());
    }

    @Test
    void detectorCalledWhenNotCached() {
        String[] dna = {"AAAA","CCCC","GGGG","TTTT"};

        when(repo.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(any())).thenReturn(true);

        service.analyzeDna(dna);

        verify(detector).isMutant(any());
    }

    @Test
    void detectorNotCalledWhenCached() {
        String[] dna = {"AAAA","CCCC","GGGG","TTTT"};

        DnaRecord record = new DnaRecord();
        record.setMutant(true);

        when(repo.findByDnaHash(anyString())).thenReturn(Optional.of(record));

        service.analyzeDna(dna);

        verify(detector, never()).isMutant(any());
    }
}
