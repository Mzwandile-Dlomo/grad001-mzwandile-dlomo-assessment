package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DisposalGuidelineRequest {
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Waste category ID is required")
    private Long wasteCategoryId;

    private String steps;

    @Size(max = 1000, message = "Safety precautions cannot exceed 1000 characters")
    private String safetyPrecautions;

    @Size(max = 1000, message = "Environmental impact cannot exceed 1000 characters")
    private String environmentalImpact;
}
