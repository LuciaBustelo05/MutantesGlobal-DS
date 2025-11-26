package com.luciabustelo.mutantes.service;

import com.luciabustelo.mutantes.dto.StatsResponse;
import com.luciabustelo.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatsServiceTest {

    private final DnaRecordRepository repo = mock(DnaRecordRepository.class);
    private final StatsService service = new StatsService(repo);

    @Test
    void ratioNormal() {
        when(repo.countByIsMutant(true)).thenReturn(40L);
        when(repo.countByIsMutant(false)).thenReturn(100L);

        StatsResponse s = service.getStats();

        assertEquals(0.4, s.getRatio());
    }

    @Test
    void zeroHumans() {
        when(repo.countByIsMutant(true)).thenReturn(10L);
        when(repo.countByIsMutant(false)).thenReturn(0L);

        StatsResponse s = service.getStats();

        assertEquals(0, s.getRatio());
    }

    @Test
    void zeroMutants() {
        when(repo.countByIsMutant(true)).thenReturn(0L);
        when(repo.countByIsMutant(false)).thenReturn(20L);

        StatsResponse s = service.getStats();

        assertEquals(0, s.getRatio());
    }

    @Test
    void largeNumbers() {
        when(repo.countByIsMutant(true)).thenReturn(5000L);
        when(repo.countByIsMutant(false)).thenReturn(10000L);

        StatsResponse s = service.getStats();

        assertEquals(0.5, s.getRatio());
    }

    @Test
    void statsReturnCorrectFields() {
        when(repo.countByIsMutant(true)).thenReturn(8L);
        when(repo.countByIsMutant(false)).thenReturn(4L);

        StatsResponse s = service.getStats();

        assertEquals(8, s.getCount_mutant_dna());
        assertEquals(4, s.getCount_human_dna());
    }
}
