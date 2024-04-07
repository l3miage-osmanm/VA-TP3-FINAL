package fr.uga.l3miage.spring.tp3.repositories;


import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateRepositoryTest {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private TestCenterRepository testCenterRepository;
    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;

    @BeforeEach
    public void clearAll(){
        candidateRepository.deleteAll();
        candidateEvaluationGridRepository.deleteAll();
        testCenterRepository.deleteAll();
    }

    @Test
    void testFindAllByHasExtraTimeFalseAndBirthDateBefore(){
        LocalDate dateTest=LocalDate.of(2022, 4, 5);
        LocalDate dateTest2=LocalDate.of(2001, 4, 5);
        LocalDate dateTest3=LocalDate.of(2000, 9, 5);

        LocalDate date1 = LocalDate.of(2002, 4, 5);
        LocalDate date2 = LocalDate.of(2000, 6, 12);
        LocalDate date3 = LocalDate.of(1999, 9, 28);
        CandidateEntity candidateEntity1=CandidateEntity.builder().
                email("test383@gmail.com")
                .firstname("vera")
                .lastname("wang")
                .phoneNumber("12345678")
                .hasExtraTime(false)
                .birthDate(date1)
                .build();
        CandidateEntity candidateEntity2=CandidateEntity.builder().
                email("test3384@gmail.com")
                .firstname("tara")
                .lastname("berkshire")
                .phoneNumber("87654321")
                .hasExtraTime(true)
                .birthDate(date2)
                .build();
        CandidateEntity candidateEntity3=CandidateEntity.builder().
                email("test4smc5@gmail.com")
                .firstname("kate")
                .lastname("hasGoneMIA")
                .phoneNumber("87636321")
                .hasExtraTime(false)
                .birthDate(date3)
                .build();
        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity1);
        candidateRepository.save(candidateEntity3);
        Set<CandidateEntity> candidateEntityTEST1= candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(dateTest);
        Set<CandidateEntity> candidateEntityTEST2= candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(dateTest2);
        Set<CandidateEntity> candidateEntityTEST3 =candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(dateTest3);

        assertThat(candidateEntityTEST1).hasSize(2);
        assertThat(candidateEntityTEST2).hasSize(1);
        assertThat(candidateEntityTEST3).hasSize(1);

        assertThat(candidateEntityTEST1.stream().allMatch(
                (candidateEntity -> (!candidateEntity.isHasExtraTime())&&(candidateEntity.getBirthDate().isBefore(dateTest)))
        )).isTrue();

        assertThat(candidateEntityTEST2.stream().allMatch(
                (candidateEntity -> (!candidateEntity.isHasExtraTime())&&(candidateEntity.getBirthDate().isBefore(dateTest2)))
        )).isTrue();

        assertThat(candidateEntityTEST3.stream().allMatch(
                (candidateEntity -> (!candidateEntity.isHasExtraTime())&&(candidateEntity.getBirthDate().isBefore(dateTest3)))
        )).isTrue();




    }


    @Test
    void testFindAllByCandidateEvaluationGridEntitiesGradeLessThan(){
        CandidateEntity candidateEntity1=CandidateEntity.builder().
                email("test45@gmail.com")
                .firstname("vera")
                .lastname("wang")
                .phoneNumber("12345678")
                .hasExtraTime(true)
                .build();
        CandidateEntity candidateEntity2=CandidateEntity.builder().
                email("test374@gmail.com")
                .firstname("tara")
                .lastname("berkshire")
                .phoneNumber("87654321")
                .hasExtraTime(true)
                .build();
        CandidateEntity candidateEntity3=CandidateEntity.builder().
                email("test4z5@gmail.com")
                .firstname("kate")
                .lastname("hasGoneMIA")
                .phoneNumber("87636321")
                .hasExtraTime(false)
                .build();
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
        CandidateEvaluationGridEntity candidateEvaluationGridEntity12=CandidateEvaluationGridEntity.builder().grade(12.00).candidateEntity(candidateEntity1).examEntity(examEntity1).build();
        CandidateEvaluationGridEntity candidateEvaluationGridEntity9=CandidateEvaluationGridEntity.builder().grade(9.25).candidateEntity(candidateEntity2).examEntity(examEntity2).build();
        CandidateEvaluationGridEntity candidateEvaluationGridEntity15=CandidateEvaluationGridEntity.builder().grade(15.75).candidateEntity(candidateEntity3).examEntity(examEntity3).build();

        candidateEvaluationGridRepository.saveAll(Set.of(candidateEvaluationGridEntity9,candidateEvaluationGridEntity12,candidateEvaluationGridEntity15));

        candidateEntity3.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity9));
        candidateEntity2.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity15));
        candidateEntity1.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity12));

        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity1);
        candidateRepository.save(candidateEntity3);

        //WHEN
        Set<CandidateEntity> candidateEntities13=candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(13.00);
        Set<CandidateEntity> candidateEntities10=candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10.00);
        Set<CandidateEntity> candidateEntities20=candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(20.00);

        //THEN


        assertThat(candidateEntities10).hasSize(1);
        assertThat(candidateEntities13).hasSize(2);
        assertThat(candidateEntities20).hasSize(3);

        assertThat(candidateEntities10.stream().
                allMatch(
                        (candidateEntity -> candidateEntity.getCandidateEvaluationGridEntities().stream().anyMatch(
                                (candidateEvaluationGridEntity -> candidateEvaluationGridEntity.getGrade()<=10)
                        ))
                )
        ).isTrue();;
        assertThat(candidateEntities13.stream().
                allMatch(
                        (candidateEntity -> candidateEntity.getCandidateEvaluationGridEntities().stream().anyMatch(
                                (candidateEvaluationGridEntity -> candidateEvaluationGridEntity.getGrade()<=13)
                        ))
                )
        ).isTrue();;
        assertThat(candidateEntities20.stream().
                allMatch(
                        (candidateEntity -> candidateEntity.getCandidateEvaluationGridEntities().stream().anyMatch(
                                (candidateEvaluationGridEntity -> candidateEvaluationGridEntity.getGrade()<=20)
                        ))
                )
        ).isTrue();


    }
    @Test
    void testFindAllByTestCenterEntityCode(){
        //GIVEN
        TestCenterEntity testCenterEntityGRENOBLE= TestCenterEntity.builder().code(TestCenterCode.GRE).build();
        TestCenterEntity testCenterEntityDIJON= TestCenterEntity.builder().code(TestCenterCode.DIJ).build();
        testCenterRepository.save(testCenterEntityDIJON);
        testCenterRepository.save(testCenterEntityGRENOBLE);

        CandidateEntity candidateEntity1=CandidateEntity.builder().id(327348L).
                email("test@gmail.com")
                .firstname("vera")
                .lastname("wang")
                .phoneNumber("12345678")
                .hasExtraTime(true)
                .build();
        CandidateEntity candidateEntity2=CandidateEntity.builder().id(6452840L).
                email("test2@gmail.com")
                .firstname("tara")
                .lastname("berkshire")
                .phoneNumber("87654321")
                .hasExtraTime(true)
                .build();
        CandidateEntity candidateEntity3=CandidateEntity.builder().id(463852L).
                email("test3@gmail.com")
                .firstname("kate")
                .lastname("hasGoneMIA")
                .phoneNumber("87636321")
                .hasExtraTime(false)
                .build();

        candidateEntity1.setTestCenterEntity(testCenterEntityDIJON);
        candidateEntity2.setTestCenterEntity(testCenterEntityGRENOBLE);
        candidateEntity3.setTestCenterEntity(testCenterEntityDIJON);

        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity1);
        candidateRepository.save(candidateEntity3);


        //WHEN
        Set<CandidateEntity> candidatesDijon=candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.DIJ);
        Set<CandidateEntity> candidatesGrenoble=candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.GRE);

        //THEN
        assertThat(candidatesDijon).hasSize(2);
        assertThat(candidatesGrenoble).hasSize(1);

        assertThat(candidatesDijon.stream()
                .allMatch(
                        (candidateEntity) -> candidateEntity.getTestCenterEntity().getCode().equals(TestCenterCode.DIJ)
                ));
        assertThat(candidatesDijon.stream()
                .allMatch(
                        (candidateEntity) -> candidateEntity.getTestCenterEntity().getCode().equals(TestCenterCode.GRE)
                ));
    }

}

