package fr.uga.l3miage.spring.tp3.exo1.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Import de ExtendWith
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension; // Import de SpringExtension

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")

public class CandidateRepositoryTest {
    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    void testFindAllByTestCenterEntityCode(){

        TestCenterEntity testCenterEntity = TestCenterEntity
                .builder()
                .code(TestCenterCode.valueOf("GRE"))
                .build();

        TestCenterEntity testCenterEntity2 = TestCenterEntity
                .builder()
                .code(TestCenterCode.valueOf("TOU"))
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
                .email("")
                .testCenterEntity(testCenterEntity2)
                .build();

        candidateRepository.save(candidateEntity);
        candidateRepository.save(candidateEntity2);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.valueOf("GRE"));

        assertThat(candidateEntitiesResponses).hasSize(1);


    }

}
