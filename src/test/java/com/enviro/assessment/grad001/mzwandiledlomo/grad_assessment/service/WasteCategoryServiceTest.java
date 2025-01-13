package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.WasteCategoryRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.WasteCategoryResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper.WasteCategoryMapper;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.WasteCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WasteCategoryServiceTest {

    @Mock
    private WasteCategoryRepository wasteCategoryRepository;

    @Mock
    private WasteCategoryMapper wasteCategoryMapper;

    @InjectMocks
    private WasteCategoryServiceImpl wasteCategoryService;

    private WasteCategoryRequest wasteCategoryRequest;
    private WasteCategoryResponse wasteCategoryResponse;
    private WasteCategory wasteCategory;

    @Test
    void shouldCreateWasteCategory() {
        // Create request DTO
        wasteCategoryRequest = new WasteCategoryRequest();
        wasteCategoryRequest.setName("Electronics");
        wasteCategoryRequest.setDescription("Recyclable Electronics waste");

        // Create entity
        wasteCategory = new WasteCategory();
        wasteCategory.setId(1L);
        wasteCategory.setName("Electronics");
        wasteCategory.setDescription("Recyclable Electronics waste");

        // Create response DTO
        wasteCategoryResponse = new WasteCategoryResponse();
        wasteCategoryResponse.setId(1L);
        wasteCategoryResponse.setName("Electronics");
        wasteCategoryResponse.setDescription("Recyclable Electronics waste");

        // Mock behavior
        when(wasteCategoryMapper.toEntity(wasteCategoryRequest)).thenReturn(wasteCategory);
        when(wasteCategoryRepository.save(any(WasteCategory.class))).thenReturn(wasteCategory);
        when(wasteCategoryMapper.toResponse(wasteCategory)).thenReturn(wasteCategoryResponse);

        // Call service method
        WasteCategoryResponse response = wasteCategoryService.createWasteCategory(wasteCategoryRequest);

        // Verify response
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Electronics");
        assertThat(response.getDescription()).isEqualTo("Recyclable Electronics waste");
    }

    @Test
    void shouldThrowExceptionWhenWasteCategoryNotFound(){
      //
        Long id = 1L;

        when(wasteCategoryRepository.findById(id))
                .thenThrow(new ResourceNotFoundException("Waste category not found with id: " + id));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> wasteCategoryService.getWasteCategoryById(id));

        assertThat(exception.getMessage()).isEqualTo("Waste category not found with id: " + id);
    }

    @Test
    void shouldGetWasteCategories() {
        wasteCategory = new WasteCategory();
        wasteCategory.setId(1L);
        wasteCategory.setName("Electronics");
        wasteCategory.setDescription("Recyclable Electronics waste");

        wasteCategoryResponse = new WasteCategoryResponse();
        wasteCategoryResponse.setId(1L);
        wasteCategoryResponse.setName("Electronics");
        wasteCategoryResponse.setDescription("Recyclable Electronics waste");

        when(wasteCategoryRepository.findAll()).thenReturn(List.of(wasteCategory));
        when(wasteCategoryMapper.toResponseList(List.of(wasteCategory))).thenReturn(List.of(wasteCategoryResponse));

        List<WasteCategoryResponse> responses = wasteCategoryService.getAllWasteCategories();

        assertThat(responses).isNotNull();
        assertThat(responses.size()).isEqualTo(1);
        assertThat(responses.get(0)).isEqualTo(wasteCategoryResponse);
    }

    @Test
    void shouldGetWasteCategoryById() {
        Long id = 1L;

        wasteCategory = new WasteCategory();
        wasteCategory.setId(1L);
        wasteCategory.setName("Electronics");
        wasteCategory.setDescription("Recyclable Electronics waste");

        wasteCategoryResponse = new WasteCategoryResponse();
        wasteCategoryResponse.setId(1L);
        wasteCategoryResponse.setName("Electronics");
        wasteCategoryResponse.setDescription("Recyclable Electronics waste");

        when(wasteCategoryRepository.findById(id)).thenReturn(Optional.of(wasteCategory));
        when(wasteCategoryMapper.toResponse(wasteCategory)).thenReturn(wasteCategoryResponse);

        wasteCategoryResponse = wasteCategoryService.getWasteCategoryById(id);

        assertThat(wasteCategoryResponse).isNotNull();
        assertThat(wasteCategoryResponse.getId()).isEqualTo(1L);
        assertThat(wasteCategoryResponse.getName()).isEqualTo("Electronics");
    }
    @Test
    void shouldThrowExceptionWhenCreatingDuplicateWasteCategory() {
        wasteCategoryRequest = new WasteCategoryRequest();
        wasteCategoryRequest.setName("Electronics");

        when(wasteCategoryRepository.existsByName("Electronics")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> wasteCategoryService.createWasteCategory(wasteCategoryRequest));

        assertThat(exception.getMessage()).isEqualTo("Waste category with this name already exists");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingToDuplicateWasteCategoryName() {
        Long id = 1L;
        WasteCategory existingCategory = new WasteCategory();
        existingCategory.setId(id);
        existingCategory.setName("Old Electronics");

        WasteCategoryRequest request = new WasteCategoryRequest();
        request.setName("New Electronics");

        when(wasteCategoryRepository.findById(id)).thenReturn(java.util.Optional.of(existingCategory));
        when(wasteCategoryRepository.existsByName("New Electronics")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> wasteCategoryService.updateWasteCategory(id, request));

        assertThat(exception.getMessage()).isEqualTo("Waste category with this name already exists");
    }


    @Test
    void shouldThrowExceptionWhenDeletingNonExistentWasteCategory() {
        Long id = 1L;

        when(wasteCategoryRepository.existsById(id)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> wasteCategoryService.deleteWasteCategory(id));

        assertThat(exception.getMessage()).isEqualTo("Waste category not found with id: " + id);
    }

    @Test
    void shouldGetWasteCategoriesWithPagination() {
        Pageable pageable = PageRequest.of(0, 5);
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Electronics");

        wasteCategoryResponse = new WasteCategoryResponse();
        wasteCategoryResponse.setId(1L);
        wasteCategoryResponse.setName("Electronics");
        wasteCategoryResponse.setDescription(null);

        Page<WasteCategory> categoryPage = new PageImpl<>(List.of(category));
        when(wasteCategoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(wasteCategoryMapper.toResponse(category)).thenReturn(wasteCategoryResponse);

        Page<WasteCategoryResponse> responsePage = wasteCategoryService.getWasteCategoriesWithPagination(pageable);

        assertThat(responsePage).isNotNull();
        assertThat(responsePage.getTotalElements()).isEqualTo(1);
        assertThat(responsePage.getContent().get(0).getName()).isEqualTo("Electronics");
    }

    @Test
    void shouldSearchWasteCategories() {
        String keyword = "Electronics";
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Electronics");

        wasteCategoryResponse = new WasteCategoryResponse();
        wasteCategoryResponse.setId(1L);
        wasteCategoryResponse.setName("Electronics");
        wasteCategoryResponse.setDescription(null);

        when(wasteCategoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword))
                .thenReturn(List.of(category));
        when(wasteCategoryMapper.toResponseList(List.of(category)))
                .thenReturn(List.of(wasteCategoryResponse));

        List<WasteCategoryResponse> responseList = wasteCategoryService.searchWasteCategories(keyword);

        assertThat(responseList).isNotEmpty();
        assertThat(responseList.size()).isEqualTo(1);
        assertThat(responseList.get(0).getName()).isEqualTo("Electronics");
    }


}
