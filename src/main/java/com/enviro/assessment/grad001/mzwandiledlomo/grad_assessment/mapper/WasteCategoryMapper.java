package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.WasteCategoryRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.WasteCategoryResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WasteCategoryMapper {
    public WasteCategory toEntity(WasteCategoryRequest request) {
        WasteCategory entity = new WasteCategory();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return entity;
    }

    public WasteCategoryResponse toResponse(WasteCategory entity) {
        WasteCategoryResponse response = new WasteCategoryResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public void updateEntityFromRequest(WasteCategoryRequest request, WasteCategory existingCategory) {
        // Set the new name and description, only if they are not null
        if (request.getName() != null) {
            existingCategory.setName(request.getName());
        }
        if (request.getDescription() != null) {
            existingCategory.setDescription(request.getDescription());
        }
    }

    public List<WasteCategoryResponse> toResponseList(List<WasteCategory> categories) {
        return categories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
