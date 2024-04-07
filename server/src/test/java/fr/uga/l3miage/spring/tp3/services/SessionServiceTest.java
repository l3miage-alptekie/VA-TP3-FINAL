package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.enums.SessionStatus;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CreationSessionRestException;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationStepEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationStepCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionServiceTest {

    @Autowired
    SessionService sessionService;

    @MockBean
    SessionComponent sessionComponent;

    @Test
    void testCreateSession() {
        // GIVEN
        EcosSessionProgrammationStepEntity ecosStepEntity1 = EcosSessionProgrammationStepEntity
                .builder()
                .description("description1") //car ça doit être "not null" d'après les contraintes de EcosSessionEntity
                .code("C1")
                .dateTime(LocalDateTime.of(2024, 12, 10, 0, 0))
                .build();

        EcosSessionProgrammationStepEntity ecosStepEntity2 = EcosSessionProgrammationStepEntity
                .builder()
                .description("description2")
                .code("C2")
                .dateTime(LocalDateTime.of(2023, 12, 10, 0, 0))
                .build();

        EcosSessionProgrammationEntity ecosProgrammationEntity = EcosSessionProgrammationEntity
                .builder()
                .label("L1")
                .ecosSessionProgrammationStepEntities(Set.of(ecosStepEntity1, ecosStepEntity2))
                .build();

        EcosSessionEntity ecosSessionEntity = EcosSessionEntity
                .builder()
                .name("Session1")
                .ecosSessionProgrammationEntity(ecosProgrammationEntity)
                .status(SessionStatus.CREATED)
                .startDate(LocalDateTime.of(2024, 12, 20, 0, 0))
                .endDate(LocalDateTime.of(2025, 1, 20, 0, 0))
                .build();

        SessionCreationRequest creationRequest = SessionCreationRequest.builder()
                .name("Session2")
                .startDate(LocalDateTime.of(2024, 12, 20, 0, 0))
                .endDate(LocalDateTime.of(2025, 1, 20, 0, 0))
                .build();

        // Mocking
        when(sessionComponent.createSession(any(EcosSessionEntity.class))).thenThrow(CreationSessionRestException.class);

        // WHEN, THEN
        assertThrows(CreationSessionRestException.class, () -> sessionService.createSession(creationRequest));
    }

}