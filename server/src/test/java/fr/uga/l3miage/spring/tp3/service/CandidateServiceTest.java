package fr.uga.l3miage.spring.tp3.service;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.services.CandidateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Set;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateServiceTest {
    @Autowired
    private CandidateService candidateService;

    @MockBean
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;
    @MockBean
    private CandidateComponent candidateComponent;
    @Test
    void testgetCandidateAverageNotFound() throws CandidateNotFoundException {

        when(candidateComponent.getCandidatById(anyLong())).thenThrow(CandidateNotFoundException.class);
        assertThrows(CandidateNotFoundRestException.class, () -> candidateService.getCandidateAverage(1L));
    }

    @Test
    void testgetCandidateAverageFound() throws CandidateNotFoundException {
        ExamEntity examEntity1 = ExamEntity.builder()
                .id(479746290L)
                .name("test")
                .weight(1)
                .build();



        ExamEntity examEntity2 = ExamEntity.builder()
                .id(145274L)
                .name("test")
                .weight(1)
                .build();
        ExamEntity examEntity3 = ExamEntity.builder()
                .id(2640274L)
                .name("test")
                .weight(1)
                .build();


        CandidateEvaluationGridEntity candidateEvaluationGridEntity12 = CandidateEvaluationGridEntity.builder()
                .grade(12.00)
                .examEntity(examEntity1)
                .build();
        CandidateEvaluationGridEntity candidateEvaluationGridEntity9 = CandidateEvaluationGridEntity.builder()
                .grade(12.00)
                .examEntity(examEntity2)
                .build();
        CandidateEvaluationGridEntity candidateEvaluationGridEntity15 = CandidateEvaluationGridEntity.builder()
                .grade(12.00)
                .examEntity(examEntity2)
                .build();

        CandidateEntity candidateEntity1=CandidateEntity.builder()
                .id(388L)
                .email("test383@gmail.com")
                .firstname("vera")
                .lastname("wang")
                .phoneNumber("12345678")
                .hasExtraTime(false)
                .candidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity12, candidateEvaluationGridEntity9,candidateEvaluationGridEntity15))
                .build();

        when(candidateComponent.getCandidatById(388L)).thenReturn((candidateEntity1)); //on trouve le candidat ajouté

        // vérifier que la moyenne est correcte
        assert(candidateService.getCandidateAverage(388L) == 12.00);


    }
}
