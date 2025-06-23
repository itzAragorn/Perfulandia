package com.prueba_fs.cl.Prueba_fs.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.prueba_fs.cl.Prueba_fs.controller.ProductoController;
import com.prueba_fs.cl.Prueba_fs.model.Producto;
import com.prueba_fs.cl.Prueba_fs.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;

    @BeforeEach
    public void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
    }

    @Test
    public void testGetAllProductos() throws Exception {
        when(productoService.obtenerProductos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(producto.getId()))
                .andExpect(jsonPath("$[0].nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$[0].precio").value(producto.getPrecio()));
    }

    @Test
    public void testGetProductoById() throws Exception {
        when(productoService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(producto));

        mockMvc.perform(get("/productos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(producto.getId()))
                .andExpect(jsonPath("$.nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$.precio").value(producto.getPrecio()));
    }

    @Test
    public void testCreateProducto() throws Exception {
        when(productoService.guardar(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(producto.getId()))
                .andExpect(jsonPath("$.nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$.precio").value(producto.getPrecio()));
    }

    @Test
    public void testUpdateProducto() throws Exception {
        when(productoService.actualizar(eq(1L), any(Producto.class))).thenReturn(producto);

        mockMvc.perform(put("/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(producto.getId()))
                .andExpect(jsonPath("$.nombre").value(producto.getNombre()))
                .andExpect(jsonPath("$.precio").value(producto.getPrecio()));
    }

    @Test
    public void testDeleteProducto() throws Exception {
        doNothing().when(productoService).eliminar(1L);

        mockMvc.perform(delete("/productos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productoService, times(1)).eliminar(1L);
    }
}
