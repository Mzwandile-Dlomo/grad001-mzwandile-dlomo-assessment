package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.controller;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.DisposalGuidelineRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.DisposalGuidelineResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service.DisposalGuidelineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DisposalGuidelineController.class)
public class DisposalGuidelineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DisposalGuidelineService disposalGuidelineService;

    private DisposalGuidelineResponse response;
    private DisposalGuidelineRequest request;

    @BeforeEach
    void setUp() {
        request = new DisposalGuidelineRequest();
        request.setTitle("Plastic Disposal Guidelines");  // At least 5 characters
        request.setDescription("Guidelines for disposing plastic waste");
        request.setWasteCategoryId(1L);  // Required field
        request.setSteps("1. Sort plastics\n2. Clean containers\n3. Place in recycling bin");
        request.setSafetyPrecautions("Wear protective gloves");
        request.setEnvironmentalImpact("Minimal environmental impact when properly disposed");

        response = new DisposalGuidelineResponse();
        response.setId(1L);
        response.setTitle("Plastic Disposal Guidelines");
        response.setDescription("Guidelines for disposing plastic waste");
        response.setSteps("1. Sort plastics\n2. Clean containers\n3. Place in recycling bin");
        response.setSafetyPrecautions("Wear protective gloves");
        response.setEnvironmentalImpact("Minimal environmental impact when properly disposed");
    }

    @Test
    void shouldCreateDisposalGuideline() throws Exception {
        when(disposalGuidelineService.createGuideline(any(DisposalGuidelineRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Plastic Disposal Guidelines"))
                .andExpect(jsonPath("$.description").value("Guidelines for disposing plastic waste"));
    }

    @Test
    void shouldUpdateDisposalGuideline() throws Exception {
        Long guidelineId = 1L;
        when(disposalGuidelineService.updateGuideline(eq(guidelineId), any(DisposalGuidelineRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/disposal-guidelines/{id}", guidelineId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guidelineId))
                .andExpect(jsonPath("$.title").value("Plastic Disposal Guidelines"));
    }
    @Test
    void shouldGetDisposalGuideline() throws Exception {
        Long guidelineId = 1L;
        when(disposalGuidelineService.getGuidelineById(guidelineId))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/disposal-guidelines/{id}", guidelineId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guidelineId))
                .andExpect(jsonPath("$.title").value("Plastic Disposal Guidelines"));
    }

    @Test
    void shouldGetAllDisposalGuidelines() throws Exception {
        Page<DisposalGuidelineResponse> guidelinesPage = new PageImpl<>(Arrays.asList(response));
        when(disposalGuidelineService.getAllGuidelines(any(Pageable.class)))
                .thenReturn(guidelinesPage);

        mockMvc.perform(get("/api/v1/disposal-guidelines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("Plastic Disposal Guidelines"));
    }


    @Test
    void shouldDeleteDisposalGuideline() throws Exception {
        Long guidelineId = 1L;
        doNothing().when(disposalGuidelineService).deleteGuideline(guidelineId);

        mockMvc.perform(delete("/api/v1/disposal-guidelines/{id}", guidelineId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestResponse() throws Exception {
        request.setTitle(""); // Invalid title

        mockMvc.perform(post("/api/v1/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestDisposalGuidelineWithInvalidRequest() throws Exception {
        DisposalGuidelineRequest invalidRequest = new DisposalGuidelineRequest();
        // Not setting required fields

        mockMvc.perform(post("/api/v1/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
