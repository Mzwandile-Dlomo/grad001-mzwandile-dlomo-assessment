package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.controller;

import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.request.WasteCategoryRequest;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.dto.response.WasteCategoryResponse;
import com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.service.WasteCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.http.MediaType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(WasteCategoryController.class)
public class WasteCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WasteCategoryService wasteCategoryService;

    private WasteCategoryRequest request;
    private WasteCategoryResponse response;

    @BeforeEach
    void setUp() {
        request = new WasteCategoryRequest();
        request.setName("Electronics");
        request.setDescription("Electronic waste");

        response = new WasteCategoryResponse();
        response.setId(1L);
        response.setName("Electronics");
        response.setDescription("Electronic waste");
    }

    @Test
    void shouldCreateWasteCategory() throws Exception {
        when(wasteCategoryService.createWasteCategory(any(WasteCategoryRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/waste-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    void shouldGetWasteCategoryById() throws Exception {
        when(wasteCategoryService.getWasteCategoryById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/waste-categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    void shouldUpdateWasteCategory() throws Exception {
        when(wasteCategoryService.updateWasteCategory(eq(1L), any(WasteCategoryRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/waste-categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldDeleteWasteCategory() throws Exception {
        mockMvc.perform(delete("/api/v1/waste-categories/1"))
                .andExpect(status().isNoContent());
    }
}
