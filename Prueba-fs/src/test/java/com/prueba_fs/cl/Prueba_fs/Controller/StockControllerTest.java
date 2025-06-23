package com.prueba_fs.cl.Prueba_fs.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.prueba_fs.cl.Prueba_fs.controller.StockController;
import com.prueba_fs.cl.Prueba_fs.model.Producto;
import com.prueba_fs.cl.Prueba_fs.model.Stock;
import com.prueba_fs.cl.Prueba_fs.model.Sucursal;
import com.prueba_fs.cl.Prueba_fs.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(StockController.class)
public class StockControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

    private Stock stock;

    @BeforeEach
    public void setUp() {
        stock = new Stock();
        stock.setId(1L);
        Producto producto = new Producto();
        producto.setId(1L);
        stock.setProducto(producto);
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        stock.setSucursal(sucursal);
        stock.setCantidad(100);
    }

    @Test
    public void testGetAllStocks() throws Exception {   
        when(stockService.obtenerTodos()).thenReturn(List.of(stock));

        mockMvc.perform(get("/stock")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(stock.getId()))
                .andExpect(jsonPath("$[0].producto.id").value(stock.getProducto().getId()))
                .andExpect(jsonPath("$[0].sucursal.id").value(stock.getSucursal().getId()))
                .andExpect(jsonPath("$[0].cantidad").value(stock.getCantidad()));
    }

    @Test
    public void testGetStockById() throws Exception {
        when(stockService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(stock));

        mockMvc.perform(get("/stock/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stock.getId()))
                .andExpect(jsonPath("$.producto.id").value(stock.getProducto().getId()))
                .andExpect(jsonPath("$.sucursal.id").value(stock.getSucursal().getId()))
                .andExpect(jsonPath("$.cantidad").value(stock.getCantidad()));
    }

    @Test
    public void testCreateStock() throws Exception {
        when(stockService.guardar(any(Stock.class))).thenReturn(stock);

        mockMvc.perform(post("/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stock.getId()))
                .andExpect(jsonPath("$.producto.id").value(stock.getProducto().getId()))
                .andExpect(jsonPath("$.sucursal.id").value(stock.getSucursal().getId()))
                .andExpect(jsonPath("$.cantidad").value(stock.getCantidad()));
    }

    @Test
    public void testUpdateStock() throws Exception {
        when(stockService.actualizar(eq(1L), any(Stock.class))).thenReturn(stock);

        mockMvc.perform(put("/stock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stock.getId()))
                .andExpect(jsonPath("$.producto.id").value(stock.getProducto().getId()))
                .andExpect(jsonPath("$.sucursal.id").value(stock.getSucursal().getId()))
                .andExpect(jsonPath("$.cantidad").value(stock.getCantidad()));
    }

    @Test
    public void testDeleteStock() throws Exception {
        doNothing().when(stockService).eliminar(1L);

        mockMvc.perform(delete("/stock/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
}
