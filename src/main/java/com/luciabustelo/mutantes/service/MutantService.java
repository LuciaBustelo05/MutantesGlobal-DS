package com.luciabustelo.mutantes.service;

import com.luciabustelo.mutantes.entity.DnaRecord;
import com.luciabustelo.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class MutantService {

    private final MutantDetector detector;
    private final DnaRecordRepository repo;

    public MutantService(MutantDetector detector, DnaRecordRepository repo) {
        this.detector = detector;
        this.repo = repo;
    }

    public boolean analyzeDna(String[] dna) {
        String hash = hash(dna);

        return repo.findByDnaHash(hash)
                .map(DnaRecord::isMutant)
                .orElseGet(() -> computeAndSave(dna, hash));
    }

    private boolean computeAndSave(String[] dna, String hash) {
        boolean isMutant = detector.isMutant(dna);

        DnaRecord record = new DnaRecord();
        record.setDnaHash(hash);
        record.setMutant(isMutant);

        repo.save(record);

        return isMutant;
    }

    private String hash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            for (String row : dna) {
                digest.update(row.getBytes());
            }

            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
