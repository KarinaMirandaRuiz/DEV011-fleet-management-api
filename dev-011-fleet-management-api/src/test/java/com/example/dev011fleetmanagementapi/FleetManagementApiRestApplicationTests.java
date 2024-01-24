package com.example.dev011fleetmanagementapi;

import com.example.dev011fleetmanagementapi.model.dao.TaxisRepository;
import com.example.dev011fleetmanagementapi.model.entity.TaxiEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FleetManagementApiRestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TaxisRepository taxisRepository;

	@Test
	@DisplayName("Probando el primer endpoint: Text...")
	void context() throws Exception {

		mockMvc.perform(get("/api/v1/taxis?page=1&pageSize=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0]").value(hasKey("id")))
				.andExpect(jsonPath("$.content[0]").value(hasKey("plate")))
				.andExpect(jsonPath("$.content[0].id").value(108))
				.andExpect(jsonPath("$.content[0].plate").value("LNGK-1108"));
	}

	@Test
	@DisplayName("Mock en el primer endpoint: Text...")
	void context2() throws Exception {
		// Crear algunos datos de prueba
		TaxiEntity taxi1 = new TaxiEntity(1, "ABC123");
		TaxiEntity taxi2 = new TaxiEntity(2, "XYZ789");
		Iterable<TaxiEntity> taxiList = Arrays.asList(taxi1, taxi2);

		taxisRepository = mock(TaxisRepository.class);

		// Configurar el comportamiento del mock
		when(taxisRepository.findAll(PageRequest.of(1, 1).withSort(Sort.by(Sort.Direction.ASC, "id")))).thenReturn((Page<TaxiEntity>) taxiList);
		// Realizar la solicitud y realizar aserciones
		mockMvc.perform(get("/api/v1/taxis?page=1&pageSize=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2)) // Ajustar según la respuesta real
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].plate").value("ABC123"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].plate").value("XYZ789"));
	}


}
