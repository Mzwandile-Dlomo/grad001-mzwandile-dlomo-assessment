package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.DisposalGuidelineRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.DisposalGuidelineResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper.DisposalGuidelineMapper;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.DisposalGuideline;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.WasteCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisposalGuidelineServiceTest {
    @Mock
    private DisposalGuidelineRepository disposalGuidelineRepository;

    @Mock
    private WasteCategoryRepository wasteCategoryRepository;

    @Mock
    private DisposalGuidelineMapper disposalGuidelineMapper;

    @InjectMocks
    private DisposalGuidelineServiceImpl disposalGuidelineService;

    private DisposalGuideline guideline;
    private DisposalGuidelineResponse response;
    private DisposalGuidelineRequest request;
    private WasteCategory wasteCategory;

    @BeforeEach
    void setUp() {
        wasteCategory = new WasteCategory();
        wasteCategory.setId(1L);
        wasteCategory.setName("Electronics");

        request = new DisposalGuidelineRequest();
        request.setTitle("Dispose Batteries");
        request.setDescription("Guidelines for disposing batteries");
        request.setWasteCategoryId(1L);

        guideline = new DisposalGuideline();
        guideline.setId(1L);
        guideline.setTitle("Dispose Batteries");
        guideline.setDescription("Guidelines for disposing batteries");
        guideline.setWasteCategory(wasteCategory);

        response = new DisposalGuidelineResponse();
        response.setId(1L);
        response.setTitle("Dispose Batteries");
        response.setDescription("Guidelines for disposing batteries");
        response.setWasteCategoryId(1L);
    }

    @Test
    void shouldCreateGuideline() {
        // given
        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.of(wasteCategory));
        when(disposalGuidelineMapper.toEntity(request)).thenReturn(guideline);
        when(disposalGuidelineRepository.save(any(DisposalGuideline.class))).thenReturn(guideline);
        when(disposalGuidelineMapper.toResponse(guideline)).thenReturn(response);

        // when
        DisposalGuidelineResponse created = disposalGuidelineService.createGuideline(request);

        // then
        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo(request.getTitle());
        verify(disposalGuidelineRepository).save(any(DisposalGuideline.class));
    }

    @Test
    void shouldThrowExceptionWhenWasteCategoryNotFound() {
        // given
        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // when/then
        assertThrows(ResourceNotFoundException.class, () ->
                disposalGuidelineService.createGuideline(request));
    }

//    @Test
//    void shouldGetGuidelinesByWasteCategoryId() {
//        // given
//        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.of(wasteCategory));
//        List<DisposalGuideline> guidelines = List.of(guideline);
//        List<DisposalGuidelineResponse> responses = List.of(response);
//        when(disposalGuidelineRepository.findByWasteCategoryId(1L)).thenReturn(guidelines);
//        when(disposalGuidelineMapper.toResponseList(guidelines)).thenReturn(responses);
//
//        // when
//        List<DisposalGuidelineResponse> found = disposalGuidelineService.getGuidelinesByWasteCategoryId(1L);
//
//        // then
//        assertThat(found.get(0).getTitle()).isEqualTo("Dispose Batteries");
//    }

    @Test
    void shouldUpdateDisposalGuideline() {
        // given
        Long guidelineId = 1L;
        DisposalGuidelineRequest updateRequest = new DisposalGuidelineRequest();
        updateRequest.setTitle("Updated Disposal Guideline");
        updateRequest.setDescription("Updated description");
        updateRequest.setWasteCategoryId(1L);

        DisposalGuideline updatedGuideline = new DisposalGuideline();
        updatedGuideline.setId(guidelineId);
        updatedGuideline.setTitle(updateRequest.getTitle());
        updatedGuideline.setDescription(updateRequest.getDescription());
        updatedGuideline.setWasteCategory(wasteCategory);

        DisposalGuidelineResponse updatedResponse = new DisposalGuidelineResponse();
        updatedResponse.setId(guidelineId);
        updatedResponse.setTitle(updateRequest.getTitle());
        updatedResponse.setDescription(updateRequest.getDescription());
        updatedResponse.setWasteCategoryId(1L);

        when(disposalGuidelineRepository.findById(guidelineId)).thenReturn(Optional.of(guideline));
        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.of(wasteCategory));
        when(disposalGuidelineRepository.save(any(DisposalGuideline.class))).thenReturn(updatedGuideline);
        when(disposalGuidelineMapper.toResponse(updatedGuideline)).thenReturn(updatedResponse);

        // when
        DisposalGuidelineResponse result = disposalGuidelineService.updateGuideline(guidelineId, updateRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(updateRequest.getTitle());
        verify(disposalGuidelineRepository).save(any(DisposalGuideline.class));
    }

    @Test
    void shouldDeleteDisposalGuideline() {
        // given
        Long guidelineId = 1L;
        when(disposalGuidelineRepository.findById(guidelineId)).thenReturn(Optional.of(guideline));
        doNothing().when(disposalGuidelineRepository).delete(guideline);

        // when
        disposalGuidelineService.deleteGuideline(guidelineId);

        // then
        verify(disposalGuidelineRepository).findById(guidelineId);
        verify(disposalGuidelineRepository).delete(guideline);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentGuideline() {
        // given
        Long nonExistentGuidelineId = 999L;
        when(disposalGuidelineRepository.findById(nonExistentGuidelineId)).thenReturn(Optional.empty());

        // when/then
        assertThrows(ResourceNotFoundException.class, () ->
                disposalGuidelineService.deleteGuideline(nonExistentGuidelineId));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentGuideline() {
        // given
        Long nonExistentGuidelineId = 999L;
        when(disposalGuidelineRepository.findById(nonExistentGuidelineId)).thenReturn(Optional.empty());

        // when/then
        assertThrows(ResourceNotFoundException.class, () ->
                disposalGuidelineService.updateGuideline(nonExistentGuidelineId, request));
    }
}
