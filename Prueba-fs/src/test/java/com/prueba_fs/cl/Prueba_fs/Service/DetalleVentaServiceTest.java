package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.prueba_fs.cl.Prueba_fs.model.DetalleVenta;
import com.prueba_fs.cl.Prueba_fs.model.Producto;
import com.prueba_fs.cl.Prueba_fs.model.Venta;
import com.prueba_fs.cl.Prueba_fs.repository.DetalleVentaRepository;
import com.prueba_fs.cl.Prueba_fs.service.DetalleVentaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class DetalleVentaServiceTest {
    @Autowired
    private DetalleVentaService detalleVentaService;

    @MockBean
    private DetalleVentaRepository detalleVentaRepository;

    @Test
    public void testFindAllDetalles() {
        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        detalle1.setVenta(venta);
        Producto producto = new Producto();
        producto.setId(1L);
        detalle1.setProducto(producto);
        detalle1.setCantidad(2);
        detalle1.setPrecioUnitario(100.0);
        when(detalleVentaRepository.findAll()).thenReturn(List.of(detalle1));

        List<DetalleVenta> result = detalleVentaService.obtenerTodos();

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getCantidad());
    }

    @Test
    public void testFindDetalleById() {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        detalle.setVenta(venta);
        Producto producto = new Producto();
        producto.setId(1L);
        detalle.setProducto(producto);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(100.0);
        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.of(detalle));

        Optional<DetalleVenta> result = detalleVentaService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testSaveDetalle() {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        detalle.setVenta(venta);
        Producto producto = new Producto();
        producto.setId(1L);
        detalle.setProducto(producto);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(100.0);
        when(detalleVentaRepository.save(detalle)).thenReturn(detalle);

        DetalleVenta result = detalleVentaService.guardar(detalle);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testupdateDetalle() {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        detalle.setVenta(venta);
        Producto producto = new Producto();
        producto.setId(1L);
        detalle.setProducto(producto);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(100.0);
        
        when(detalleVentaRepository.findById(anyLong())).thenReturn(Optional.of(detalle));
        when(detalleVentaRepository.existsById(1L)).thenReturn(true);
        when(detalleVentaRepository.save(detalle)).thenReturn(detalle);

        DetalleVenta result = detalleVentaService.actualizar(1L, detalle);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testDeleteDetalle() {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        detalle.setVenta(venta);
        Producto producto = new Producto();
        producto.setId(1L);
        detalle.setProducto(producto);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(100.0);
        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.of(detalle));

        detalleVentaService.eliminar(1L);

        verify(detalleVentaRepository, times(1)).deleteById(1L);
    }
    
}




