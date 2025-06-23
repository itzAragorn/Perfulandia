package com.prueba_fs.cl.Prueba_fs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba_fs.cl.Prueba_fs.model.Administrador;
import com.prueba_fs.cl.Prueba_fs.service.AdministradorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/administradores")
@Tag(name = "Administrador", description = "Operaciones relacionadas con los administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    @Operation(summary = "Obtener todos los administradores", description = "Devuelve una lista de todos los administradores registrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener la lista de administradores")
    })
    public List<Administrador> obtenerTodos() {
        return administradorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener administrador por ID", description = "Devuelve un administrador específico por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
        @ApiResponse(responseCode = "404", description = "Administrador no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener el administrador")
    })
    public Optional<Administrador> obtenerPorId(@PathVariable Long id) {
        return administradorService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo administrador", description = "Crea un nuevo administrador.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Administrador creado exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el administrador")
    })
    public Administrador guardar(@RequestBody Administrador administrador) {
        return administradorService.guardar(administrador);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar administrador", description = "Actualiza un administrador existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Administrador actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Administrador no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el administrador")
    })
    public Administrador actualizar(@PathVariable Long id, @RequestBody Administrador administrador) {
        return administradorService.actualizar(id, administrador);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar administrador", description = "Elimina un administrador existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Administrador eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Administrador no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el administrador")
    })
    public void eliminar(@PathVariable Long id) {
        administradorService.eliminar(id);
    }
    
    @DeleteMapping()
    @Operation(summary = "Eliminar todos los administradores", description = "Elimina todos los administradores existentes.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Todos los administradores eliminados exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar los administradores")
    })
    public void eliminarTodos(){
        administradorService.eliminarTodos();
    }
}
