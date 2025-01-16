package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecyclingTipResponse {
    private Long id;
    private Long wasteCategoryId;
    private String title;
    private String content;
    private String difficulty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
