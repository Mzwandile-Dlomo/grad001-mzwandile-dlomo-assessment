package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.RecyclingTip.DifficultyLevel;


@Setter
@Getter
public class RecyclingTipRequest {
    @NotNull(message = "Waste category ID is required")
    private Long wasteCategoryId;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 20, message = "Title must be between 2 and 20 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Content cannot exceed 2000 characters")
    private String content;

    private DifficultyLevel difficultyLevel;
}
