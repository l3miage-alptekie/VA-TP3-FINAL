package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.enums.SessionStatus;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationStepRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class SessionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    // les repositories
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private EcosSessionRepository ecosSessionRepository;
    @Autowired
    private EcosSessionProgrammationStepRepository ecosSessionProgrammationStepRepository;
    @Autowired
    private EcosSessionProgrammationRepository ecosSessionProgrammationRepository;

    @AfterEach
    public void clear() {
        examRepository.deleteAll();
        ecosSessionRepository.deleteAll();
        ecosSessionProgrammationStepRepository.deleteAll();
        ecosSessionProgrammationRepository.deleteAll();
    }

    @Test
    void canCreateSession() {

        EcosSessionEntity sessionEntity = EcosSessionEntity.builder()
                .name("Session de test")
                .startDate(LocalDateTime.of(2024, 4, 7, 10, 0))
                .endDate(LocalDateTime.of(2024, 4, 7, 11, 0))
                .status(SessionStatus.CREATED)
                .build();

        // Appel de l'endpoint de création de session
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity("/api/sessions/create", sessionEntity, String.class);

        // Vérifier la réponse
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotBlank();
    }
}
