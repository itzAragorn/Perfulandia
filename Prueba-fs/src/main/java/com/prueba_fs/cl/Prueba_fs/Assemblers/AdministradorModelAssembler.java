package com.prueba_fs.cl.Prueba_fs.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.prueba_fs.cl.Prueba_fs.controller.AdministradorControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.Administrador;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AdministradorModelAssembler implements RepresentationModelAssembler<Administrador, EntityModel<Administrador>>{
    @Override
    public EntityModel<Administrador> toModel(Administrador administrador) {
        return EntityModel.of(administrador,
                linkTo(methodOn(AdministradorControllerv2.class).obtenerPorId(administrador.getId())).withSelfRel(),
                linkTo(methodOn(AdministradorControllerv2.class).obtenerTodos()).withRel("administradores"));
    }
}
