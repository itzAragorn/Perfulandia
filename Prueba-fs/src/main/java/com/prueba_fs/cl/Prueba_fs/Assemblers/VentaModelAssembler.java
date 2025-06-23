package com.prueba_fs.cl.Prueba_fs.Assemblers;

import com.prueba_fs.cl.Prueba_fs.controller.VentaControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.Venta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Override
    public EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(venta,
                linkTo(methodOn(VentaControllerv2.class).obtenerPorId(venta.getId())).withSelfRel(),
                linkTo(methodOn(VentaControllerv2.class).obtenerTodas()).withRel("ventas"));
    }

}
