package com.luciabustelo.mutantes.controller;

import com.luciabustelo.mutantes.dto.DnaRequest;
import com.luciabustelo.mutantes.dto.StatsResponse;
import com.luciabustelo.mutantes.service.MutantService;
import com.luciabustelo.mutantes.service.StatsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    public MutantController(MutantService mutantService, StatsService statsService) {
        this.mutantService = mutantService;
        this.statsService = statsService;
    }

    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());

        return isMutant
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(403).build();
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> stats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}
