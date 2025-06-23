package com.prueba_fs.cl.Prueba_fs.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.prueba_fs.cl.Prueba_fs.controller.GerenteController;
import com.prueba_fs.cl.Prueba_fs.model.Gerente;
import com.prueba_fs.cl.Prueba_fs.service.GerenteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(GerenteController.class)
public class GerenteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GerenteService gerenteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Gerente gerente;

    @BeforeEach
    public void setUp() {
        gerente = new Gerente();
        gerente.setId(1L);
        gerente.setNombre("Gerente Test");
        gerente.setCorreo("gerente@test.com");
        gerente.setPassword("password123");
    }

    @Test
    public void testGetAllGerentes() throws Exception {
        when(gerenteService.obtenerTodos()).thenReturn(List.of(gerente));

        mockMvc.perform(get("/gerentes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(gerente.getId()))
                .andExpect(jsonPath("$[0].nombre").value(gerente.getNombre()))
                .andExpect(jsonPath("$[0].correo").value(gerente.getCorreo()));
    }

    @Test
    public void testGetGerenteById() throws Exception {
        when(gerenteService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(gerente));

        mockMvc.perform(get("/gerentes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gerente.getId()))
                .andExpect(jsonPath("$.nombre").value(gerente.getNombre()))
                .andExpect(jsonPath("$.correo").value(gerente.getCorreo()));
    }

    @Test
    public void testCreateGerente() throws Exception {
        when(gerenteService.guardar(any(Gerente.class))).thenReturn(gerente);

        mockMvc.perform(post("/gerentes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gerente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gerente.getId()))
                .andExpect(jsonPath("$.nombre").value(gerente.getNombre()))
                .andExpect(jsonPath("$.correo").value(gerente.getCorreo()));
    }

    @Test
    public void testUpdateGerente() throws Exception {
        when(gerenteService.actualizar(eq(1L), any(Gerente.class))).thenReturn(gerente);

        mockMvc.perform(put("/gerentes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gerente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gerente.getId()))
                .andExpect(jsonPath("$.nombre").value(gerente.getNombre()))
                .andExpect(jsonPath("$.correo").value(gerente.getCorreo()));
    }

    @Test
    public void testDeleteGerente() throws Exception {
        doNothing().when(gerenteService).eliminar(1L);

        mockMvc.perform(delete("/gerentes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(gerenteService, times(1)).eliminar(1L);
    }

}
