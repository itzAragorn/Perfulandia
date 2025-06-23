package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.prueba_fs.cl.Prueba_fs.model.Gerente;
import com.prueba_fs.cl.Prueba_fs.repository.GerenteRepository;
import com.prueba_fs.cl.Prueba_fs.service.GerenteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class GerenteServiceTest {
    @Autowired
    private GerenteService gerenteService;

    @MockBean
    private GerenteRepository gerenteRepository;

    @Test
    public void testFindAllGerentes() {
        Gerente gerente1 = new Gerente();
        gerente1.setNombre("gerente1");
        gerente1.setCorreo("gerente1@example.com");
        gerente1.setPassword("password1");
        when(gerenteRepository.findAll()).thenReturn(List.of(gerente1));
        List<Gerente> result = gerenteService.obtenerTodos();
        assertEquals(1, result.size());
        assertEquals("gerente1", result.get(0).getNombre());
    }
    @Test
    public void testFindGerenteById() {
        Gerente gerente = new Gerente();
        gerente.setId(1L);
        gerente.setNombre("gerente1");
        gerente.setCorreo("gerente1@example.com");
        gerente.setPassword("password1");
        when(gerenteRepository.findById(1L)).thenReturn(Optional.of(gerente));

        Optional<Gerente> result = gerenteService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("gerente1", result.get().getNombre());
    }

    @Test
    public void testSaveGerente() {
        Gerente gerente = new Gerente();
        gerente.setNombre("gerente1");
        gerente.setCorreo("gerente1@example.com");
        gerente.setPassword("password1");
        when(gerenteRepository.save(gerente)).thenReturn(gerente);

        Gerente result = gerenteService.guardar(gerente);

        assertNotNull(result);
        assertEquals("gerente1", result.getNombre());
    }

    @Test
    public void testUpdateGerente() {
        Gerente gerente = new Gerente();
        gerente.setId(1L);
        gerente.setNombre("gerente1");
        gerente.setCorreo("gerente1@example.com");
        gerente.setPassword("password1");
        when(gerenteRepository.findById(1L)).thenReturn(Optional.of(gerente));
        when(gerenteRepository.existsById(1L)).thenReturn(true);
        when(gerenteRepository.save(gerente)).thenReturn(gerente);

        Gerente updatedGerente = gerenteService.actualizar(1L, gerente);

        assertNotNull(updatedGerente);
        assertEquals("gerente1", updatedGerente.getNombre());
    }

    @Test
    public void testDeleteGerenteById() {
        Gerente gerente = new Gerente();
        gerente.setId(1L);
        gerente.setNombre("gerente1");
        gerente.setCorreo("gerente1@example.com");
        gerente.setPassword("password1");
        when(gerenteRepository.findById(1L)).thenReturn(Optional.of(gerente));

        gerenteService.eliminar(1L);

        verify(gerenteRepository, times(1)).deleteById(1L);
    }
}
