package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class WasteCategoryRepositoryTest {

    @Autowired
    private WasteCategoryRepository wasteCategoryRepository;

    private WasteCategory sampleWasteCategory;

    @BeforeEach
    void setUp() {
        // Clear the repository before each test
        wasteCategoryRepository.deleteAll();

        sampleWasteCategory = new WasteCategory();
        sampleWasteCategory.setName("Plastic");
        sampleWasteCategory.setDescription("Recyclable plastic materials");
    }

    @Test
    void shouldSaveWasteCategory() {
        // when
        WasteCategory savedCategory = wasteCategoryRepository.save(sampleWasteCategory);

        // then
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Plastic");
        assertThat(savedCategory.getDescription()).isEqualTo("Recyclable plastic materials");
    }

    @Test
    void shouldFindWasteCategoryByName() {
        // given
        WasteCategory savedCategory = wasteCategoryRepository.save(sampleWasteCategory);

        // when
        WasteCategory found = wasteCategoryRepository.findByName("Plastic").orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(sampleWasteCategory.getName());
        assertThat(found.getId()).isEqualTo(savedCategory.getId());
    }

    @Test
    void shouldCheckIfCategoryExists() {
        // given
        wasteCategoryRepository.save(sampleWasteCategory);

        // when
        boolean exists = wasteCategoryRepository.existsByName("Plastic");

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void shouldFindCategoriesByNameOrDescription() {
        // given
        wasteCategoryRepository.save(sampleWasteCategory);

        // when
        var foundByName = wasteCategoryRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase("PLASTIC", "");
        var foundByDescription = wasteCategoryRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase("", "recyclable");

        // then
        assertThat(foundByName).hasSize(1);
        assertThat(foundByDescription).hasSize(1);
    }
}
