package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.controller;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.DisposalGuidelineRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.DisposalGuidelineResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service.DisposalGuidelineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/disposal-guidelines")
@RequiredArgsConstructor
public class DisposalGuidelineController {
    private final DisposalGuidelineService disposalGuidelineService;

    @PostMapping
    public ResponseEntity<DisposalGuidelineResponse> createDisposalGuideline(@Valid @RequestBody DisposalGuidelineRequest request) {
        DisposalGuidelineResponse response = disposalGuidelineService.createGuideline(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisposalGuidelineResponse> updateDisposalGuideline(@PathVariable Long id, @Valid @RequestBody DisposalGuidelineRequest request) {
        DisposalGuidelineResponse response = disposalGuidelineService.updateGuideline(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisposalGuidelineResponse> getDisposalGuidelineById(@PathVariable Long id) {
        DisposalGuidelineResponse response = disposalGuidelineService.getGuidelineById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<DisposalGuidelineResponse>> getAllDisposalGuidelines(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<DisposalGuidelineResponse> responses = disposalGuidelineService.getAllGuidelines(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/waste-category/{categoryId}")
    public ResponseEntity<List<DisposalGuidelineResponse>> getGuidelinesByCategory(
            @PathVariable Long categoryId) {
        List<DisposalGuidelineResponse> responses = disposalGuidelineService
                .getGuidelinesByWasteCategoryId(categoryId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisposalGuideline(@PathVariable Long id) {
        disposalGuidelineService.deleteGuideline(id);
        return ResponseEntity.noContent().build();
    }
}
