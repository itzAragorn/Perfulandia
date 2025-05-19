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

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    public List<Administrador> obtenerTodos() {
        return administradorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Administrador> obtenerPorId(@PathVariable Long id) {
        return administradorService.obtenerPorId(id);
    }

    @PostMapping
    public Administrador guardar(@RequestBody Administrador administrador) {
        return administradorService.guardar(administrador);
    }

    @PutMapping("/{id}")
    public Administrador actualizar(@PathVariable Long id, @RequestBody Administrador administrador) {
        return administradorService.actualizar(id, administrador);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        administradorService.eliminar(id);
    }
    
    @DeleteMapping()
    public void eliminarTodos(){
        administradorService.eliminarTodos();
    }
}
