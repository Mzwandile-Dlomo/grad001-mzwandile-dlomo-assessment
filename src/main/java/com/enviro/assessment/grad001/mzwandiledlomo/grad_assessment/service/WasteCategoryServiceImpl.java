package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.WasteCategoryRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.WasteCategoryResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper.WasteCategoryMapper;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.WasteCategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WasteCategoryServiceImpl implements WasteCategoryService {
    private final WasteCategoryRepository wasteCategoryRepository;
    private final WasteCategoryMapper wasteCategoryMapper;

    @Override
    public WasteCategoryResponse createWasteCategory(WasteCategoryRequest request) {
        if (wasteCategoryRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Waste category with this name already exists");
        }

        WasteCategory category = wasteCategoryMapper.toEntity(request);
        WasteCategory savedCategory = wasteCategoryRepository.save(category);
        return wasteCategoryMapper.toResponse(savedCategory);
    }

    @Override
    public WasteCategoryResponse updateWasteCategory(Long id, WasteCategoryRequest request) {
        WasteCategory existingCategory = wasteCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Waste category not found with id: " + id));

        // If name is being changed, check if new name already exists
        if (!existingCategory.getName().equals(request.getName()) &&
                wasteCategoryRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Waste category with this name already exists");
        }

        wasteCategoryMapper.updateEntityFromRequest(request, existingCategory);
        WasteCategory updatedCategory = wasteCategoryRepository.save(existingCategory);
        return wasteCategoryMapper.toResponse(updatedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public WasteCategoryResponse getWasteCategoryById(Long id) {
        WasteCategory category = wasteCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Waste category not found with id: " + id));
        return wasteCategoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WasteCategoryResponse> getAllWasteCategories() {
        List<WasteCategory> categories = wasteCategoryRepository.findAll();
        return wasteCategoryMapper.toResponseList(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WasteCategoryResponse> getWasteCategoriesWithPagination(Pageable pageable) {
        return wasteCategoryRepository.findAll(pageable)
                .map(wasteCategoryMapper::toResponse);
    }

    @Override
    public void deleteWasteCategory(Long id) {
        if (!wasteCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Waste category not found with id: " + id);
        }
        wasteCategoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WasteCategoryResponse> searchWasteCategories(String keyword) {
        List<WasteCategory> categories = wasteCategoryRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        return wasteCategoryMapper.toResponseList(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return wasteCategoryRepository.existsByName(name);
    }
}
