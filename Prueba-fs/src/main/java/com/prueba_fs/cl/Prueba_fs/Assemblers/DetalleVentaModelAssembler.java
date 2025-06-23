package com.prueba_fs.cl.Prueba_fs.Assemblers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.prueba_fs.cl.Prueba_fs.controller.DetalleVentaControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.DetalleVenta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaModelAssembler implements RepresentationModelAssembler<DetalleVenta, EntityModel<DetalleVenta>> {

    @Override
    public EntityModel<DetalleVenta> toModel(DetalleVenta detalleVenta) {
        return EntityModel.of(detalleVenta,
                linkTo(methodOn(DetalleVentaControllerv2.class).obtenerPorId(detalleVenta.getId())).withSelfRel(),
                linkTo(methodOn(DetalleVentaControllerv2.class).obtenerTodos()).withRel("detalleVentas"));
    }

}
