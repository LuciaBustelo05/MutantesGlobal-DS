package com.luciabustelo.mutantes.dto;

import com.luciabustelo.mutantes.validation.ValidDnaSequence;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DnaRequest {

    @NotNull
    @ValidDnaSequence
    private String[] dna;
}
