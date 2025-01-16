package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.config.MapperConfig;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.RecyclingTipRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.RecyclingTipResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.RecyclingTip;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface RecyclingTipMapper {

    @Mapping(source = "wasteCategoryId", target = "wasteCategory.id")
    RecyclingTip toEntity(RecyclingTipRequest request);

    RecyclingTipResponse toResponse(RecyclingTip recyclingTip);

    default List<RecyclingTipResponse> toResponseList(List<RecyclingTip> recyclingTips) {
        return recyclingTips.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

}
