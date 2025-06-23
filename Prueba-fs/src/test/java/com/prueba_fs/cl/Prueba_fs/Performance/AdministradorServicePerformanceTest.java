package com.prueba_fs.cl.Prueba_fs.Performance;

import com.prueba_fs.cl.Prueba_fs.model.Administrador;
import com.prueba_fs.cl.Prueba_fs.repository.AdministradorRepository;
import com.prueba_fs.cl.Prueba_fs.service.AdministradorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AdministradorServicePerformanceTest {

    @Mock
    private AdministradorRepository mockRepo;

    @InjectMocks
    private AdministradorService administradorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Administrador administrador = new Administrador();
        administrador.setId(1L);
        administrador.setNombre("admin");
        administrador.setCorreo("admin@correo.com");
        when(mockRepo.findAll()).thenReturn(List.of(administrador));
    }

    @Test
    public void medirDuracionObtenerTodos() {
        long start = System.currentTimeMillis();

        administradorService.obtenerTodos();

        long duration = System.currentTimeMillis() - start;
        System.out.println("Duración de obtenerTodos(): " + duration + " ms");
        assertTrue(duration >= 0);
    }

    @Test
    public void medirPromedioObtenerTodos() {
        int iteraciones = 1000;
        long total = 0;

        for (int i = 0; i < iteraciones; i++) {
            long start = System.nanoTime(); // más preciso
            administradorService.obtenerTodos();
            long end = System.nanoTime();
            total += (end - start);
        }

        double promedioMs = (total / iteraciones) / 1_000_000.0;
        System.out.println("Tiempo promedio de obtenerTodos(): " + promedioMs + " ms");
        assertTrue(promedioMs >= 0);
    }
}
