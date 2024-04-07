package fr.uga.l3miage.spring.tp3.service;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CreationSessionRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.ExamNotFoundException;
import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationStepCreationRequest;
import fr.uga.l3miage.spring.tp3.services.SessionService;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;



@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionServiceTest {
    @Autowired
    private SessionService sessionService;
    @SpyBean
    private SessionMapper sessionMapper;
    @MockBean
    private SessionComponent sessionComponent;
    @MockBean
    private ExamComponent examComponent;


    @Test
    void createSessionTest() throws CreationSessionRestException, ExamNotFoundException {
        SessionProgrammationStepCreationRequest sessionProgrammationStepCreationRequest = SessionProgrammationStepCreationRequest
                .builder()
                .code("AKB103")
                .build();
        ExamEntity examEntity1 = ExamEntity.builder()
                .id(479746290L)
                .name("test")
                .weight(1)
                .build();


        Set<SessionProgrammationStepCreationRequest> Step1 = new HashSet<>();
        Step1.add(sessionProgrammationStepCreationRequest);





        SessionProgrammationCreationRequest sessionProgrammationCreationRequest = SessionProgrammationCreationRequest
                .builder()
                .label("LABEL")
                .steps(Step1)
                .build();
        SessionCreationRequest sessionCreationRequest = SessionCreationRequest
                .builder()
                .name("test")
                .examsId(Set.of((3849271L)))
                .ecosSessionProgrammation(sessionProgrammationCreationRequest)
                .build();
        EcosSessionEntity sessionEntity1 = sessionMapper.toEntity(sessionCreationRequest);
        when(sessionComponent.createSession(any())).thenReturn(sessionEntity1);

        Set<ExamEntity> examEntitiesSet = new HashSet<>();
        examEntitiesSet.add(examEntity1);

        when(examComponent.getAllById(any())).thenReturn(examEntitiesSet);
        SessionResponse sessionResponseExpected = sessionMapper.toResponse(sessionEntity1);

        SessionResponse ans = sessionService.createSession(sessionCreationRequest);

        assertThat(ans).usingRecursiveComparison().isEqualTo(sessionResponseExpected);
        verify(sessionMapper, times(2)).toEntity(sessionCreationRequest);
        verify(sessionMapper, times(2)).toResponse(same(sessionEntity1));
        verify(sessionComponent, times(1)).createSession(any(EcosSessionEntity.class));
    }
}