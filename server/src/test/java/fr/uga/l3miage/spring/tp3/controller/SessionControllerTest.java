package fr.uga.l3miage.spring.tp3.controller;


import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import fr.uga.l3miage.spring.tp3.components.*;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class SessionControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EcosSessionRepository ecosSessionRepository;

    @SpyBean
    private SessionComponent sessionComponent;

    @SpyBean
    private ExamComponent examComponent;

    @AfterEach
    public void clearAll() {
        ecosSessionRepository.deleteAll();
    }

    @Test
    void createSessionNoExamOK(){
         
    }
    void createSessionNoExamKO(){
        final HttpHeaders headers = new HttpHeaders();
        final HashMap<String, Object> urlParams = new HashMap<>();
        urlParams.put("idSession", "KO creation session sans exam");
        ResponseEntity<ChangeSetPersister.NotFoundException> ans = testRestTemplate.exchange("/api/candidates/{idSession}", HttpMethod.GET, new HttpEntity<>(null, headers), ChangeSetPersister.NotFoundException.class, urlParams);
        assertThat(ans.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
