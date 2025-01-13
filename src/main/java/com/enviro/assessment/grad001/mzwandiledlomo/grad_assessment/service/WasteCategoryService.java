package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;


import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.WasteCategoryRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.WasteCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WasteCategoryService {
    WasteCategoryResponse createWasteCategory(WasteCategoryRequest request);
    WasteCategoryResponse updateWasteCategory(Long id, WasteCategoryRequest request);
    WasteCategoryResponse getWasteCategoryById(Long id);
    List<WasteCategoryResponse> getAllWasteCategories();
    Page<WasteCategoryResponse> getWasteCategoriesWithPagination(Pageable pageable);
    void deleteWasteCategory(Long id);
    List<WasteCategoryResponse> searchWasteCategories(String keyword);
    boolean existsByName(String name);
}
