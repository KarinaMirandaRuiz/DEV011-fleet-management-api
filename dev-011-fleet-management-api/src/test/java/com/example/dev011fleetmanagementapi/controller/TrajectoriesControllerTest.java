package com.example.dev011fleetmanagementapi.controller;

import com.example.dev011fleetmanagementapi.model.dao.TaxisRepository;
import com.example.dev011fleetmanagementapi.model.dao.TrajectoriesRepository;
import com.example.dev011fleetmanagementapi.model.entity.TaxiEntity;
import com.example.dev011fleetmanagementapi.model.entity.TrajectoryEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.sql.Timestamp;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class TrajectoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrajectoriesRepository trajectoriesRepository;

    @Autowired
    private TaxisRepository taxisRepository;

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer(
            DockerImageName.parse("postgres")
                    .asCompatibleSubstituteFor("postgres")
        )
            .withDatabaseName("testtrajectories")
            .withUsername("def")
            .withPassword("pass");

    @DynamicPropertySource
    static void properties (DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", ()->String.format("jdbc:postgresql://localhost:%d/testtrajectories",postgres.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "def");
        registry.add("spring.datasource.password", () -> "pass");
    }

    @BeforeEach
    void setUpBefore(){
        trajectoriesRepository.deleteAll();
        taxisRepository.deleteAll();
    }

    @AfterEach
    void setupAfter(){
        trajectoriesRepository.deleteAll();
        taxisRepository.deleteAll();
    }


    private TaxiEntity newTaxi = new TaxiEntity(1,"xxxx-0000");

    private TrajectoryEntity newTrajectory0 = new TrajectoryEntity(0,newTaxi, Timestamp.valueOf("2008-02-02 14:22:40"),39.96525, 116.30508);
    private TrajectoryEntity newTrajectoryUpdated = new TrajectoryEntity(0,newTaxi, Timestamp.valueOf("2008-02-02 14:22:40"), 0.0,0.0);
    private TrajectoryEntity newTrajectory2 = new TrajectoryEntity(2,newTaxi, Timestamp.valueOf("2008-02-02 14:22:45"),39.96505, 116.30568);

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/api/v1/trajectories?page=0&pageSize=2"))
                .andExpect(jsonPath("$.content").isEmpty());

        taxisRepository.save(newTaxi);
        trajectoriesRepository.save(newTrajectory0);
        trajectoriesRepository.save(newTrajectory2);

        mockMvc.perform(get("/api/v1/trajectories?page=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content.[0].taxi").value(newTaxi));
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get("/api/v1/trajectories?page=0&pageSize=10"))
                .andExpect(jsonPath("$.content").isEmpty());

        taxisRepository.save(newTaxi);
        trajectoriesRepository.save(newTrajectory0);

        mockMvc.perform(get("/api/v1/trajectory?id=0"))
                .andExpect(jsonPath("$.id").value(newTrajectory0.getId()));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(get("/api/v1/trajectories?page=0&pageSize=10"))
                .andExpect(jsonPath("$.content").isEmpty());

        taxisRepository.save(newTaxi);
        String newTrajectoryAsString = new ObjectMapper().writeValueAsString(newTrajectory0);

        mockMvc.perform(post("/api/v1/trajectory")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(newTrajectoryAsString))
                .andExpect(status().isCreated());

    }

    @Test
    void update() throws Exception {
        mockMvc.perform(get("/api/v1/trajectories?page=0&pageSize=10"))
                .andExpect(jsonPath("$.content").isEmpty());

        taxisRepository.save(newTaxi);
        trajectoriesRepository.save(newTrajectory0);

        String newTrajectoryAsString = new ObjectMapper().writeValueAsString(newTrajectoryUpdated);

        mockMvc.perform(put("/api/v1/trajectory?id=0")
                .contentType(APPLICATION_JSON_UTF8)
                .content(newTrajectoryAsString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newTrajectory0.getId()))
                .andExpect(jsonPath("$.longitude").value(0))
                .andExpect(jsonPath("$.latitude").value(0));

    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(get("/api/v1/trajectories?page=0&pageSize=10"))
                .andExpect(jsonPath("$.content").isEmpty());

        taxisRepository.save(newTaxi);
        trajectoriesRepository.save(newTrajectory2);

        mockMvc.perform(get("/api/v1/trajectory?id=2"))
                .andExpect(jsonPath("$.id").value(newTrajectory2.getId()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/trajectory?id=2"))
                .andExpect(status().isOk());
    }
}