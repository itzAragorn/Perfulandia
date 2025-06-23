package com.prueba_fs.cl.Prueba_fs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba_fs.cl.Prueba_fs.model.Envio;
import com.prueba_fs.cl.Prueba_fs.repository.EnvioRepository;

@Service
public class EnvioService {
    

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> obtenerTodos() {
        return envioRepository.findAll();
    }

    public Optional<Envio> obtenerPorId(Long id) {
        return envioRepository.findById(id);
    }

    public Envio guardar(Envio envio) {
        return envioRepository.save(envio);
    }

    public Envio actualizar(Long id, Envio envio) {
        if (envioRepository.existsById(id)) {
            envio.setId(id);
            return envioRepository.save(envio);
        } else {
            throw new RuntimeException("Envio de id: " + id + " no encontrado");
        }
    }

    public void eliminar(Long id) {
        envioRepository.deleteById(id);
    }

    public void eliminarTodos(){
        envioRepository.deleteAll();
    }

    public boolean existePorId(Long id) {
        return envioRepository.existsById(id);
    }
    
}
