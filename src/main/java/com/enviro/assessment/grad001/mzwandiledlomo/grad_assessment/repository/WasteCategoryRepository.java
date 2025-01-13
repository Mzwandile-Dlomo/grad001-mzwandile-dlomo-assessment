package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WasteCategoryRepository extends JpaRepository<WasteCategory, Long> {
    boolean existsByName(String name);
    List<WasteCategory> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
    Optional<WasteCategory> findByName(String name);
}
