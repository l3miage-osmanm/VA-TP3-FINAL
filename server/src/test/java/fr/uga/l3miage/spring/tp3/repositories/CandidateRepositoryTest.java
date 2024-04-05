package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.exo1.enums.GenreMusical;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;
/*Set<CandidateEntity> findAllByTestCenterEntityCode(TestCenterCode code);*/
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateRepositoryTest {
    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    void testFindAllByTestCenterEntityCode(){
        //GIVEN
        LocalDate d=01-01-2001;
        TestCenterEntity testCenterEntityGRENOBLE= TestCenterEntity.builder().code(TestCenterCode.GRE).build();
        TestCenterEntity testCenterEntityDIJON= TestCenterEntity.builder().code(TestCenterCode.DIJ).build();
        CandidateEntity candidateEntity1=CandidateEntity.builder().id(327348).
                email("test@gmail.com")
                .firstname("vera")
                .lastname("wang")
                .phoneNumber("12345678")
                .hasExtraTime(true)
                .birthDate(d)
                .build();
        CandidateEntity candidateEntity2=CandidateEntity.builder().id(6452840).
                email("test2@gmail.com")
                .firstname("tara")
                .lastname("berkshire")
                .phoneNumber("87654321")
                .hasExtraTime(true)
                .birthDate(d)
                .build();
        CandidateEntity candidateEntity3=CandidateEntity.builder().id(463852).
                email("test3@gmail.com")
                .firstname("kate")
                .lastname("hasGoneMIA")
                .phoneNumber("87636321")
                .hasExtraTime(false)
                .birthDate(d)
                .build();
        candidateEntity1.setTestCenterEntity(testCenterEntityDIJON);
        candidateEntity2.setTestCenterEntity(testCenterEntityGRENOBLE);
        candidateEntity3.setTestCenterEntity(testCenterEntityDIJON);

        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity1);
        candidateRepository.save(candidateEntity3)

        //WHEN
        Set<CandidateEntity> candidatesDijon=findAllByTestCenterEntityCode(TestCenterCode.DIJ);
        Set<CandidateEntity> candidatesGrenoble=findAllByTestCenterEntityCode(TestCenterCode.GRE);

        //THEN
        assertThat(candidatesDijon).hasSize(2);
        assertThat(candidatesGrenoble).hasSize(1);

        assertThat(candidatesDijon.stream()
                        .allMatch(
                                (candidateEntity) -> candidateEntity.getTestCenterEntity().getCode().equals(TestCenterCode.DIJ)
                        ));

    }

}
