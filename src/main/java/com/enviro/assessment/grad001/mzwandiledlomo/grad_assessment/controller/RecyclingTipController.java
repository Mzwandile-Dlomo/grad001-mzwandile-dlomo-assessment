package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.controller;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.RecyclingTipRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.RecyclingTipResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service.RecyclingTipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recycling-tips")
@RequiredArgsConstructor
public class RecyclingTipController {
    private final RecyclingTipService recyclingTipService;

    @PostMapping
    public ResponseEntity<RecyclingTipResponse> createRecyclingTip(@Valid  @RequestBody RecyclingTipRequest recyclingTipRequest) {
        RecyclingTipResponse recyclingTipResponse = recyclingTipService.createRecyclingTip(recyclingTipRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(recyclingTipResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecyclingTipResponse> updateRecyclingTip( @PathVariable Long id, @Valid @RequestBody RecyclingTipRequest recyclingTipRequest) {
        RecyclingTipResponse recyclingTipResponse = recyclingTipService.updateRecyclingTip(id, recyclingTipRequest);
        return ResponseEntity.ok(recyclingTipResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecyclingTipResponse> getRecyclingTipById(@PathVariable Long id) {
        RecyclingTipResponse recyclingTipResponse = recyclingTipService.getRecyclingTipById(id);
        return ResponseEntity.ok(recyclingTipResponse);
    }

    @GetMapping
    public ResponseEntity<List<RecyclingTipResponse>> getAllRecyclingTips() {
        List<RecyclingTipResponse> tipPage = recyclingTipService.getAllTips();
        return ResponseEntity.ok(tipPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecyclingTipResponse> deleteRecyclingTip(@PathVariable Long id) {
        recyclingTipService.deleteTip(id);
        return ResponseEntity.noContent().build();
    }

}
