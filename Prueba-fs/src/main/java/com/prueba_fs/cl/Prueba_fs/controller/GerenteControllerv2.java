package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.GerenteModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.Gerente;
import com.prueba_fs.cl.Prueba_fs.service.GerenteService;
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
@RequestMapping("/v2/gerentes")
public class GerenteControllerv2 {
    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private GerenteModelAssembler gerenteModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Gerente>> obtenerTodos() {
        List<EntityModel<Gerente>> gerentes = gerenteService.obtenerTodos().stream()
                .map(gerenteModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(gerentes,
                linkTo(methodOn(GerenteControllerv2.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Gerente> obtenerPorId(@PathVariable Long id) {
        Gerente gerente = gerenteService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Gerente no encontrado"));
        return gerenteModelAssembler.toModel(gerente);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Gerente>> guardar(@RequestBody Gerente gerente) {
        Gerente nuevoGerente = gerenteService.guardar(gerente);
        return ResponseEntity.created(linkTo(methodOn(GerenteControllerv2.class).obtenerPorId(nuevoGerente.getId())).toUri())
                .body(gerenteModelAssembler.toModel(nuevoGerente));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Gerente> actualizar(@PathVariable Long id, @RequestBody Gerente gerente) {
        Gerente actualizado = gerenteService.actualizar(id, gerente);
        return gerenteModelAssembler.toModel(actualizado);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gerenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
