package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.RecyclingTipRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.RecyclingTipResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecyclingTipService {
    RecyclingTipResponse createRecyclingTip(RecyclingTipRequest request);
    RecyclingTipResponse updateRecyclingTip(Long id, RecyclingTipRequest request);
    RecyclingTipResponse getRecyclingTipById(Long id);
    List<RecyclingTipResponse> getTipsByWasteCategoryId(Long wasteCategoryId);
    List<RecyclingTipResponse> getAllTips();
    void deleteTip(Long id);
}
