package com.prueba_fs.cl.Prueba_fs.Controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.prueba_fs.cl.Prueba_fs.controller.EmpleadoController;
import com.prueba_fs.cl.Prueba_fs.model.Empleado;
import com.prueba_fs.cl.Prueba_fs.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
@WebMvcTest(EmpleadoController.class)
public class EmpleadoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Empleado empleado;

    @BeforeEach
    public void setUp() {
        empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Empleado Test");
        empleado.setCorreo("empleadotest@gmail.com");
        empleado.setPassword("password123");
    }

    @Test
    public void testGetAllEmpleados() throws Exception {
        when(empleadoService.obtenerTodos()).thenReturn(List.of(empleado));

        mockMvc.perform(get("/empleados")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(empleado.getId()))
                .andExpect(jsonPath("$[0].nombre").value(empleado.getNombre()))
                .andExpect(jsonPath("$[0].correo").value(empleado.getCorreo()));
    }

    @Test
    public void testGetEmpleadoById() throws Exception {
        when(empleadoService.obtenerPorId(1L)).thenReturn(java.util.Optional.of(empleado));

        mockMvc.perform(get("/empleados/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(empleado.getId()))
                .andExpect(jsonPath("$.nombre").value(empleado.getNombre()))
                .andExpect(jsonPath("$.correo").value(empleado.getCorreo()));
    }

    @Test
    public void testCreateEmpleado() throws Exception {
        when(empleadoService.guardar(any(Empleado.class))).thenReturn(empleado);

        mockMvc.perform(post("/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(empleado.getId()))
                .andExpect(jsonPath("$.nombre").value(empleado.getNombre()))
                .andExpect(jsonPath("$.correo").value(empleado.getCorreo()));
    }

    @Test
    public void testUpdateEmpleado() throws Exception {
        when(empleadoService.actualizar(eq(1L),any(Empleado.class))).thenReturn(empleado);

        mockMvc.perform(put("/empleados/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(empleado.getId()))
                .andExpect(jsonPath("$.nombre").value(empleado.getNombre()))
                .andExpect(jsonPath("$.correo").value(empleado.getCorreo()));
    }

    @Test
    public void testDeleteEmpleado() throws Exception {
        doNothing().when(empleadoService).eliminar(1L);

        mockMvc.perform(delete("/empleados/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
