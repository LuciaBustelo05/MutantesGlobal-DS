package com.luciabustelo.mutantes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DnaSequenceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDnaSequence {

    String message() default "DNA must be NxN and contain only A,T,C,G";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
