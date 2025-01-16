package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.RecyclingTip;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.RecyclingTip.DifficultyLevel;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.RecyclingTipRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RecyclingTipMapperTest {
    @Autowired
    private RecyclingTipMapper recyclingTipMapper;
    private RecyclingTipRequest request;
    private RecyclingTip entity;
    private WasteCategory category;

    @BeforeEach
    void setUp() {
        // Setup WasteCategory
        category = new WasteCategory();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic waste items");

        // Setup Request
        request = new RecyclingTipRequest();
        request.setTitle("Phone recycling");
        request.setContent("How to recycle phone");
        request.setDifficultyLevel(DifficultyLevel.MEDIUM);
        request.setWasteCategoryId(1L);

        // Setup Entity
        entity = new RecyclingTip();
        entity.setId(1L);
        entity.setTitle("Phone recycling");
        entity.setContent("How to recycle phone");
        entity.setDifficultyLevel(DifficultyLevel.MEDIUM);
        entity.setWasteCategory(category);
    }


    @Test
    void shouldMapRequestToEntity() {
//        // when
//        RecyclingTip mapped = recyclingTipMapper.toEntity(request);
//
//        // then
//        assertThat(mapped).isNotNull();
//        assertThat(mapped.getTitle()).isEqualTo("Phone recycling");
//        assertThat(mapped.getContent()).isEqualTo("How to recycle phone");
//        assertThat(mapped.getDifficultyLevel()).isEqualTo(DifficultyLevel.MEDIUM);

        // Setup WasteCategory
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Electronics");

        // Set the WasteCategory in RecyclingTipRequest
        request.setWasteCategoryId(1L);

        // when
        RecyclingTip mapped = recyclingTipMapper.toEntity(request);

        // then
        assertThat(mapped).isNotNull();
        assertThat(mapped.getTitle()).isEqualTo("Phone recycling");
        assertThat(mapped.getContent()).isEqualTo("How to recycle phone");
        assertThat(mapped.getDifficultyLevel()).isEqualTo(DifficultyLevel.MEDIUM);
        assertThat(mapped.getWasteCategory()).isNotNull();
        assertThat(mapped.getWasteCategory().getId()).isEqualTo(1L);
    }


}
