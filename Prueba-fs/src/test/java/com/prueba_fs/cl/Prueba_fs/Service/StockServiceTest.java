package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.prueba_fs.cl.Prueba_fs.model.Producto;
import com.prueba_fs.cl.Prueba_fs.model.Stock;
import com.prueba_fs.cl.Prueba_fs.model.Sucursal;
import com.prueba_fs.cl.Prueba_fs.repository.StockRepository;
import com.prueba_fs.cl.Prueba_fs.service.StockService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class StockServiceTest {
    @Autowired
    private StockService stockService;

    @MockBean
    private StockRepository stockRepository;

    @Test
    public void testFindAllStocks() {
        Stock stock1 = new Stock();
        Producto producto = new Producto();
        producto.setId(1L);
        stock1.setProducto(producto);
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        stock1.setSucursal(sucursal);
        stock1.setCantidad(100);
        when(stockRepository.findAll()).thenReturn(List.of(stock1));

        List<Stock> result = stockService.obtenerTodos();

        assertEquals(1, result.size());
        assertEquals(100, result.get(0).getCantidad());
    }

    @Test
    public void testFindStockById() {
        Stock stock = new Stock();
        Producto producto = new Producto();
        producto.setId(1L);
        stock.setProducto(producto);
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        stock.setSucursal(sucursal);
        stock.setCantidad(100);
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Optional<Stock> result = stockService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(100, result.get().getCantidad());
    }

    @Test
    public void testSaveStock() {
        Stock stock = new Stock();
        Producto producto = new Producto();
        producto.setId(1L);
        stock.setProducto(producto);
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        stock.setSucursal(sucursal);
        stock.setCantidad(100);
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.guardar(stock);

        assertNotNull(result);
        assertEquals(100, result.getCantidad());
    }

    @Test
    public void testUpdateStock() {
        Stock stock = new Stock();
        Producto producto = new Producto();
        producto.setId(1L);
        stock.setProducto(producto);
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        stock.setSucursal(sucursal);
        stock.setCantidad(100);
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(stockRepository.existsById(1L)).thenReturn(true);
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.actualizar(1L, stock);

        assertNotNull(result);
        assertEquals(100, result.getCantidad());
    }

    @Test
    public void testDeleteStock() {
        Stock stock = new Stock();
        Producto producto = new Producto();
        producto.setId(1L);
        stock.setProducto(producto);
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        stock.setSucursal(sucursal);
        stock.setCantidad(100);
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        stockService.eliminar(1L);

        verify(stockRepository, times(1)).deleteById(1L);
    }
    
}
