package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.controller;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.RecyclingTipRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.RecyclingTipResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service.RecyclingTipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecyclingTipController.class)
public class RecyclingTipControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecyclingTipService recyclingTipService;

    private RecyclingTipRequest request;
    private RecyclingTipResponse response;

    @BeforeEach
    void setUp() {
        request = new RecyclingTipRequest();
        request.setTitle("Battery Recycling");
        request.setContent("How to properly recycle batteries");
        request.setWasteCategoryId(1L);

        response = new RecyclingTipResponse();
        response.setId(1L);
        response.setTitle("Battery Recycling");
        response.setContent("How to properly recycle batteries");
        response.setWasteCategoryId(1L);
    }

    @Test
    void shouldCreateRecyclingTip() throws Exception {
        when(recyclingTipService.createRecyclingTip(any(RecyclingTipRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/recycling-tips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Battery Recycling"))
                .andExpect(jsonPath("$.content").value("How to properly recycle batteries"))
                .andExpect(jsonPath("$.wasteCategoryId").value(1L));

        verify(recyclingTipService).createRecyclingTip(any(RecyclingTipRequest.class));
    }

    @Test
    void shouldUpdateRecyclingTip() throws Exception {
        when(recyclingTipService.updateRecyclingTip(eq(1L), any(RecyclingTipRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/recycling-tips/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Battery Recycling"))
                .andExpect(jsonPath("$.content").value("How to properly recycle batteries"))
                .andExpect(jsonPath("$.wasteCategoryId").value(1L));

        verify(recyclingTipService).updateRecyclingTip(eq(1L), any(RecyclingTipRequest.class));
    }

    @Test
    void shouldGetRecyclingTipById() throws Exception {
        when(recyclingTipService.getRecyclingTipById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/recycling-tips/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Battery Recycling"))
                .andExpect(jsonPath("$.content").value("How to properly recycle batteries"))
                .andExpect(jsonPath("$.wasteCategoryId").value(1L));

        verify(recyclingTipService).getRecyclingTipById(1L);
    }

    @Test
    void shouldGetAllRecyclingTips() throws Exception {
        List<RecyclingTipResponse> responses = List.of(response);
        when(recyclingTipService.getAllTips())
                .thenReturn(responses);

        mockMvc.perform(get("/api/v1/recycling-tips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Battery Recycling"))
                .andExpect(jsonPath("$[0].content").value("How to properly recycle batteries"))
                .andExpect(jsonPath("$[0].wasteCategoryId").value(1L));

        verify(recyclingTipService).getAllTips();
    }

    @Test
    void shouldDeleteRecyclingTip() throws Exception {
        doNothing().when(recyclingTipService).deleteTip(1L);

        mockMvc.perform(delete("/api/v1/recycling-tips/1"))
                .andExpect(status().isNoContent());

        verify(recyclingTipService).deleteTip(1L);
    }

    @Test
    void shouldReturnBadRequestWhenCreateRecyclingTipWithInvalidRequest() throws Exception {
        request.setTitle(""); // Invalid title

        mockMvc.perform(post("/api/v1/recycling-tips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(recyclingTipService, never()).createRecyclingTip(any());
    }

    @Test
    void shouldReturnBadRequestWhenUpdateRecyclingTipWithInvalidRequest() throws Exception {
        request.setTitle(""); // Invalid title

        mockMvc.perform(put("/api/v1/recycling-tips/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(recyclingTipService, never()).updateRecyclingTip(anyLong(), any());
    }
}
