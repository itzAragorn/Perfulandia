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

import com.prueba_fs.cl.Prueba_fs.model.DetalleVenta;
import com.prueba_fs.cl.Prueba_fs.service.DetalleVentaService;

@RestController
@RequestMapping("/detalle-venta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping
    public List<DetalleVenta> obtenerTodos() {
        return detalleVentaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<DetalleVenta> obtenerPorId(@PathVariable Long id) {
        return detalleVentaService.obtenerPorId(id);
    }

    @PostMapping
    public DetalleVenta guardar(@RequestBody DetalleVenta detalleVenta) {
        return detalleVentaService.guardar(detalleVenta);
    }

    @PutMapping("/{id}")
    public DetalleVenta actualizar(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        return detalleVentaService.actualizar(id, detalleVenta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        detalleVentaService.eliminar(id);
    }

    @DeleteMapping
    public void eliminarTodos(){
        detalleVentaService.eliminarTodos();
    }

}
