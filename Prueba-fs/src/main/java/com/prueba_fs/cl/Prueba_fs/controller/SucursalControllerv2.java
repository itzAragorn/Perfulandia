package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.SucursalModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.Sucursal;
import com.prueba_fs.cl.Prueba_fs.service.SucursalService;
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
@RequestMapping("/v2/sucursales")
public class SucursalControllerv2 {
    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler sucursalModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Sucursal>> obtenerTodas() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.obtenerTodas().stream()
                .map(sucursalModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalControllerv2.class).obtenerTodas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Sucursal> obtenerPorId(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        return sucursalModelAssembler.toModel(sucursal);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> guardar(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.guardar(sucursal);
        return ResponseEntity.created(linkTo(methodOn(SucursalControllerv2.class).obtenerPorId(nuevaSucursal.getId())).toUri())
                .body(sucursalModelAssembler.toModel(nuevaSucursal));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Sucursal> actualizar(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal actualizada = sucursalService.actualizar(id, sucursal);
        return sucursalModelAssembler.toModel(actualizada);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
