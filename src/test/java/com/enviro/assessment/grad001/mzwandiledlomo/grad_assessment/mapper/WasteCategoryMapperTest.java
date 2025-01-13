package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.WasteCategoryRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.WasteCategoryResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WasteCategoryMapperTest {
    @Autowired
    private WasteCategoryMapper mapper;

    @Test
    void shouldMapRequestToEntity() {
        // given
        WasteCategoryRequest request = new WasteCategoryRequest();
        request.setName("Electronics");
        request.setDescription("Electronic waste materials");

        // when
        WasteCategory entity = mapper.toEntity(request);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull(); // ID should be ignored in mapping
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
    }

    @Test
    void shouldMapEntityToResponse() {
        // given
        WasteCategory entity = new WasteCategory();
        entity.setId(1L);
        entity.setName("Electronics");
        entity.setDescription("Electronic waste materials");

        // when
        WasteCategoryResponse response = mapper.toResponse(entity);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(entity.getId());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
    }
}


