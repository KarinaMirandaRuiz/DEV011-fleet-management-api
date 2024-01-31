package com.example.dev011fleetmanagementapi;

import com.example.dev011fleetmanagementapi.model.dao.TaxisRepository;
import com.example.dev011fleetmanagementapi.model.entity.TaxiEntity;
import com.example.dev011fleetmanagementapi.service.impl.TaxiImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgisContainerProvider;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.PostgreSQLContainerProvider;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.testcontainers.containers.PostgreSQLContainer;



@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class FleetManagementApiRestApplicationTests {


	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private TaxiImpl taxiImpl;
	@Autowired
	private TaxisRepository taxisRepository;

	@BeforeEach
	void setup() {
		taxisRepository.deleteAll();
	}

	// factorizar código
	@Container
	static PostgreSQLContainer postgres = new PostgreSQLContainer(
			DockerImageName.parse("postgres")
					.asCompatibleSubstituteFor("postgres")
	)
			.withDatabaseName("test")
			.withUsername("def")
			.withPassword("pass");
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://localhost:%d/test",postgres.getFirstMappedPort()));
		registry.add("spring.datasource.username", () -> "def");
		registry.add("spring.datasource.password", () -> "pass");
	}

	@Test
	@DisplayName("Probando el primer endpoint: Text...")
	void context() throws Exception {

		// Cambiar la parte de la consulta de
		taxisRepository.save(new TaxiEntity(1,"asdf-1234"));
		taxisRepository.save(new TaxiEntity(2,"xxxx-1111"));

		ResultActions resultActions = mockMvc.perform(get("/api/v1/taxis?page=0&pageSize=2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.size()").value(2));

	}


//	@BeforeEach
//    void setUp throws void Exception{
//		MockitoAnnotations.initMocks(this);
//	}

//	@Test
//	@DisplayName("Probando el primer endpoint: Text...")
//	void context() throws Exception {
//
//		// Cambiar la parte de la consulta de
//		mockMvc.perform(get("/api/v1/taxis?page=1&pageSize=1"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.content[0]").value(hasKey("id")))
//				.andExpect(jsonPath("$.content[0]").value(hasKey("plate")))
//				.andExpect(jsonPath("$.content[0].id").value(108))
//				.andExpect(jsonPath("$.content[0].plate").value("LNGK-1108"));
//	}




//	@Test
//	@DisplayName("Mock en el primer endpoint: Text...")
//	void context2() throws Exception {
//		// Crear algunos datos de prueba
//		TaxiEntity taxi1 = new TaxiEntity(1, "ABC123");
//		TaxiEntity taxi2 = new TaxiEntity(2, "XYZ789");
//		List<TaxiEntity> taxiList = Arrays.asList(taxi1, taxi2);
//		Page<TaxiEntity> taxiPage = new PageImpl<>(taxiList, PageRequest.of(1, 1), taxiList.size());
//
//		taxisRepository = mock(TaxisRepository.class);
//
//
//		//ITaxi taxiImpl =  new TaxiImpl(taxisRepository);
//		// Configurar el comportamiento del mock
//		when(taxisRepository.findAll(PageRequest.of(1, 1).withSort(Sort.by(Sort.Direction.ASC, "id")))).thenReturn(taxiPage);
//		// Realizar la solicitud y realizar aserciones
//		//assertTrue(Objects.nonNull(taxiImpl.findAllTaxis(1,1)));
//		mockMvc.perform(get("/api/v1/taxis?page=1&pageSize=1"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.length()").value(2)) // Ajustar según la respuesta real
//				.andExpect(jsonPath("$[0].id").value(1))
//				.andExpect(jsonPath("$[0].plate").value("ABC123"))
//				.andExpect(jsonPath("$[1].id").value(2))
//				.andExpect(jsonPath("$[1].plate").value("XYZ789"));
//	}

}

