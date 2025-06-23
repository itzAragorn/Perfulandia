package com.prueba_fs.cl.Prueba_fs.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.prueba_fs.cl.Prueba_fs.controller.DetalleVentaController;
import com.prueba_fs.cl.Prueba_fs.model.DetalleVenta;
import com.prueba_fs.cl.Prueba_fs.model.Producto;
import com.prueba_fs.cl.Prueba_fs.model.Venta;
import com.prueba_fs.cl.Prueba_fs.service.DetalleVentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
@WebMvcTest(DetalleVentaController.class)
public class DetalleVentaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalleVentaService detalleVentaService;

    @Autowired
    private ObjectMapper objectMapper;

    private DetalleVenta detalleVenta;

    @BeforeEach
    public void setUp() {
        detalleVenta = new DetalleVenta();
        detalleVenta.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        detalleVenta.setVenta(venta);
        Producto producto = new Producto();
        producto.setId(1L);
        detalleVenta.setProducto(producto);
        detalleVenta.setCantidad(2);
        detalleVenta.setPrecioUnitario(100.0);
        // Aquí puedes agregar más configuraciones si es necesario
    }

    @Test
    public void testGetAllDetalles() throws Exception {
        when(detalleVentaService.obtenerTodos()).thenReturn(List.of(detalleVenta));

        mockMvc.perform(get("/detalle-venta")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(detalleVenta.getId()))
                .andExpect(jsonPath("$[0].venta.id").value(detalleVenta.getVenta().getId()))
                .andExpect(jsonPath("$[0].producto.id").value(detalleVenta.getProducto().getId()))
                .andExpect(jsonPath("$[0].cantidad").value(detalleVenta.getCantidad()))
                .andExpect(jsonPath("$[0].precioUnitario").value(detalleVenta.getPrecioUnitario()));
    }

    @Test
    public void testGetDetalleById() throws Exception {
        when(detalleVentaService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(detalleVenta));

        mockMvc.perform(get("/detalle-venta/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(detalleVenta.getId()))
                .andExpect(jsonPath("$.venta.id").value(detalleVenta.getVenta().getId()))
                .andExpect(jsonPath("$.producto.id").value(detalleVenta.getProducto().getId()))
                .andExpect(jsonPath("$.cantidad").value(detalleVenta.getCantidad()))
                .andExpect(jsonPath("$.precioUnitario").value(detalleVenta.getPrecioUnitario()));
    }

    @Test
    public void testCreateDetalle() throws Exception {
        when(detalleVentaService.guardar(any(DetalleVenta.class))).thenReturn(detalleVenta);

        mockMvc.perform(post("/detalle-venta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(detalleVenta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(detalleVenta.getId()))
                .andExpect(jsonPath("$.venta.id").value(detalleVenta.getVenta().getId()))
                .andExpect(jsonPath("$.producto.id").value(detalleVenta.getProducto().getId()))
                .andExpect(jsonPath("$.cantidad").value(detalleVenta.getCantidad()))
                .andExpect(jsonPath("$.precioUnitario").value(detalleVenta.getPrecioUnitario()));
    }

    @Test
    public void testUpdateDetalle() throws Exception {
        when(detalleVentaService.actualizar(eq(1L), any(DetalleVenta.class))).thenReturn(detalleVenta);

        mockMvc.perform(put("/detalle-venta/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(detalleVenta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(detalleVenta.getId()))
                .andExpect(jsonPath("$.venta.id").value(detalleVenta.getVenta().getId()))
                .andExpect(jsonPath("$.producto.id").value(detalleVenta.getProducto().getId()))
                .andExpect(jsonPath("$.cantidad").value(detalleVenta.getCantidad()))
                .andExpect(jsonPath("$.precioUnitario").value(detalleVenta.getPrecioUnitario()));
    }

    @Test
    public void testDeleteDetalle() throws Exception {
        doNothing().when(detalleVentaService).eliminar(1L);

        mockMvc.perform(delete("/detalle-venta/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
