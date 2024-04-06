package fr.uga.l3miage.spring.tp3.exo1.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.TestCenterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Import de ExtendWith
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")

public class CandidateRepositoryTest {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private TestCenterRepository testCenterRepository;
    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;


    @Test
    void testFindAllByTestCenterEntityCode() {

        TestCenterEntity testCenterEntity = TestCenterEntity
                .builder()
                .code(TestCenterCode.GRE)
                .build();

        TestCenterEntity testCenterEntity2 = TestCenterEntity
                .builder()
                .code(TestCenterCode.TOU)
                .build();

        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("code Gre")
                .email("") //il faut le mettre car cest not null
                .testCenterEntity(testCenterEntity)
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("code Tou")
                .email("aaa@gmail.com")
                .testCenterEntity(testCenterEntity2)
                .build();

        testCenterRepository.save(testCenterEntity);
        testCenterRepository.save(testCenterEntity2);
        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.GRE);

        assertThat(candidateEntitiesResponses).hasSize(1);

    }

    @Test
    void findAllByCandidateEvaluationGridEntitiesGradeLessThan() {

        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("code Gre")
                .email("bbb@gmail.com") //il faut le mettre car cest not null
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("code Tou")
                .email("aaa@gmail.com")
                .build();

        CandidateEntity candidateEntity3 = CandidateEntity
                .builder()
                .firstname("code Gre")
                .email("ccc@gmail.com")
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity = CandidateEvaluationGridEntity
                .builder()
                .grade(5.00)
                .candidateEntity(candidateEntity)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity2 = CandidateEvaluationGridEntity
                .builder()
                .grade(5.00)
                .candidateEntity(candidateEntity2)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity3 = CandidateEvaluationGridEntity
                .builder()
                .grade(7.00)
                .candidateEntity(candidateEntity)
                .build();

        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity3);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity2);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity3);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(7.00);

        assertThat(candidateEntitiesResponses).hasSize(2);


    }


    @Test
    void findAllByHasExtraTimeFalseAndBirthDateBefore() {

        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("Baran")
                .email("d@gmail.com")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2002, 3, 2))
                .build();

        CandidateEntity candidateEntity1 = CandidateEntity
                .builder()
                .firstname("Eylul")
                .email("a@gmail.com")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2001, 10, 15))
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("Poyraz")
                .email("b@gmail.com")
                .hasExtraTime(true)
                .birthDate(LocalDate.of(2004, 10, 24))
                .build();

        CandidateEntity candidateEntity3 = CandidateEntity
                .builder()
                .firstname("Ayse")
                .email("c@gmail.com")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2008, 3, 2))
                .build();

        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity1);
        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity3);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2005, 1, 1));

        assertThat(candidateEntitiesResponses).hasSize(2);

        LocalDate secondCandidateBirthDate = candidateEntitiesResponses.stream()
                .skip(1)
                .findFirst()
                .map(CandidateEntity::getBirthDate)
                .orElse(null);

        assertThat(secondCandidateBirthDate).isEqualTo(LocalDate.of(2001, 10, 15));

    }


}