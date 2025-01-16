package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.DisposalGuideline;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class DisposalGuidelineRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DisposalGuidelineRepository repository;

    private WasteCategory category;
    private DisposalGuideline guideline;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        repository.deleteAll();

        // Create and persist a waste category
        category = new WasteCategory();
        category.setName("Electronics");
        category.setDescription("Electronic waste disposal");
        category = entityManager.persistAndFlush(category);

        // Create a sample guideline
        guideline = new DisposalGuideline();
        guideline.setTitle("Proper E-waste Disposal Guidelines");
        guideline.setDescription("Complete guide for disposing electronic waste");
        guideline.setSteps("1. Remove batteries\n2. Separate components\n3. Contact recycler");
        guideline.setSafetyPrecautions("Wear protective gloves when handling broken electronics");
        guideline.setEnvironmentalImpact("Prevents toxic materials from entering landfills");
        guideline.setWasteCategory(category);
        guideline.setActive(true);
    }

    @Test
    void shouldSaveDisposalGuidelineWithAllFields() {
        // when
        DisposalGuideline saved = repository.save(guideline);
        entityManager.flush();
        entityManager.clear();

        // then
        DisposalGuideline found = entityManager.find(DisposalGuideline.class, saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getId()).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Proper E-waste Disposal Guidelines");
        assertThat(found.getDescription()).isEqualTo("Complete guide for disposing electronic waste");
        assertThat(found.getSteps()).contains("1. Remove batteries");
        assertThat(found.getSafetyPrecautions()).contains("protective gloves");
        assertThat(found.getEnvironmentalImpact()).contains("toxic materials");
        assertThat(found.isActive()).isTrue();
        assertThat(found.getCreatedAt()).isNotNull();
        assertThat(found.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldFindMultipleGuidelinesByWasteCategoryId() {
        // given
        DisposalGuideline guideline2 = new DisposalGuideline();
        guideline2.setTitle("Battery Disposal Guide");
        guideline2.setDescription("How to safely dispose of batteries");
        guideline2.setWasteCategory(category);
        guideline2.setActive(true);

        repository.save(guideline);
        repository.save(guideline2);
        entityManager.flush();
        entityManager.clear();

        // when
        List<DisposalGuideline> found = repository.findByWasteCategoryId(category.getId());

        // then
        assertThat(found).hasSize(2);
        assertThat(found).extracting(DisposalGuideline::getTitle)
                .containsExactlyInAnyOrder(
                        "Proper E-waste Disposal Guidelines",
                        "Battery Disposal Guide"
                );
    }

//    @Test
//    void shouldFindActiveGuidelines() {
//        // given
//        guideline.setActive(true);
//        DisposalGuideline inactiveGuideline = new DisposalGuideline();
//        inactiveGuideline.setTitle("Inactive Guide");
//        inactiveGuideline.setDescription("This is inactive");
//        inactiveGuideline.setWasteCategory(category);
//        inactiveGuideline.setActive(false);
//
//        repository.save(guideline);
//        repository.save(inactiveGuideline);
//        entityManager.flush();
//        entityManager.clear();
//
//        // when
//        List<DisposalGuideline> activeGuidelines = repository.findByActiveTrue();
//
//        // then
//        assertThat(activeGuidelines).hasSize(1);
//        assertThat(activeGuidelines.get(0).getTitle()).isEqualTo("Proper E-waste Disposal Guidelines");
//    }

    @Test
    void shouldUpdateGuidelineFields() {
        // given
        DisposalGuideline saved = repository.save(guideline);
        entityManager.flush();
        entityManager.clear();

        // when
        DisposalGuideline toUpdate = entityManager.find(DisposalGuideline.class, saved.getId());
        toUpdate.setTitle("Updated E-waste Guidelines");
        toUpdate.setDescription("Updated description");
        toUpdate.setSafetyPrecautions("New safety measures");
        repository.save(toUpdate);
        entityManager.flush();
        entityManager.clear();

        // then
        DisposalGuideline updated = entityManager.find(DisposalGuideline.class, saved.getId());
        assertThat(updated.getTitle()).isEqualTo("Updated E-waste Guidelines");
        assertThat(updated.getDescription()).isEqualTo("Updated description");
        assertThat(updated.getSafetyPrecautions()).isEqualTo("New safety measures");
        assertThat(updated.getUpdatedAt()).isAfterOrEqualTo(updated.getCreatedAt());
    }

//    @Test
//    void shouldFindByTitleContaining() {
//        // given
//        repository.save(guideline);
//        entityManager.flush();
//        entityManager.clear();
//
//        // when
//        List<DisposalGuideline> found = repository.findByTitleContainingIgnoreCase("E-waste");
//
//        // then
//        assertThat(found).hasSize(1);
//        assertThat(found.get(0).getTitle()).contains("E-waste");
//    }

    @Test
    void shouldDeactivateGuideline() {
        // given
        DisposalGuideline saved = repository.save(guideline);
        entityManager.flush();
        entityManager.clear();

        // when
        DisposalGuideline toDeactivate = entityManager.find(DisposalGuideline.class, saved.getId());
        toDeactivate.setActive(false);
        repository.save(toDeactivate);
        entityManager.flush();
        entityManager.clear();

        // then
        DisposalGuideline deactivated = entityManager.find(DisposalGuideline.class, saved.getId());
        assertThat(deactivated.isActive()).isFalse();
    }
}
