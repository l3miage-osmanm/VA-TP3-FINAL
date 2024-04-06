package fr.uga.l3miage.spring.tp3.components;


import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
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
public class CandidateComponentTest {

    @Autowired
    private CandidateComponent candidateComponent;

    @MockBean
    private CandidateRepository candidateRepository;
    @Test
    void getCandidateNotFound(){

        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(CandidateNotFoundException.class,()->candidateComponent.getCandidatById((264718L)));


    }
    @Test
    void getCandidateFound(){
        //GIVEN
        CandidateEntity candidateEntity=CandidateEntity.builder()
                .id(3385619L).
                email("test4z5@gmail.com")
                .firstname("kate")
                .lastname("hasGoneMIA")
                .phoneNumber("87636321")
                .hasExtraTime(false)
                .build();
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.of(candidateEntity));

        //when-then
        assertDoesNotThrow(()->candidateComponent.getCandidatById(3385619L));

    }

}
