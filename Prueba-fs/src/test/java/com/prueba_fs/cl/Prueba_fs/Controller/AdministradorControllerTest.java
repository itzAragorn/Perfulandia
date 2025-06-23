package com.prueba_fs.cl.Prueba_fs.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.prueba_fs.cl.Prueba_fs.controller.AdministradorController;
import com.prueba_fs.cl.Prueba_fs.model.Administrador;
import com.prueba_fs.cl.Prueba_fs.service.AdministradorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


@WebMvcTest(AdministradorController.class)
public class AdministradorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdministradorService administradorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Administrador administrador;

    @BeforeEach
    public void setUp() {
        administrador = new Administrador();
        administrador.setId(1L);
        administrador.setNombre("Admin Test");
        administrador.setCorreo("admintest@gmail.com");
        administrador.setPassword("password123");
        }
    
    @Test
    public void testGetAllAdministradores() throws Exception {
        when(administradorService.obtenerTodos()).thenReturn(List.of(administrador));

        mockMvc.perform(get("/administradores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(administrador.getId()))
                .andExpect(jsonPath("$[0].nombre").value(administrador.getNombre()))
                .andExpect(jsonPath("$[0].correo").value(administrador.getCorreo()));
    }

    @Test
    public void testGetAdministradorById() throws Exception {
        when(administradorService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(administrador));

        mockMvc.perform(get("/administradores/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(administrador.getId()))
                .andExpect(jsonPath("$.nombre").value(administrador.getNombre()))
                .andExpect(jsonPath("$.correo").value(administrador.getCorreo()));
    }

    @Test
    public void testCreateAdministrador() throws Exception {
        when(administradorService.guardar(any(Administrador.class))).thenReturn(administrador);

        mockMvc.perform(post("/administradores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(administrador)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(administrador.getId()))
                .andExpect(jsonPath("$.nombre").value(administrador.getNombre()))
                .andExpect(jsonPath("$.correo").value(administrador.getCorreo()));
    }

    @Test
    public void testUpdateAdministrador() throws Exception {
        when(administradorService.actualizar(eq(1L), any(Administrador.class))).thenReturn(administrador);

        mockMvc.perform(put("/administradores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(administrador)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(administrador.getId()))
                .andExpect(jsonPath("$.nombre").value(administrador.getNombre()))
                .andExpect(jsonPath("$.correo").value(administrador.getCorreo()));
    }

    @Test
    public void testDeleteAdministrador() throws Exception {
        doNothing().when(administradorService).eliminar(1L);

        mockMvc.perform(delete("/administradores/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(administradorService, times(1)).eliminar(1L);
    }
        
}

