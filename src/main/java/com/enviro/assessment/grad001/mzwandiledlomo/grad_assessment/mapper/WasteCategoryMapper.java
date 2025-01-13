package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.config.MapperConfig;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.WasteCategoryRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.WasteCategoryResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface WasteCategoryMapper {
    WasteCategory toEntity(WasteCategoryRequest request);

    WasteCategoryResponse toResponse(WasteCategory entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(WasteCategoryRequest request, @MappingTarget WasteCategory existingCategory);

    default List<WasteCategoryResponse> toResponseList(List<WasteCategory> categories) {
        return categories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}