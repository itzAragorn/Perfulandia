package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.prueba_fs.cl.Prueba_fs.model.Producto;
import com.prueba_fs.cl.Prueba_fs.repository.ProductoRepository;
import com.prueba_fs.cl.Prueba_fs.service.ProductoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class ProductoServiceTest {
    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @Test
    public void testFindAllProductos() {
        Producto producto1 = new Producto();
        producto1.setNombre("producto1");
        producto1.setPrecio(100.0);
        when(productoRepository.findAll()).thenReturn(List.of(producto1));
        List<Producto> result = productoService.obtenerProductos();
        assertEquals(1, result.size());
        assertEquals("producto1", result.get(0).getNombre());
    }

    @Test
    public void testFindProductoById() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("producto1");
        producto.setPrecio(100.0);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("producto1", result.get().getNombre());
    }

    @Test
    public void testSaveProducto() {
        Producto producto = new Producto();
        producto.setNombre("producto1");
        producto.setPrecio(100.0);
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto result = productoService.guardar(producto);

        assertNotNull(result);
        assertEquals("producto1", result.getNombre());
    }

    @Test
    public void testUpdateProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("producto1");
        producto.setPrecio(100.0);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.existsById(1L)).thenReturn(true);
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto updatedProducto = productoService.actualizar(1L, producto);

        assertNotNull(updatedProducto);
        assertEquals("producto1", updatedProducto.getNombre());
        
    }

    @Test
    public void testDeleteProductoById() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("producto1");
        producto.setPrecio(100.0);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        productoService.eliminar(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }
}
