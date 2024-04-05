package fr.uga.l3miage.spring.tp2.exo1.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

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
                .email("")
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
