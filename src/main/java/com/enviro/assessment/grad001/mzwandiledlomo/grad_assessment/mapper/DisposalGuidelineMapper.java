package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper;


import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.config.MapperConfig;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.DisposalGuidelineRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.DisposalGuidelineResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.DisposalGuideline;

import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface DisposalGuidelineMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wasteCategory", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DisposalGuideline toEntity(DisposalGuidelineRequest request);

    @Mapping(target = "wasteCategoryId", source = "wasteCategory.id")
    @Mapping(target = "wasteCategoryName", source = "wasteCategory.name")
    DisposalGuidelineResponse toResponse(DisposalGuideline disposalGuideline);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wasteCategory", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(DisposalGuidelineRequest request, @MappingTarget DisposalGuideline existingDisposalGuideline);

    default List<DisposalGuidelineResponse> toResponseList(List<DisposalGuideline> disposalGuidelines) {
        return disposalGuidelines.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
