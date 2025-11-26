package com.luciabustelo.mutantes.repository;

import com.luciabustelo.mutantes.entity.DnaRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DnaRecordRepositoryTest {

    @Autowired
    private DnaRecordRepository repo;

    @Test
    void saveAndFindByHash() {
        DnaRecord rec = new DnaRecord();
        rec.setDnaHash("123abc");
        rec.setMutant(true);

        repo.save(rec);

        Optional<DnaRecord> found = repo.findByDnaHash("123abc");
        assertTrue(found.isPresent());
        assertTrue(found.get().isMutant());
    }

    @Test
    void countsMutantAndHumanCorrectly() {
        DnaRecord m = new DnaRecord();
        m.setDnaHash("m");
        m.setMutant(true);

        DnaRecord h = new DnaRecord();
        h.setDnaHash("h");
        h.setMutant(false);

        repo.save(m);
        repo.save(h);

        assertEquals(1, repo.countByIsMutant(true));
        assertEquals(1, repo.countByIsMutant(false));
    }
}
