package com.prueba_fs.cl.Prueba_fs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba_fs.cl.Prueba_fs.model.Administrador;
import com.prueba_fs.cl.Prueba_fs.repository.AdministradorRepository;

@Service
public class AdministradorService {
    
    @Autowired
    private AdministradorRepository administradorRepository;

    public List<Administrador> obtenerTodos() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> obtenerPorId(Long id) {
        return administradorRepository.findById(id);
    }

    public Administrador guardar(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public Administrador actualizar(Long id, Administrador administrador) {
        if (administradorRepository.existsById(id)) {
            administrador.setId(id);
            return administradorRepository.save(administrador);
        } else {
            throw new RuntimeException("Administrador de id: " + id + " no encontrado");
        }
    }

    public void eliminar(Long id) {
        administradorRepository.deleteById(id);
    }

    public void eliminarTodos() {
        administradorRepository.deleteAll();
    }
}
