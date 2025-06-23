package com.prueba_fs.cl.Prueba_fs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba_fs.cl.Prueba_fs.model.Gerente;
import com.prueba_fs.cl.Prueba_fs.repository.GerenteRepository;

@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    public List<Gerente> obtenerTodos() {
        return gerenteRepository.findAll();
    }

    public Optional<Gerente> obtenerPorId(Long id) {
        return gerenteRepository.findById(id);
    }

    public Gerente guardar(Gerente gerente) {
        return gerenteRepository.save(gerente);
    }

    public Gerente actualizar(Long id, Gerente gerente) {
        if (gerenteRepository.existsById(id)) {
            gerente.setId(id);
            return gerenteRepository.save(gerente);
        } else {
            throw new RuntimeException("Gerente de id: " + id + " no encontrado");
        }
    }

    public void eliminar(Long id) {
        gerenteRepository.deleteById(id);
    }

    public void eliminarTodos(){
        gerenteRepository.deleteAll();
    }

}
