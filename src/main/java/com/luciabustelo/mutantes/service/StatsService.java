package com.luciabustelo.mutantes.service;

import com.luciabustelo.mutantes.dto.StatsResponse;
import com.luciabustelo.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DnaRecordRepository repo;

    public StatsService(DnaRecordRepository repo) {
        this.repo = repo;
    }

    public StatsResponse getStats() {
        long mutant = repo.countByIsMutant(true);
        long human = repo.countByIsMutant(false);

        double ratio = human == 0 ? 0 : (double) mutant / human;

        return new StatsResponse(mutant, human, ratio);
    }
}
