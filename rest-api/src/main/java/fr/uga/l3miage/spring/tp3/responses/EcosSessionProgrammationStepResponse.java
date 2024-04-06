package fr.uga.l3miage.spring.tp3.responses;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Builder
public class EcosSessionProgrammationStepResponse {
    private Long id;
    private String code;
    private String description;
    private LocalDateTime dateTime;
}
