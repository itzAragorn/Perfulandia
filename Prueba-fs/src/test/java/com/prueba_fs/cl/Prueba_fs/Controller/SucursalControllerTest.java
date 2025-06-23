package com.prueba_fs.cl.Prueba_fs.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.prueba_fs.cl.Prueba_fs.controller.SucursalController;
import com.prueba_fs.cl.Prueba_fs.model.Sucursal;
import com.prueba_fs.cl.Prueba_fs.service.SucursalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(SucursalController.class)
public class SucursalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SucursalService sucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Sucursal sucursal;

    @BeforeEach
    public void setUp() {
        sucursal = new Sucursal();
        sucursal.setNombre("Sucursal Test");
        sucursal.setDireccion("Calle Falsa 123");
    }

    @Test
    public void testGetAllSucursales() throws Exception {
        when(sucursalService.obtenerTodas()).thenReturn(List.of(sucursal));

        mockMvc.perform(get("/sucursales")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(sucursal.getNombre()))
                .andExpect(jsonPath("$[0].direccion").value(sucursal.getDireccion()));
    }

    @Test
    public void testGetSucursalById() throws Exception {
        sucursal.setId(1L);
        when(sucursalService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(sucursal));

        mockMvc.perform(get("/sucursales/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sucursal.getId()))
                .andExpect(jsonPath("$.nombre").value(sucursal.getNombre()))
                .andExpect(jsonPath("$.direccion").value(sucursal.getDireccion()));
    }

    @Test
    public void testCreateSucursal() throws Exception {
        when(sucursalService.guardar(any(Sucursal.class))).thenReturn(sucursal);

        mockMvc.perform(post("/sucursales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(sucursal.getNombre()))
                .andExpect(jsonPath("$.direccion").value(sucursal.getDireccion()));
    }

    @Test
    public void testUpdateSucursal() throws Exception {
        sucursal.setId(1L);
        when(sucursalService.actualizar(eq(1L), any(Sucursal.class))).thenReturn(sucursal);

        mockMvc.perform(put("/sucursales/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sucursal.getId()))
                .andExpect(jsonPath("$.nombre").value(sucursal.getNombre()))
                .andExpect(jsonPath("$.direccion").value(sucursal.getDireccion()));
    }

    @Test
    public void testDeleteSucursal() throws Exception {
        doNothing().when(sucursalService).eliminar(1L);

        mockMvc.perform(delete("/sucursales/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
