package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.prueba_fs.cl.Prueba_fs.model.DetalleVenta;
import com.prueba_fs.cl.Prueba_fs.model.Empleado;
import com.prueba_fs.cl.Prueba_fs.model.Venta;
import com.prueba_fs.cl.Prueba_fs.repository.VentaRepository;
import com.prueba_fs.cl.Prueba_fs.service.VentaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.xmlunit.diff.Comparison.Detail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class VentaServiceTest {
    @Autowired
    private VentaService ventaService;

    @MockBean
    private VentaRepository ventaRepository;

    @Test
    public void testFindAllVentas() {
        Venta venta1 = new Venta();
        venta1.setFecha(LocalDateTime.now());
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        venta1.setEmpleado(empleado);
        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setId(1L);
        venta1.setDetalles(List.of(detalle1));
        when(ventaRepository.findAll()).thenReturn(List.of(venta1));

        List<Venta> result = ventaService.obtenerTodas();

        assertEquals(1, result.size());
        assertEquals(venta1.getFecha(), result.get(0).getFecha());
    }

    @Test
    public void testFindVentaById() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setFecha(LocalDateTime.now());
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        venta.setEmpleado(empleado);
        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setId(1L);
        venta.setDetalles(List.of(detalle1));
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        Optional<Venta> result = ventaService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(venta.getFecha(), result.get().getFecha());
    }

    @Test
    public void testSaveVenta() {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        venta.setEmpleado(empleado);
        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setId(1L);
        venta.setDetalles(List.of(detalle1));
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        Venta result = ventaService.guardar(venta);

        assertNotNull(result);
        assertEquals(venta.getFecha(), result.getFecha());
    }

    @Test
    public void testUpdateVenta() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setFecha(LocalDateTime.now());
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        venta.setEmpleado(empleado);
        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setId(1L);
        venta.setDetalles(List.of(detalle1));
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(ventaRepository.existsById(1L)).thenReturn(true);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        Venta result = ventaService.actualizar(1L, venta);

        assertNotNull(result);
        assertEquals(venta.getId(), result.getId());
    }

    @Test
    public void testDeleteVenta() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setFecha(LocalDateTime.now());
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        venta.setEmpleado(empleado);
        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setId(1L);
        venta.setDetalles(List.of(detalle1));
        doNothing().when(ventaRepository).deleteById(1L);

        ventaService.eliminar(1L);

        verify(ventaRepository, times(1)).deleteById(1L);
    }

}
