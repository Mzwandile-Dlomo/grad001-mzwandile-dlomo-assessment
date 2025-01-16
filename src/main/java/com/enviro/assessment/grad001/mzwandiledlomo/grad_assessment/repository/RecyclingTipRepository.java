package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.RecyclingTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingTipRepository extends JpaRepository<RecyclingTip, Long> {
    List<RecyclingTip> findByWasteCategoryId(Long wasteCategory_id);
}
