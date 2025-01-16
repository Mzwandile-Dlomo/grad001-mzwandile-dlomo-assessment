package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.RecyclingTipRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.RecyclingTipResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper.RecyclingTipMapper;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.RecyclingTip;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.RecyclingTipRepository;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.WasteCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecyclingTipServiceImp implements RecyclingTipService {
    private final RecyclingTipRepository recyclingTipRepository;
    private final RecyclingTipMapper recyclingTipMapper;
    private final WasteCategoryRepository wasteCategoryRepository;

    @Override
    public RecyclingTipResponse createRecyclingTip(RecyclingTipRequest request) {

        WasteCategory wasteCategory = wasteCategoryRepository.findById(request.getWasteCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Waste category not found"));

        RecyclingTip recyclingTip = recyclingTipMapper.toEntity(request);
        recyclingTip.setWasteCategory(wasteCategory);

        RecyclingTip recyclingTipSaved = recyclingTipRepository.save(recyclingTip);
        return recyclingTipMapper.toResponse(recyclingTipSaved);
    }

    @Override
    public RecyclingTipResponse updateRecyclingTip(Long id, RecyclingTipRequest request) {
        RecyclingTip existingRecyclingTip = recyclingTipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recycling tip not found with id: " + id));

        WasteCategory wasteCategory = wasteCategoryRepository.findById(request.getWasteCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Waste category not found"));

        existingRecyclingTip.setTitle(request.getTitle());
        existingRecyclingTip.setContent(request.getContent());
        existingRecyclingTip.setDifficultyLevel(request.getDifficultyLevel());
        existingRecyclingTip.setWasteCategory(wasteCategory);

        RecyclingTip updated = recyclingTipRepository.save(existingRecyclingTip);
        return recyclingTipMapper.toResponse(updated);
    }

    @Override
    public RecyclingTipResponse getRecyclingTipById(Long id) {
        RecyclingTip tip = recyclingTipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recycling tip not found with id: " + id));
        return recyclingTipMapper.toResponse(tip);
    }

    @Override
    public List<RecyclingTipResponse> getTipsByWasteCategoryId(Long wasteCategoryId) {
        wasteCategoryRepository.findById(wasteCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Waste category not found"));

        List<RecyclingTip> tips = recyclingTipRepository.findByWasteCategoryId(wasteCategoryId);
        return recyclingTipMapper.toResponseList(tips);
    }

    @Override
    public List<RecyclingTipResponse> getAllTips() {
        List<RecyclingTip> tips = recyclingTipRepository.findAll();
        return recyclingTipMapper.toResponseList(tips);
    }

    @Override
    public void deleteTip(Long id) {
        RecyclingTip tip = recyclingTipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recycling tip not found with id: " + id));

        recyclingTipRepository.delete(tip);
    }
}
