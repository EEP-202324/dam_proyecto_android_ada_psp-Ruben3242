package com.example.procesamiento;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.procesamiento.controller.CenterController;
import com.example.procesamiento.model.Center;
import com.example.procesamiento.service.CenterService;

@ExtendWith(SpringExtension.class)
public class CenterControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CenterService centerService;

	@InjectMocks
	private CenterController centerController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(centerController).build();
	}

	@Test // verifica que se pueden obtener todos los centros.
	void testGetAllCenters() throws Exception {
		List<Center> centers = Arrays.asList(
				new Center(1, "Name1", "Web1", "Type1", "Address1", "Phone1", "Description1"),
				new Center(2, "Name2", "Web2", "Type2", "Address2", "Phone2", "Description2"));
		when(centerService.getAllCenters()).thenReturn(centers);

		mockMvc.perform(get("/centers")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(centers.size()));
	}

	@Test
	void testGetAllCentersEmptyCase() throws Exception {
	    when(centerService.getAllCenters()).thenReturn(Collections.emptyList());

	    mockMvc.perform(get("/centers"))
	           .andExpect(status().isOk())
	           .andExpect(jsonPath("$.size()").value(0));
	}

	@Test // verifica que se puede obtener un centro por su id.
	void testGetCenter() throws Exception {
		Center center = new Center(1, "Name1", "Web1", "Type1", "Address1", "Phone1", "Description1");
		when(centerService.getCenter(1)).thenReturn(center);

		mockMvc.perform(get("/centers/{id}", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(center.getName()));
	}

	@Test
	void testGetCenterByIdNotFound() throws Exception {
	    when(centerService.getCenter(anyInt())).thenReturn(null);

	    mockMvc.perform(get("/centers/{id}", 999))
	           .andExpect(status().isNotFound());
	}

//	@Test // verifica crear un centro con datos completos.
//	void testAddCenter() throws Exception {
//		Center center = new Center(1, "Name1", "Web1", "Type1", "Address1", "Phone1", "Description1");
//		when(centerService.saveCenter(any(Center.class))).thenReturn(center);
//
//		mockMvc.perform(post("/centers").contentType(MediaType.APPLICATION_JSON).content(
//				"{\"name\":\"Name1\",\"web\":\"Web1\",\"type\":\"Type1\",\"address\":\"Address1\",\"phone\":\"Phone1\",\"descr\":\"Description1\"}"))
//				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Name1"));
//	}

	@Test
	void testUpdateCenter() throws Exception {
		Center updatedCenter = new Center(1, "UpdatedName", "UpdatedWeb", "UpdatedType", "UpdatedAddress",
				"UpdatedPhone", "UpdatedDescription");
		when(centerService.updateCenter(eq(1), any(Center.class))).thenReturn(updatedCenter);

		mockMvc.perform(put("/centers/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\":\"UpdatedName\",\"web\":\"UpdatedWeb\",\"type\":\"UpdatedType\",\"address\":\"UpdatedAddress\",\"phone\":\"UpdatedPhone\",\"descr\":\"UpdatedDescription\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("UpdatedName"));
	}

	@Test
	void testDeleteCenterWhenCenterExists() throws Exception {
	    when(centerService.existsById(1)).thenReturn(true); 
	    doNothing().when(centerService).deleteCenter(1);

	    mockMvc.perform(delete("/centers/{id}", 1))
	           .andExpect(status().isNoContent()); 
	}

	@Test
	void testDeleteCenterShouldReturnNotFoundIfCenterDoesNotExist() throws Exception {
		doThrow(new RuntimeException("Center not found")).when(centerService).deleteCenter(anyInt());
		mockMvc.perform(delete("/centers/{id}", 999)).andExpect(status().isNotFound());
	}

	@Test
	void testAddCenterShouldCreateCenter() throws Exception {
		Center center = new Center();
		center.setId(1);
		when(centerService.saveCenter(any(Center.class))).thenReturn(center);

		mockMvc.perform(post("/centers").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"New Center\"}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1));
	}

	@Test
	void testUpdateCenterShouldUpdateCenterDetails() throws Exception {
		Center updatedCenter = new Center();
		updatedCenter.setId(1);
		updatedCenter.setName("Updated Name");
		when(centerService.updateCenter(eq(1), any(Center.class))).thenReturn(updatedCenter);

		mockMvc.perform(
				put("/centers/{id}", 1).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Updated Name\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Updated Name"));
	}

	@Test
	void testAddCenterWithIncompleteData() throws Exception {
		mockMvc.perform(
				post("/centers").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"\", \"web\":\"Web1\"}"))
				.andExpect(status().isBadRequest());
	}

}
