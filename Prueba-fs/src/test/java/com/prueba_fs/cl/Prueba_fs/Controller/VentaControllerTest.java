package com.prueba_fs.cl.Prueba_fs.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.startsWith;

import com.prueba_fs.cl.Prueba_fs.controller.VentaController;
import com.prueba_fs.cl.Prueba_fs.model.DetalleVenta;
import com.prueba_fs.cl.Prueba_fs.model.Empleado;
import com.prueba_fs.cl.Prueba_fs.model.Venta;
import com.prueba_fs.cl.Prueba_fs.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(VentaController.class)
public class VentaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Venta venta;

    @BeforeEach
    public void setUp() {
        venta = new Venta();
        venta.setId(1L);
        venta.setFecha(LocalDateTime.now());
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        venta.setEmpleado(empleado);
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(1L);
        venta.setDetalles(List.of(detalle));
    }

    @Test
    public void testGetAllVentas() throws Exception {
        when(ventaService.obtenerTodas()).thenReturn(List.of(venta));

        mockMvc.perform(get("/ventas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(venta.getId()))
                .andExpect(jsonPath("$[0].fecha", startsWith(venta.getFecha().toLocalDate().toString())))
                .andExpect(jsonPath("$[0].empleado.id").value(venta.getEmpleado().getId()))
                .andExpect(jsonPath("$[0].detalles[0].id").value(venta.getDetalles().get(0).getId()));
    }

    @Test
    public void testGetVentaById() throws Exception {   
        venta.setId(1L);
        when(ventaService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(venta));

        mockMvc.perform(get("/ventas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(venta.getId()))
                .andExpect(jsonPath("$.fecha", startsWith(venta.getFecha().toLocalDate().toString())))
                .andExpect(jsonPath("$.empleado.id").value(venta.getEmpleado().getId()))
                .andExpect(jsonPath("$.detalles[0].id").value(venta.getDetalles().get(0).getId()));
    }

    @Test
    public void testCreateVenta() throws Exception {
        when(ventaService.guardar(any(Venta.class))).thenReturn(venta);

        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(venta.getId()))
                .andExpect(jsonPath("$.fecha", startsWith(venta.getFecha().toLocalDate().toString())))
                .andExpect(jsonPath("$.empleado.id").value(venta.getEmpleado().getId()))
                .andExpect(jsonPath("$.detalles[0].id").value(venta.getDetalles().get(0).getId()));
    }

    @Test
    public void testUpdateVenta() throws Exception {
        venta.setId(1L);
        when(ventaService.actualizar(eq(1L), any(Venta.class))).thenReturn(venta);

        mockMvc.perform(put("/ventas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(venta.getId()))
                .andExpect(jsonPath("$.fecha", startsWith(venta.getFecha().toLocalDate().toString())))
                .andExpect(jsonPath("$.empleado.id").value(venta.getEmpleado().getId()))
                .andExpect(jsonPath("$.detalles[0].id").value(venta.getDetalles().get(0).getId()));
    }

    @Test
    public void testDeleteVenta() throws Exception {
        doNothing().when(ventaService).eliminar(1L);

        mockMvc.perform(delete("/ventas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
