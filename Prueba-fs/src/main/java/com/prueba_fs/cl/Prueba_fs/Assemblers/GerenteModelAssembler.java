package com.prueba_fs.cl.Prueba_fs.Assemblers;

import com.prueba_fs.cl.Prueba_fs.controller.GerenteControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.Gerente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class GerenteModelAssembler implements RepresentationModelAssembler<Gerente, EntityModel<Gerente>> {

    @Override
    public EntityModel<Gerente> toModel(Gerente gerente) {
        return EntityModel.of(gerente,
                linkTo(methodOn(GerenteControllerv2.class).obtenerPorId(gerente.getId())).withSelfRel(),
                linkTo(methodOn(GerenteControllerv2.class).obtenerTodos()).withRel("gerentes"));
    }

}