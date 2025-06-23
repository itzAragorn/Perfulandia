package com.prueba_fs.cl.Prueba_fs.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.prueba_fs.cl.Prueba_fs.model.Empleado;
import com.prueba_fs.cl.Prueba_fs.repository.EmpleadoRepository;
import com.prueba_fs.cl.Prueba_fs.service.EmpleadoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class EmpleadoServiceTest {
    @Autowired
    private EmpleadoService empleadoService;

    @MockBean
    private EmpleadoRepository empleadoRepository;

    @Test
    public void testFindAllEmpleados() {
        Empleado empleado1 = new Empleado();
        empleado1.setNombre("empleado1");
        empleado1.setCorreo("empleado1@example.com");
        empleado1.setPassword("password1");
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado1));
        List<Empleado> result = empleadoService.obtenerTodos();
        assertEquals(1, result.size());
        assertEquals("empleado1", result.get(0).getNombre());
    }
    
    @Test
    public void testFindEmpleadoById() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("empleado1");
        empleado.setCorreo("empleado1@example.com");
        empleado.setPassword("password1");
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        Optional<Empleado> result = empleadoService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("empleado1", result.get().getNombre());
    }

    @Test
    public void testSaveEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setNombre("empleado1");
        empleado.setCorreo("empleado1@example.com");
        empleado.setPassword("password1");
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        Empleado result = empleadoService.guardar(empleado);

        assertNotNull(result);
        assertEquals("empleado1", result.getNombre());
    }

    @Test
    public void testUpdateEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("empleado1");
        empleado.setCorreo("empleado1@example.com");
        empleado.setPassword("password1");
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(empleadoRepository.existsById(1L)).thenReturn(true);
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        Empleado updatedEmpleado = empleadoService.actualizar(1L, empleado);

        assertNotNull(updatedEmpleado);
        assertEquals("empleado1", updatedEmpleado.getNombre());
    }

    @Test
    public void testDeleteEmpleadoById() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("empleado1");
        empleado.setCorreo("empleado1@example.com");
        empleado.setPassword("password1");
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        empleadoService.eliminar(1L);

        verify(empleadoRepository, times(1)).deleteById(1L);
    }
}
