package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationStepRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionComponentTest {
    @Autowired
    private SessionComponent sessionComponent;

    @MockBean
    private EcosSessionRepository ecosSessionRepository;
    @MockBean
    private  EcosSessionProgrammationRepository ecosSessionProgrammationRepository;
    @MockBean
    private  EcosSessionProgrammationStepRepository ecosSessionProgrammationStepRepository;
    /*  when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(CandidateNotFoundException.class,()->candidateComponent.getCandidatById((264718L)));
*/
    @Test
    void CreatedSessionNotFound(){

    }
}
