package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.prueba_fs.cl.Prueba_fs.model.Envio;
import com.prueba_fs.cl.Prueba_fs.model.Venta;
import com.prueba_fs.cl.Prueba_fs.repository.EnvioRepository;
import com.prueba_fs.cl.Prueba_fs.service.EnvioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class EnvioServiceTest {
    @Autowired
    private EnvioService envioService;

    @MockBean
    private EnvioRepository envioRepository;

    @Test
    public void testFindAllEnvios() {
        Envio envio1 = new Envio();
        envio1.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        envio1.setVenta(venta);
        envio1.setDireccionDestino("Calle Falsa 123");
        envio1.setEstado("Enviado");
        envio1.setFechaEnvio(LocalDate.now()); 
        when(envioRepository.findAll()).thenReturn(List.of(envio1));

        List<Envio> result = envioService.obtenerTodos();

        assertEquals(1, result.size());
        assertEquals("Calle Falsa 123", result.get(0).getDireccionDestino());
    }

    @Test
    public void testFindEnvioById() {
        Envio envio = new Envio();
        envio.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        envio.setVenta(venta);
        envio.setDireccionDestino("Calle Falsa 123");
        envio.setEstado("Enviado");
        envio.setFechaEnvio(LocalDate.now());
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        Optional<Envio> result = envioService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Calle Falsa 123", result.get().getDireccionDestino());
    }

    @Test
    public void testSaveEnvio() {
        Envio envio = new Envio();
        envio.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        envio.setVenta(venta);
        envio.setDireccionDestino("Calle Falsa 123");
        envio.setEstado("Enviado");
        envio.setFechaEnvio(LocalDate.now());
        when(envioRepository.save(envio)).thenReturn(envio);

        Envio result = envioService.guardar(envio);

        assertNotNull(result);
        assertEquals("Calle Falsa 123", result.getDireccionDestino());
    }

    @Test
    public void testUpdateEnvio() {
        Envio envio = new Envio();
        envio.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        envio.setVenta(venta);
        envio.setDireccionDestino("Calle Falsa 123");
        envio.setEstado("Enviado");
        envio.setFechaEnvio(LocalDate.now());
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        when(envioRepository.existsById(1L)).thenReturn(true);
        when(envioRepository.save(envio)).thenReturn(envio);

        Envio result = envioService.actualizar(1L, envio);

        assertNotNull(result);
        assertEquals("Calle Falsa 123", result.getDireccionDestino());
    }

    @Test
    public void testDeleteEnvio() {
        Envio envio = new Envio();
        envio.setId(1L);
        Venta venta = new Venta();
        venta.setId(1L);
        envio.setVenta(venta);
        envio.setDireccionDestino("Calle Falsa 123");
        envio.setEstado("Enviado");
        envio.setFechaEnvio(LocalDate.now());
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        
        envioService.eliminar(1L);

        verify(envioRepository, times(1)).deleteById(1L);
    }

}
