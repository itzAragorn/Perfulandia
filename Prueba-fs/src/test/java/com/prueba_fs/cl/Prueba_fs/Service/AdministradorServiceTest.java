package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.prueba_fs.cl.Prueba_fs.model.Administrador;
import com.prueba_fs.cl.Prueba_fs.repository.AdministradorRepository;
import com.prueba_fs.cl.Prueba_fs.service.AdministradorService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class AdministradorServiceTest {
    @Autowired
    private AdministradorService administradorService;

    @MockBean
    private AdministradorRepository administradorRepository;

    @Test
    public void testFindAllAdministradores() {
        Administrador admin1 = new Administrador();
        admin1.setNombre("admin1");
        admin1.setCorreo("admin1@example.com");
        admin1.setPassword("password1");
        when(administradorRepository.findAll()).thenReturn(List.of(admin1));

        List<Administrador> result = administradorService.obtenerTodos();

        assertEquals(1, result.size());
        assertEquals("admin1", result.get(0).getNombre());
    }

    @Test
    public void testFindAdministradorById() {
        Administrador admin = new Administrador();
        admin.setId(1L);
        admin.setNombre("admin1");
        admin.setCorreo("admin1@example.com");
        admin.setPassword("password1");
        when(administradorRepository.findById(1L)).thenReturn(Optional.of(admin));

        Optional<Administrador> result = administradorService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("admin1", result.get().getNombre());
    }

    @Test
    public void testSaveAdministrador() {
        Administrador admin = new Administrador();
        admin.setNombre("admin1");
        admin.setCorreo("admin1@example.com");
        admin.setPassword("password1");
        when(administradorRepository.save(admin)).thenReturn(admin);

        Administrador result = administradorService.guardar(admin);

        assertNotNull(result);
        assertEquals("admin1", result.getNombre());
    }

    @Test
    public void testUpdateAdministrador() {
        Administrador admin = new Administrador();
        admin.setId(1L);
        admin.setNombre("admin1");
        admin.setCorreo("admin1@example.com");
        admin.setPassword("password1");
        when(administradorRepository.existsById(1L)).thenReturn(true);
        when(administradorRepository.save(admin)).thenReturn(admin);

        Administrador result = administradorService.actualizar(1L, admin);

        assertNotNull(result);
        assertEquals("admin1", result.getNombre());
    }

    @Test
    public void testDeleteAdministradorById() {
        Administrador admin = new Administrador();
        admin.setId(1L);
        admin.setNombre("admin1");
        admin.setCorreo("admin1@example.com");
        admin.setPassword("password1");
        when(administradorRepository.findById(1L)).thenReturn(Optional.of(admin));

        administradorService.eliminar(1L);

        verify(administradorRepository, times(1)).deleteById(1L);
    }

}