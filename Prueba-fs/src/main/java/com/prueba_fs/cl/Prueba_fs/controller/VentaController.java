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

import com.prueba_fs.cl.Prueba_fs.model.Venta;
import com.prueba_fs.cl.Prueba_fs.service.VentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> obtenerTodas() {
        return ventaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id);
    }

    @PostMapping
    public Venta guardar(@RequestBody Venta venta) {
        return ventaService.guardar(venta);
    }

    @PutMapping("/{id}")
    public Venta actualizar(@PathVariable Long id, @RequestBody Venta venta) {
        return ventaService.actualizar(id, venta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
    }

    @DeleteMapping
    public void eliminarTodos(){
        ventaService.eliminarTodos();
    }
}
