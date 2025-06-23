package com.prueba_fs.cl.Prueba_fs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba_fs.cl.Prueba_fs.model.Sucursal;
import com.prueba_fs.cl.Prueba_fs.service.SucursalService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public List<Sucursal> obtenerTodas() {
        return sucursalService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<Sucursal> obtenerPorId(@PathVariable Long id) {
        return sucursalService.obtenerPorId(id);
    }

    @PostMapping()    
    public Sucursal guardar(@RequestBody Sucursal sucursal) {
        return sucursalService.guardar(sucursal);
    }

    @PutMapping("/{id}")
    public Sucursal actualizar(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        return sucursalService.actualizar(id, sucursal);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        sucursalService.eliminar(id);
    }

    @DeleteMapping
    public void eliminarTodos(){
        sucursalService.eliminarTodos();
    }

}
