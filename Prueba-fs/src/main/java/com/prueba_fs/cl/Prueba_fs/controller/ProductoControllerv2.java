package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.ProductoModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.Producto;
import com.prueba_fs.cl.Prueba_fs.service.ProductoService;
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
@RequestMapping("/v2/productos")
public class ProductoControllerv2 {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler productoModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> obtenerProductos() {
        List<EntityModel<Producto>> productos = productoService.obtenerProductos().stream()
                .map(productoModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerv2.class).obtenerProductos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoModelAssembler.toModel(producto);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> guardar(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardar(producto);
        return ResponseEntity.created(linkTo(methodOn(ProductoControllerv2.class).obtenerPorId(nuevoProducto.getId())).toUri())
                .body(productoModelAssembler.toModel(nuevoProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Producto actualizado = productoService.actualizar(id, producto);
        return productoModelAssembler.toModel(actualizado);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
