package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.EnvioModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.Envio;
import com.prueba_fs.cl.Prueba_fs.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/v2/envios")
public class EnvioControllerv2 {
    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioModelAssembler envioModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Envio>> obtenerTodos() {
        List<EntityModel<Envio>> envios = envioService.obtenerTodos().stream()
                .map(envioModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(envios,
                linkTo(methodOn(EnvioControllerv2.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Envio> obtenerPorId(@PathVariable Long id) {
        Envio envio = envioService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Envio no encontrado"));
        return envioModelAssembler.toModel(envio);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Envio>> guardar(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.guardar(envio);
        return ResponseEntity.created(linkTo(methodOn(EnvioControllerv2.class).obtenerPorId(nuevoEnvio.getId())).toUri())
                .body(envioModelAssembler.toModel(nuevoEnvio));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Envio> actualizar(@PathVariable Long id, @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizar(id, envio);
        return envioModelAssembler.toModel(actualizado);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
