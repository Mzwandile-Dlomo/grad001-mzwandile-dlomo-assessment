package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.DisposalGuidelineRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.DisposalGuidelineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DisposalGuidelineService {
    DisposalGuidelineResponse createGuideline(DisposalGuidelineRequest request);
    DisposalGuidelineResponse updateGuideline(Long id, DisposalGuidelineRequest request);
    DisposalGuidelineResponse getGuidelineById(Long id);
    List<DisposalGuidelineResponse> getGuidelinesByWasteCategoryId(Long wasteCategoryId);
    Page<DisposalGuidelineResponse> getAllGuidelines(Pageable pageable);
    void deleteGuideline(Long id);
}
