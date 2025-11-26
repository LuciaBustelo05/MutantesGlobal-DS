package com.luciabustelo.mutantes.integration;

import com.luciabustelo.mutantes.dto.DnaRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MutantIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void fullFlowMutant() {
        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"AAAA", "AAAA", "AAAA", "AAAA"});

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DnaRequest> request = new HttpEntity<>(req, headers);

        ResponseEntity<String> response =
                rest.postForEntity(url("/mutant"), request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void fullFlowStats() {
        ResponseEntity<String> response =
                rest.getForEntity(url("/stats"), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
