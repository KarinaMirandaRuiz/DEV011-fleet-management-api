package com.example.dev011fleetmanagementapi.controller;

import com.example.dev011fleetmanagementapi.model.dao.TaxisRepository;
import com.example.dev011fleetmanagementapi.model.entity.TaxiEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class TaxiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TaxisRepository taxisRepository;

    TaxiEntity newTaxi0 = new TaxiEntity(0, "xxxx-0000");
    TaxiEntity newTaxi1 = new TaxiEntity(1, "yyyy-1111");
    TaxiEntity newTaxi2 = new TaxiEntity(1, "zzzz-2222");


    @Test
    @DisplayName("{GET}:/taxis?page=0&pageSize=10")
    void getAll() throws Exception{

        //Declaración de la lista de taxis que se va a retornar
        List<TaxiEntity> taxiList = Arrays.asList(newTaxi0, newTaxi1);

        //Creación de la paginación que se va a retornar
        Page<TaxiEntity> taxiPage = new PageImpl<>(taxiList, PageRequest.of(1, 1), taxiList.size());

        //Seteo del mock de 'taxisRepository.findAll' cuando sea llamado con cierta paginación y ordenado
        when(taxisRepository.findAll(
                PageRequest.of(0, 10)
                        .withSort(Sort.by(Sort.Direction.ASC, "id"))
                ))
                .thenReturn(taxiPage);

        mockMvc.perform(get("/api/v1/taxis?page=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    @DisplayName("{GET}:/taxi?id=0")
    void getById() throws Exception {
        //Seteo del Mock
        when(taxisRepository.findById(0)).thenReturn(Optional.of(newTaxi0));
        mockMvc.perform(get("/api/v1/taxi?id=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newTaxi0.getId()));
    }

    @Test
    @DisplayName("{POST}:/taxi")
    void post() throws Exception{
//
//        //Seteo del mock de 'taxisRepository.save' cuando sea llamado con algún taxi
//        when(taxisRepository.save(any(TaxiEntity.class)))
//                .thenReturn(newTaxi1);
//        String requestBody = new ObjectMapper().writeValueAsString(newTaxi1);
//        // String requestBody = "{\"id\":2, \"plate\":\"xxxx-2222\"}";
//        mockMvc.perform(post("/api/v1/taxi")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    @DisplayName("{PUT}:/taxi")
    void put() throws Exception{

//        //Seteo del mock de 'taxisRepository.save' cuando sea llamado con algún taxi
//        when(taxisRepository.save(any(TaxiEntity.class)))
//                .thenReturn(newTaxi1);
//        String requestBody = new ObjectMapper().writeValueAsString(newTaxi1);
//        // String requestBody = "{\"id\":2, \"plate\":\"xxxx-2222\"}";
//        mockMvc.perform(post("/api/v1/taxi")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    @DisplayName("{DELETE}:/taxi?id=0")
    void deleteById() throws Exception {
        //Seteo del Mock
        when(taxisRepository.findById(2)).thenReturn(Optional.of(newTaxi2));

        mockMvc.perform(get("/api/v1/taxi?id=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newTaxi2.getId()));
    }

}