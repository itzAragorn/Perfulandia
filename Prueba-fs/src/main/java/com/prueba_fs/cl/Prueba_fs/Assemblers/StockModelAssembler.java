package com.prueba_fs.cl.Prueba_fs.Assemblers;

import com.prueba_fs.cl.Prueba_fs.controller.StockControllerv2;
import com.prueba_fs.cl.Prueba_fs.model.Stock;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class StockModelAssembler implements RepresentationModelAssembler<Stock, EntityModel<Stock>> {

    @Override
    public EntityModel<Stock> toModel(Stock stock) {
        return EntityModel.of(stock,
                linkTo(methodOn(StockControllerv2.class).obtenerPorId(stock.getId())).withSelfRel(),
                linkTo(methodOn(StockControllerv2.class).obtenerTodos()).withRel("stocks"));
    }

}
