package com.prueba_fs.cl.Prueba_fs.controller;

import com.prueba_fs.cl.Prueba_fs.Assemblers.EmpleadoModelAssembler;
import com.prueba_fs.cl.Prueba_fs.model.Empleado;
import com.prueba_fs.cl.Prueba_fs.service.EmpleadoService;
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
@RequestMapping("/v2/empleados")
public class EmpleadoControllerv2 {
    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private EmpleadoModelAssembler empleadoModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Empleado>> obtenerTodos() {
        List<EntityModel<Empleado>> empleados = empleadoService.obtenerTodos().stream()
                .map(empleadoModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(empleados,
                linkTo(methodOn(EmpleadoControllerv2.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Empleado> obtenerPorId(@PathVariable Long id) {
        Empleado empleado = empleadoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return empleadoModelAssembler.toModel(empleado);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Empleado>> guardar(@RequestBody Empleado empleado) {
        Empleado nuevoEmpleado = empleadoService.guardar(empleado);
        return ResponseEntity.created(linkTo(methodOn(EmpleadoControllerv2.class).obtenerPorId(nuevoEmpleado.getId())).toUri())
                .body(empleadoModelAssembler.toModel(nuevoEmpleado));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        Empleado actualizado = empleadoService.actualizar(id, empleado);
        return empleadoModelAssembler.toModel(actualizado);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
