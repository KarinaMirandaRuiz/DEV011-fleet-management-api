package com.example.dev011fleetmanagementapi.controller;

import com.example.dev011fleetmanagementapi.model.dao.TaxisRepository;
import com.example.dev011fleetmanagementapi.model.entity.TaxiEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.verification.MockAwareVerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class TaxiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TaxisRepository taxisRepository;

    @Test
    void getById() throws Exception {

        TaxiEntity taxiEntity = new TaxiEntity(1, "xxxx-1234");

        when(taxisRepository.findById(1)).thenReturn(Optional.of(taxiEntity));
        mockMvc.perform(get("/api/v1/taxi?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taxiEntity.getId()));


    }
}