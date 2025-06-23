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

import com.prueba_fs.cl.Prueba_fs.model.Stock;
import com.prueba_fs.cl.Prueba_fs.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
    
    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> obtenerTodos() {
        return stockService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Stock> obtenerPorId(@PathVariable Long id) {
        return stockService.obtenerPorId(id);
    }

    @PostMapping
    public Stock guardar(@RequestBody Stock stock) {
        return stockService.guardar(stock);
    }

    @PutMapping("/{id}")
    public Stock actualizar(@PathVariable Long id, @RequestBody Stock stock) {
        return stockService.actualizar(id, stock);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        stockService.eliminar(id);
    }

    @DeleteMapping
    public void eliminarTodos(){
        stockService.eliminarTodos();
    }
}
