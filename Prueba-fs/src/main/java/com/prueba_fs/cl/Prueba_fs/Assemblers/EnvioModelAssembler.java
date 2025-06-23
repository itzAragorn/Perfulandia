package com.prueba_fs.cl.Prueba_fs.Assemblers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;

import com.prueba_fs.cl.Prueba_fs.controller.EnvioControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {
    
    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioControllerv2.class).obtenerPorId(envio.getId())).withSelfRel(),
                linkTo(methodOn(EnvioControllerv2.class).obtenerTodos()).withRel("envios"));
    }

}
