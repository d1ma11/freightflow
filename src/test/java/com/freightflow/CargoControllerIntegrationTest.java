package com.freightflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freightflow.model.Cargo;
import com.freightflow.model.Cargo.CargoType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for CargoController using MockMvc.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CargoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllCargos() throws Exception {
        mockMvc.perform(get("/api/cargos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetCargoById() throws Exception {
        mockMvc.perform(get("/api/cargos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void testGetCargoByType() throws Exception {
        mockMvc.perform(get("/api/cargos")
                        .param("type", "GENERAL"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateCargo() throws Exception {
        Cargo cargo = new Cargo("New Cargo", 200.0, 2.0, CargoType.GENERAL);

        mockMvc.perform(post("/api/cargos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cargo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Cargo"));
    }

    @Test
    public void testCreateCargoWithNegativeWeight() throws Exception {
        Cargo cargo = new Cargo("Invalid Cargo", -10.0, 2.0, CargoType.GENERAL);

        mockMvc.perform(post("/api/cargos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cargo)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetNonExistentCargo() throws Exception {
        mockMvc.perform(get("/api/cargos/999"))
                .andExpect(status().isNotFound());
    }
}
