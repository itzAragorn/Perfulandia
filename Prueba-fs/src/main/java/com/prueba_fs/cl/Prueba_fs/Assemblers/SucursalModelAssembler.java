package com.prueba_fs.cl.Prueba_fs.Assemblers;

import com.prueba_fs.cl.Prueba_fs.controller.SucursalControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerv2.class).obtenerPorId(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalControllerv2.class).obtenerTodas()).withRel("sucursales"));
    }

}
