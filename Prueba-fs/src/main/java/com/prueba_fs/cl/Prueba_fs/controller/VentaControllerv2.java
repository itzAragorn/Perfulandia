package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.VentaModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.Venta;
import com.prueba_fs.cl.Prueba_fs.service.VentaService;
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
@RequestMapping("/v2/ventas")
public class VentaControllerv2 {
    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaModelAssembler ventaModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Venta>> obtenerTodas() {
        List<EntityModel<Venta>> ventas = ventaService.obtenerTodas().stream()
                .map(ventaModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaControllerv2.class).obtenerTodas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Venta> obtenerPorId(@PathVariable Long id) {
        Venta venta = ventaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return ventaModelAssembler.toModel(venta);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Venta>> guardar(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.guardar(venta);
        return ResponseEntity.created(linkTo(methodOn(VentaControllerv2.class).obtenerPorId(nuevaVenta.getId())).toUri())
                .body(ventaModelAssembler.toModel(nuevaVenta));
    }
}
