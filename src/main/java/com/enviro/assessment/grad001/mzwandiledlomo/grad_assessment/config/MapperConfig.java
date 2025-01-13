package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MapperConfig {
}
