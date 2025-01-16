package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.RecyclingTip;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class RecyclingTipRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecyclingTipRepository repository;

    private WasteCategory category;
    private RecyclingTip testRecyclingTip;

    @BeforeEach
    void setUp() {
        category = new WasteCategory();
        category.setName("Electronics");
        category.setDescription("Electronic waste");
        entityManager.persist(category);

        testRecyclingTip = new RecyclingTip();
        testRecyclingTip.setTitle("Battery Recycling");
        testRecyclingTip.setContent("How to recycle batteries");
        testRecyclingTip.setWasteCategory(category);
        entityManager.persist(testRecyclingTip);

        entityManager.flush();
    }

    @Test
    void shouldSaveRecyclingTip() {
        // given
        RecyclingTip tip = new RecyclingTip();
        tip.setTitle("Battery Recycling");
        tip.setContent("How to recycle batteries");
        tip.setWasteCategory(category);

        // when
        RecyclingTip saved = repository.save(tip);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Battery Recycling");
        assertThat(saved.getWasteCategory().getId()).isEqualTo(category.getId());
    }



    @Test
    void shouldFindByWasteCategoryId() {
        // given - using data from setup

        // when
        List<RecyclingTip> found = repository.findByWasteCategoryId(category.getId());

        // then
        assertThat(found.get(0).getTitle()).isEqualTo("Battery Recycling");
    }

    @Test
    void shouldFindMultipleTipsByWasteCategoryId() {
        // given
        RecyclingTip phoneRecyclingTip = new RecyclingTip();
        phoneRecyclingTip.setTitle("Phone Recycling");
        phoneRecyclingTip.setContent("How to recycle phones");
        phoneRecyclingTip.setWasteCategory(category);
        entityManager.persist(phoneRecyclingTip);
        entityManager.flush();

        List<RecyclingTip> found = repository.findByWasteCategoryId(category.getId());

        //then
        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(2);
        assertThat(found.get(0).getTitle()).isEqualTo("Battery Recycling");
        assertThat(found.get(1).getTitle()).isEqualTo("Phone Recycling");
    }

    @Test
    void shouldReturnEmptyListWhenWasteCategoryNotFound() {
        List<RecyclingTip> found = repository.findByWasteCategoryId(99L);

        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(0);
    }

    @Test
    void shouldUpdateExistingRecyclingTip() {
        // given - update the setup data
        testRecyclingTip.setTitle("Updated - Battery Recycling");
        testRecyclingTip.setContent("Updated - How to recycle batteries");

        // when
        RecyclingTip updated = repository.save(testRecyclingTip);

        // then
        assertThat(updated.getTitle()).isEqualTo(testRecyclingTip.getTitle());
        assertThat(updated.getContent()).isEqualTo(testRecyclingTip.getContent());
        assertThat(updated.getWasteCategory().getId()).isEqualTo(updated.getWasteCategory().getId());
    }

    @Test
    void shouldDeleteExistingRecyclingTip() {
        // given - delete setup data

        repository.delete(testRecyclingTip);
        RecyclingTip found = entityManager.find(RecyclingTip.class, testRecyclingTip.getId());

        // then
        assertThat(found).isNull();
    }

    @Test
    void shouldFindById() {
        Optional<RecyclingTip> found = repository.findById(testRecyclingTip.getId());

        //then
        assertThat(found).isNotNull();
        assertThat(found.get().getTitle()).isEqualTo(testRecyclingTip.getTitle());
    }

    @Test
    void shouldNotNonExistentId() {
        Optional<RecyclingTip> found = repository.findById(99L);

        //then
        assertThat(found).isEmpty();
    }
}
