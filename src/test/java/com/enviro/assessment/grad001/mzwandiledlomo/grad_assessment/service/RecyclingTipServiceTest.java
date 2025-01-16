package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.RecyclingTipRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.RecyclingTipResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.mapper.RecyclingTipMapper;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.RecyclingTip;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.model.WasteCategory;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.RecyclingTipRepository;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.repository.WasteCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecyclingTipServiceTest {
    @Mock
    private RecyclingTipRepository tipRepository;

    @Mock
    private WasteCategoryRepository wasteCategoryRepository;

    @Mock
    private RecyclingTipMapper tipMapper;

    @InjectMocks
    private RecyclingTipServiceImp tipService;

    private RecyclingTipRequest request;
    private RecyclingTip tip;
    private RecyclingTipResponse response;
    private WasteCategory wasteCategory;

    @BeforeEach
    void setUp() {
        wasteCategory = new WasteCategory();
        wasteCategory.setId(1L);
        wasteCategory.setName("Electronics");

        request = new RecyclingTipRequest();
        request.setTitle("Battery Recycling");
        request.setContent("How to properly recycle batteries");
        request.setWasteCategoryId(1L);

        tip = new RecyclingTip();
        tip.setId(1L);
        tip.setTitle("Battery Recycling");
        tip.setContent("How to properly recycle batteries");
        tip.setWasteCategory(wasteCategory);

        response = new RecyclingTipResponse();
        response.setId(1L);
        response.setTitle("Battery Recycling");
        response.setContent("How to properly recycle batteries");
        response.setWasteCategoryId(1L);
    }

    @Test
    void shouldCreateRecyclingTip() {
        // given
        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.of(wasteCategory));
        when(tipMapper.toEntity(request)).thenReturn(tip);
        when(tipRepository.save(any(RecyclingTip.class))).thenReturn(tip);
        when(tipMapper.toResponse(tip)).thenReturn(response);

        // when
        RecyclingTipResponse created = tipService.createRecyclingTip(request);

        // then
        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo(request.getTitle());
        verify(tipRepository).save(any(RecyclingTip.class));
    }

    @Test
    void shouldThrowExceptionWhenWasteCategoryNotFound() {
        // given
        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // when/then
        assertThrows(ResourceNotFoundException.class, () ->
                tipService.createRecyclingTip(request));
    }

    @Test
    void shouldGetTipsByWasteCategoryId() {
        // given
        WasteCategory wasteCategory = new WasteCategory();
        wasteCategory.setId(1L);
        wasteCategory.setName("Electronics");

        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.of(wasteCategory));
        List<RecyclingTip> tips = List.of(tip);
        List<RecyclingTipResponse> responses = List.of(response);
        when(tipRepository.findByWasteCategoryId(1L)).thenReturn(tips);
        when(tipMapper.toResponseList(tips)).thenReturn(responses);

        // when
        List<RecyclingTipResponse> found = tipService.getTipsByWasteCategoryId(1L);

        // then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitle()).isEqualTo("Battery Recycling");
    }

    @Test
    void shouldUpdateRecyclingTip() {
        // given
        Long tipId = 1L;
        RecyclingTipRequest updateRequest = new RecyclingTipRequest();
        updateRequest.setTitle("Updated Battery Recycling");
        updateRequest.setContent("Updated content for battery recycling");
        updateRequest.setWasteCategoryId(1L);

        RecyclingTip updatedTip = new RecyclingTip();
        updatedTip.setId(tipId);
        updatedTip.setTitle(updateRequest.getTitle());
        updatedTip.setContent(updateRequest.getContent());
        updatedTip.setWasteCategory(wasteCategory);

        RecyclingTipResponse updatedResponse = new RecyclingTipResponse();
        updatedResponse.setId(tipId);
        updatedResponse.setTitle(updateRequest.getTitle());
        updatedResponse.setContent(updateRequest.getContent());
        updatedResponse.setWasteCategoryId(1L);

        when(tipRepository.findById(tipId)).thenReturn(Optional.of(tip));
        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.of(wasteCategory));
        when(tipRepository.save(any(RecyclingTip.class))).thenReturn(updatedTip);
        when(tipMapper.toResponse(updatedTip)).thenReturn(updatedResponse);

        // when
        RecyclingTipResponse result = tipService.updateRecyclingTip(tipId, updateRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(result.getContent()).isEqualTo(updateRequest.getContent());
        verify(tipRepository).save(any(RecyclingTip.class));
    }

    @Test
    void shouldGetRecyclingTipById(){
        // given
        Long tipId = 1L;
        when(tipRepository.findById(tipId)).thenReturn(Optional.of(tip));
        when(tipMapper.toResponse(tip)).thenReturn(response);

        // when
        RecyclingTipResponse found = tipService.getRecyclingTipById(tipId);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(tipId);
        assertThat(found.getTitle()).isEqualTo(tip.getTitle());
        verify(tipRepository).findById(tipId);
    }

    @Test
    void shouldGetAllTips(){
        // given
        List<RecyclingTip> tips = List.of(tip);
        List<RecyclingTipResponse> responses = List.of(response);
        when(tipRepository.findAll()).thenReturn(tips);
        when(tipMapper.toResponseList(tips)).thenReturn(responses);

        // when
        List<RecyclingTipResponse> result = tipService.getAllTips();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Battery Recycling");
        verify(tipRepository).findAll();
        verify(tipMapper).toResponseList(tips);
    }

    @Test
    void shouldDeleteRecyclingTip() {
        // given
        Long tipId = 1L;
        when(tipRepository.findById(tipId)).thenReturn(Optional.of(tip));
        doNothing().when(tipRepository).delete(tip);

        // when
        tipService.deleteTip(tipId);

        // then
        verify(tipRepository).findById(tipId);
        verify(tipRepository).delete(tip);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTip() {
        // given
        Long nonExistentTipId = 999L;
        when(tipRepository.findById(nonExistentTipId)).thenReturn(Optional.empty());

        // when/then
        assertThrows(ResourceNotFoundException.class, () ->
                tipService.deleteTip(nonExistentTipId));
        verify(tipRepository, never()).delete(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentTip() {
        // given
        Long nonExistentTipId = 999L;
        when(tipRepository.findById(nonExistentTipId)).thenReturn(Optional.empty());

        // when/then
        assertThrows(ResourceNotFoundException.class, () ->
                tipService.updateRecyclingTip(nonExistentTipId, request));
        verify(tipRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenGettingNonExistentTip() {
        // given
        Long nonExistentTipId = 999L;
        when(tipRepository.findById(nonExistentTipId)).thenReturn(Optional.empty());

        // when/then
        assertThrows(ResourceNotFoundException.class, () ->
                tipService.getRecyclingTipById(nonExistentTipId));
    }
}
