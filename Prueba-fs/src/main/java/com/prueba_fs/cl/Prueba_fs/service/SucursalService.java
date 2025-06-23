package com.prueba_fs.cl.Prueba_fs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba_fs.cl.Prueba_fs.model.Sucursal;
import com.prueba_fs.cl.Prueba_fs.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> obtenerTodas() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> obtenerPorId(Long id) {
        return sucursalRepository.findById(id);
    }

    public Sucursal guardar(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public Sucursal actualizar(Long id, Sucursal sucursal) {
        if (sucursalRepository.existsById(id)) {
            sucursal.setId(id);
            return sucursalRepository.save(sucursal);
        } else {
            throw new RuntimeException("Sucursal de id: " + id + " no encontrada");
        }
    }

    public void eliminar(Long id) {
        sucursalRepository.deleteById(id);
    }

    public void eliminarTodos(){
        sucursalRepository.deleteAll();
    }
}
