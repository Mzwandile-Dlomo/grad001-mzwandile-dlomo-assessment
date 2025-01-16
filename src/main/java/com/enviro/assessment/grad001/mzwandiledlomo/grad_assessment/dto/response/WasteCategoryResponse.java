package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WasteCategoryResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<DisposalGuidelineResponse> guidelines;
    private List<RecyclingTipResponse> recyclingTips;
}
