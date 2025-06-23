package com.prueba_fs.cl.Prueba_fs.Controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.prueba_fs.cl.Prueba_fs.controller.EnvioController;
import com.prueba_fs.cl.Prueba_fs.model.Envio;
import com.prueba_fs.cl.Prueba_fs.model.Venta;
import com.prueba_fs.cl.Prueba_fs.service.EnvioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Envio envio;

    @BeforeEach
    public void setUp() {
        envio = new Envio();
        envio.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        envio.setVenta(venta);
        envio.setDireccionDestino("Calle Falsa 123");
        envio.setEstado("Enviado");
        envio.setFechaEnvio(LocalDate.now());
    }

    @Test
    public void testGetAllEnvios() throws Exception {
        when(envioService.obtenerTodos()).thenReturn(List.of(envio));

        mockMvc.perform(get("/envios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(envio.getId()))
                .andExpect(jsonPath("$[0].venta.id").value(envio.getVenta().getId()))
                .andExpect(jsonPath("$[0].direccionDestino").value(envio.getDireccionDestino()))
                .andExpect(jsonPath("$[0].estado").value(envio.getEstado()))
                .andExpect(jsonPath("$[0].fechaEnvio").value(envio.getFechaEnvio().toString()));
    }

    @Test
    public void testGetEnvioById() throws Exception {
        when(envioService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(envio));

        mockMvc.perform(get("/envios/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(envio.getId()))
                .andExpect(jsonPath("$.venta.id").value(envio.getVenta().getId()))
                .andExpect(jsonPath("$.direccionDestino").value(envio.getDireccionDestino()))
                .andExpect(jsonPath("$.estado").value(envio.getEstado()))
                .andExpect(jsonPath("$.fechaEnvio").value(envio.getFechaEnvio().toString()));
    }

    @Test
    public void testCreateEnvio() throws Exception {
        when(envioService.guardar(any(Envio.class))).thenReturn(envio);

        mockMvc.perform(post("/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(envio.getId()))
                .andExpect(jsonPath("$.venta.id").value(envio.getVenta().getId()))
                .andExpect(jsonPath("$.direccionDestino").value(envio.getDireccionDestino()))
                .andExpect(jsonPath("$.estado").value(envio.getEstado()))
                .andExpect(jsonPath("$.fechaEnvio").value(envio.getFechaEnvio().toString()));
    }

    @Test
    public void testUpdateEnvio() throws Exception {
        when(envioService.actualizar(eq(1L), any(Envio.class))).thenReturn(envio);

        mockMvc.perform(put("/envios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(envio.getId()))
                .andExpect(jsonPath("$.venta.id").value(envio.getVenta().getId()))
                .andExpect(jsonPath("$.direccionDestino").value(envio.getDireccionDestino()))
                .andExpect(jsonPath("$.estado").value(envio.getEstado()))
                .andExpect(jsonPath("$.fechaEnvio").value(envio.getFechaEnvio().toString()));
    }

    @Test
    public void testDeleteEnvio() throws Exception {
        when(envioService.existePorId(1L)).thenReturn(true);
        doNothing().when(envioService).eliminar(1L);

        mockMvc.perform(delete("/envios/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
