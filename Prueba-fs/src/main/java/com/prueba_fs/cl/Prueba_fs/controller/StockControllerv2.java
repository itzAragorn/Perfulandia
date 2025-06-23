package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.StockModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.Stock;
import com.prueba_fs.cl.Prueba_fs.service.StockService;
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
@RequestMapping("/v2/stocks")
public class StockControllerv2 {
    @Autowired
    private StockService stockService;

    @Autowired
    private StockModelAssembler stockModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Stock>> obtenerTodos() {
        List<EntityModel<Stock>> stocks = stockService.obtenerTodos().stream()
                .map(stockModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(stocks,
                linkTo(methodOn(StockControllerv2.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Stock> obtenerPorId(@PathVariable Long id) {
        Stock stock = stockService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Stock no encontrado"));
        return stockModelAssembler.toModel(stock);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Stock>> guardar(@RequestBody Stock stock) {
        Stock nuevoStock = stockService.guardar(stock);
        return ResponseEntity.created(linkTo(methodOn(StockControllerv2.class).obtenerPorId(nuevoStock.getId())).toUri())
                .body(stockModelAssembler.toModel(nuevoStock)); 
    }

}
