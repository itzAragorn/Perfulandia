package com.prueba_fs.cl.Prueba_fs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba_fs.cl.Prueba_fs.model.Envio;
import com.prueba_fs.cl.Prueba_fs.service.EnvioService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public List<Envio> obtenerTodos() {
        return envioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerPorId(@PathVariable Long id) {
        return envioService.obtenerPorId(id)
                .map(envio -> ResponseEntity.ok(envio))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Envio guardar(@RequestBody Envio envio) {
        return envioService.guardar(envio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizar(@PathVariable Long id, @RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.actualizar(id, envio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!envioService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        envioService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public void eliminarTodos(){
        envioService.eliminarTodos();
    }
}
