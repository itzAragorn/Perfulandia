package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.AdministradorModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.Administrador;
import com.prueba_fs.cl.Prueba_fs.service.AdministradorService;
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
@RequestMapping("/v2/administradores")
public class AdministradorControllerv2 {
    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private AdministradorModelAssembler administradorModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Administrador>> obtenerTodos() {
        List<EntityModel<Administrador>> administradores = administradorService.obtenerTodos().stream()
                .map(administradorModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(administradores,
                linkTo(methodOn(AdministradorControllerv2.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Administrador> obtenerPorId(@PathVariable Long id) {
        Administrador administrador = administradorService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        return administradorModelAssembler.toModel(administrador);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Administrador>> guardar(@RequestBody Administrador administrador) {
        Administrador nuevoAdministrador = administradorService.guardar(administrador);
        return ResponseEntity.created(linkTo(methodOn(AdministradorControllerv2.class).obtenerPorId(nuevoAdministrador.getId())).toUri())
                .body(administradorModelAssembler.toModel(nuevoAdministrador));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Administrador> actualizar(@PathVariable Long id, @RequestBody Administrador administrador) {
        Administrador actualizado = administradorService.actualizar(id, administrador);
        return administradorModelAssembler.toModel(actualizado);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        administradorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
