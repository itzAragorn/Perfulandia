package com.prueba_fs.cl.Prueba_fs.Assemblers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;

import com.prueba_fs.cl.Prueba_fs.controller.EmpleadoControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.Empleado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<Empleado, EntityModel<Empleado>> {

    @Override
    public EntityModel<Empleado> toModel(Empleado empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoControllerv2.class).obtenerPorId(empleado.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerv2.class).obtenerTodos()).withRel("empleados"));
    }

}
