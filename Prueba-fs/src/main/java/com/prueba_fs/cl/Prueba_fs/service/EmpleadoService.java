package com.prueba_fs.cl.Prueba_fs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba_fs.cl.Prueba_fs.model.Empleado;
import com.prueba_fs.cl.Prueba_fs.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizar(Long id, Empleado empleado) {
        if (empleadoRepository.existsById(id)) {
            empleado.setId(id);
            return empleadoRepository.save(empleado);
        } else {
            throw new RuntimeException("Empleado de id: " + id + " no encontrado");
        }
    }

    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }

    public void eliminarTodos(){
        empleadoRepository.deleteAll();
    }

}
