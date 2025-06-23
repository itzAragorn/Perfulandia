package com.prueba_fs.cl.Prueba_fs.Assemblers;

import com.prueba_fs.cl.Prueba_fs.controller.ProductoControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerv2.class).obtenerPorId(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerv2.class).obtenerProductos()).withRel("productos"));
    }

}
