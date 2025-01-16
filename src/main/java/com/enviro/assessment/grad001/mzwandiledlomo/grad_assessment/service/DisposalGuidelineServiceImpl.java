package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.DisposalGuidelineRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.DisposalGuidelineResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.DisposalGuideline;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.WasteCategoryRepository;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper.DisposalGuidelineMapper;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DisposalGuidelineServiceImpl implements DisposalGuidelineService{

    private final DisposalGuidelineRepository guidelineRepository;
    private final WasteCategoryRepository wasteCategoryRepository;
    private final DisposalGuidelineMapper guidelineMapper;

    @Override
    public DisposalGuidelineResponse createGuideline(DisposalGuidelineRequest request) {
        WasteCategory category = wasteCategoryRepository.findById(request.getWasteCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Waste category not found"));

        DisposalGuideline guideline = guidelineMapper.toEntity(request);
        guideline.setWasteCategory(category);

        DisposalGuideline savedGuideline = guidelineRepository.save(guideline);
        return guidelineMapper.toResponse(savedGuideline);
    }

    @Override
    public DisposalGuidelineResponse updateGuideline(Long id, DisposalGuidelineRequest request) {
        DisposalGuideline existingGuideline = guidelineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guideline not found"));

        WasteCategory category = wasteCategoryRepository.findById(request.getWasteCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Waste category not found"));

        guidelineMapper.updateEntityFromRequest(request, existingGuideline);
        existingGuideline.setWasteCategory(category);

        DisposalGuideline updatedGuideline = guidelineRepository.save(existingGuideline);
        return guidelineMapper.toResponse(updatedGuideline);
    }

    @Override
    @Transactional(readOnly = true)
    public DisposalGuidelineResponse getGuidelineById(Long id) {
        DisposalGuideline guideline = guidelineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guideline not found"));
        return guidelineMapper.toResponse(guideline);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisposalGuidelineResponse> getGuidelinesByWasteCategoryId(Long wasteCategoryId) {
        List<DisposalGuideline> guidelines = guidelineRepository.findByWasteCategoryId(wasteCategoryId);
        return guidelineMapper.toResponseList(guidelines);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisposalGuidelineResponse> getAllGuidelines(Pageable pageable) {
        return guidelineRepository.findAll(pageable)
                .map(guidelineMapper::toResponse);
    }

    @Override
    public void deleteGuideline(Long guidelineId) {
        DisposalGuideline guideline = guidelineRepository.findById(guidelineId)
                .orElseThrow(() -> new ResourceNotFoundException("Guideline not found with id: " + guidelineId));
        guidelineRepository.delete(guideline);
    }

//    public void deleteGuideline(Long id) {
//        if (!guidelineRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Guideline not found");
//        }
//        guidelineRepository.deleteById(id);
//    }
}
