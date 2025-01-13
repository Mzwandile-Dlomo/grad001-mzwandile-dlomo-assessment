package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.controller;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.WasteCategoryRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.WasteCategoryResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service.WasteCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/waste-categories")
@RequiredArgsConstructor
public class WasteCategoryController {

    private final WasteCategoryService wasteCategoryService;

    @PostMapping
    public ResponseEntity<WasteCategoryResponse> createWasteCategory(
            @Valid @RequestBody WasteCategoryRequest request) {
        WasteCategoryResponse response = wasteCategoryService.createWasteCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<WasteCategoryResponse> updateWasteCategory(
            @PathVariable Long id,
            @Valid @RequestBody WasteCategoryRequest request) {
        WasteCategoryResponse response = wasteCategoryService.updateWasteCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteCategoryResponse> getWasteCategoryById(@PathVariable Long id) {
        WasteCategoryResponse response = wasteCategoryService.getWasteCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WasteCategoryResponse>> getAllWasteCategories() {
        List<WasteCategoryResponse> responses = wasteCategoryService.getAllWasteCategories();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWasteCategory(@PathVariable Long id) {
        wasteCategoryService.deleteWasteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<WasteCategoryResponse>> searchWasteCategories(
            @RequestParam String keyword) {
        List<WasteCategoryResponse> responses = wasteCategoryService.searchWasteCategories(keyword);
        return ResponseEntity.ok(responses);
    }

}
