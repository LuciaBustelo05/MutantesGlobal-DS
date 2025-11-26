package com.luciabustelo.mutantes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luciabustelo.mutantes.dto.DnaRequest;
import com.luciabustelo.mutantes.service.MutantService;
import com.luciabustelo.mutantes.service.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.luciabustelo.mutantes.dto.StatsResponse;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MutantService mutantService;

    @MockBean
    StatsService statsService;

    @Autowired
    ObjectMapper mapper;

    @Test
    void mutantReturns200() throws Exception {
        when(mutantService.analyzeDna(any())).thenReturn(true);

        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"AAAA","AAAA","AAAA","AAAA"});

        mvc.perform(post("/mutant")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void humanReturns403() throws Exception {
        when(mutantService.analyzeDna(any())).thenReturn(false);

        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"ATGC","CAGT","TTAT","AGAA"});

        mvc.perform(post("/mutant")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    void invalidDnaReturns400() throws Exception {
        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"AXXX","CAGT","TTAT","AGAA"});

        mvc.perform(post("/mutant")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void statsReturn200() throws Exception {
        when(statsService.getStats()).thenReturn(new StatsResponse(40,100,0.4));

        mvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    void statsReturnFields() throws Exception {
        when(statsService.getStats()).thenReturn(new StatsResponse(10,20,0.5));

        mvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(10))
                .andExpect(jsonPath("$.count_human_dna").value(20));
    }

    @Test
    void emptyBodyReturns400() throws Exception {
        mvc.perform(post("/mutant")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
