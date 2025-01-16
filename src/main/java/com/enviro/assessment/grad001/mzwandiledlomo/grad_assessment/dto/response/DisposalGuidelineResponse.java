package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DisposalGuidelineResponse {
    private Long id;
    private String title;
    private String description;
    private Long wasteCategoryId;
    private String wasteCategoryName;
    private String steps;
    private String safetyPrecautions;
    private String environmentalImpact;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
