package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.DetalleVentaModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.DetalleVenta;
import com.prueba_fs.cl.Prueba_fs.service.DetalleVentaService;
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
@RequestMapping("/v2/detalle-ventas")
public class DetalleVentaControllerv2 {
    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private DetalleVentaModelAssembler detalleVentaModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<DetalleVenta>> obtenerTodos() {
        List<EntityModel<DetalleVenta>> detalleVentas = detalleVentaService.obtenerTodos().stream()
                .map(detalleVentaModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(detalleVentas,
                linkTo(methodOn(DetalleVentaControllerv2.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<DetalleVenta> obtenerPorId(@PathVariable Long id) {
        DetalleVenta detalleVenta = detalleVentaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado"));
        return detalleVentaModelAssembler.toModel(detalleVenta);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetalleVenta>> guardar(@RequestBody DetalleVenta detalleVenta) {
        DetalleVenta nuevoDetalleVenta = detalleVentaService.guardar(detalleVenta);
        return ResponseEntity.created(linkTo(methodOn(DetalleVentaControllerv2.class).obtenerPorId(nuevoDetalleVenta.getId())).toUri())
                .body(detalleVentaModelAssembler.toModel(nuevoDetalleVenta));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<DetalleVenta> actualizar(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta actualizado = detalleVentaService.actualizar(id, detalleVenta);
        return detalleVentaModelAssembler.toModel(actualizado);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detalleVentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
