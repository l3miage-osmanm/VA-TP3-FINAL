package fr.uga.l3miage.spring.tp3.controller;


import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.TestCenterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private CandidateRepository candidateRepository;

    @MockBean
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;

    @MockBean
    private CandidateController candidateController;

    @AfterEach
    void clearAll(){
        candidateRepository.deleteAll();
    }
    @Test
    void canNotGetCandidateAverage(){
        final HttpHeaders headers = new HttpHeaders();
        final HashMap<String, Object> urlParams = new HashMap<>();
        urlParams.put("idCandidate", "La moyenne du candidat n'a pas pu être trouvé");
        ResponseEntity<ChangeSetPersister.NotFoundException> ans = testRestTemplate.exchange("/api/candidates/{idCandidate}", HttpMethod.GET, new HttpEntity<>(null, headers), ChangeSetPersister.NotFoundException.class, urlParams);
        assertThat(ans.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
    @Test
    void canGetCandidateAverage(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        CandidateEntity candidateEntity1=CandidateEntity.builder()
                .id(388L)
                .email("test383@gmail.com")
                .firstname("vera")
                .lastname("wang")
                .phoneNumber("12345678")
                .hasExtraTime(false)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity12=CandidateEvaluationGridEntity.builder().grade(12.00).build();
        candidateEntity1.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity12));
        candidateRepository.save(candidateEntity1);

        urlParams.put("idCandidate", 388L);
        ResponseEntity<Double> ans = testRestTemplate.exchange("/api/candidates/{idCandidate}/average", HttpMethod.GET, new HttpEntity<>(null, headers), Double.class,urlParams);
        assertThat(ans.getStatusCode()).isEqualTo(HttpStatus.OK);


    }



}
