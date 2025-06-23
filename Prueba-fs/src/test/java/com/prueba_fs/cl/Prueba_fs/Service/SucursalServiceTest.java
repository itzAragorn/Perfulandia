package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.prueba_fs.cl.Prueba_fs.model.Sucursal;
import com.prueba_fs.cl.Prueba_fs.repository.SucursalRepository;
import com.prueba_fs.cl.Prueba_fs.service.SucursalService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class SucursalServiceTest {
    @Autowired
    private SucursalService sucursalService;

    @MockBean
    private SucursalRepository sucursalRepository;

    @Test
    public void testFindAllSucursales() {
        Sucursal sucursal1 = new Sucursal();
        sucursal1.setNombre("Sucursal 1");
        sucursal1.setDireccion("Direccion 1");
        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal1));

        List<Sucursal> result = sucursalService.obtenerTodas();

        assertEquals(1, result.size());
        assertEquals("Sucursal 1", result.get(0).getNombre());
    }

    @Test
    public void testFindSucursalById() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal 1");
        sucursal.setDireccion("Direccion 1");
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));

        Optional<Sucursal> result = sucursalService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Sucursal 1", result.get().getNombre());
    }

    @Test
    public void testSaveSucursal() {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre("Sucursal 1");
        sucursal.setDireccion("Direccion 1");
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);

        Sucursal result = sucursalService.guardar(sucursal);

        assertNotNull(result);
        assertEquals("Sucursal 1", result.getNombre());
    }

    @Test
    public void testUpdateSucursal() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal 1");
        sucursal.setDireccion("Direccion 1");
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(sucursalRepository.existsById(1L)).thenReturn(true);
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);

        Sucursal result = sucursalService.actualizar(1L,sucursal);

        assertNotNull(result);
        assertEquals("Sucursal 1", result.getNombre());
    }

    @Test
    public void testDeleteSucursalById() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal 1");
        sucursal.setDireccion("Direccion 1");
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        
        sucursalService.eliminar(1L);

        verify(sucursalRepository, times(1)).deleteById(1L);
    }
}
