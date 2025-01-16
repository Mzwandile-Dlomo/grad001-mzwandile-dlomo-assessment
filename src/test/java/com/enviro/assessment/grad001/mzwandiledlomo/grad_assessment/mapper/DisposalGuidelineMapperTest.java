package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper;


import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.DisposalGuidelineRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.DisposalGuidelineResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.DisposalGuideline;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DisposalGuidelineMapperTest {
    @Autowired
    private DisposalGuidelineMapper disposalGuidelineMapper;
    private DisposalGuidelineRequest request;
    private DisposalGuideline entity;
    private WasteCategory category;

    @BeforeEach
    void setUp() {
        // Setup WasteCategory
        category = new WasteCategory();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic waste items");

        // Setup DisposalGuidelineRequest
        request = new DisposalGuidelineRequest();
        request.setTitle("Recycle Batteries");
        request.setDescription("Dispose of batteries in designated recycling bins.");

        // Setup DisposalGuideline entity
        entity = new DisposalGuideline();
        entity.setId(1L);
        entity.setTitle("Recycle Batteries");
        entity.setDescription("Dispose of batteries in designated recycling bins.");
        entity.setWasteCategory(category);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void shouldMapRequestToEntity() {
        // Act
        DisposalGuideline result = disposalGuidelineMapper.toEntity(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(request.getTitle());
        assertThat(result.getDescription()).isEqualTo(request.getDescription());
        assertThat(result.getWasteCategory()).isNull(); // Because the mapping ignores wasteCategory
    }

    @Test
    void shouldMapEntityToResponse() {
        // Act
        DisposalGuidelineResponse response = disposalGuidelineMapper.toResponse(entity);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo(entity.getTitle());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getWasteCategoryId()).isEqualTo(category.getId());
        assertThat(response.getWasteCategoryName()).isEqualTo(category.getName());
    }

    @Test
    void shouldUpdateEntityFromRequest() {
        // Arrange
        DisposalGuideline existingEntity = new DisposalGuideline();
        existingEntity.setTitle("Old Title");
        existingEntity.setDescription("Old Description");

        // Act
        disposalGuidelineMapper.updateEntityFromRequest(request, existingEntity);

        // Assert
        assertThat(existingEntity.getTitle()).isEqualTo(request.getTitle());
        assertThat(existingEntity.getDescription()).isEqualTo(request.getDescription());
    }
}
