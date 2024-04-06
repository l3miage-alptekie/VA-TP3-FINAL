package fr.uga.l3miage.spring.tp3.exo1.components;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateComponentTest {
    @Autowired
    private CandidateComponent candidateComponent;
    @MockBean
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;
    @MockBean
    private CandidateRepository candidateRepository;

    @Test
    void getAllEliminatedCandidateTest() {
        // Given
        CandidateEntity candidateEntity1 = CandidateEntity
                .builder()
                .candidateEvaluationGridEntities(Set.of())
                .birthDate(LocalDate.of(2000, 11, 20))
                .hasExtraTime(false)
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .candidateEvaluationGridEntities(Set.of())
                .birthDate(LocalDate.of(2001, 10, 15))
                .hasExtraTime(true)
                .build();

        CandidateEvaluationGridEntity evaluationGridEntity1 = CandidateEvaluationGridEntity
                .builder()
                .grade(4.5)
                .candidateEntity(candidateEntity1)
                .build();

        CandidateEvaluationGridEntity evaluationGridEntity2 = CandidateEvaluationGridEntity
                .builder()
                .grade(5.5)
                .candidateEntity(candidateEntity2)
                .build();

        Set<CandidateEvaluationGridEntity> initialSet = Set.of(evaluationGridEntity1, evaluationGridEntity2);

        when(candidateEvaluationGridRepository.findAllByGradeIsLessThanEqual(5))
                .thenReturn(new HashSet<>(initialSet)); // Ensure the set is mutable

        // When - Then
        assertDoesNotThrow(() -> candidateComponent.getAllEliminatedCandidate());
    }
}
