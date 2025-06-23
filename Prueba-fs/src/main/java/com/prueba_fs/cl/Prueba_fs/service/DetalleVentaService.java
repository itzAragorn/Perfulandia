package com.prueba_fs.cl.Prueba_fs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba_fs.cl.Prueba_fs.model.DetalleVenta;
import com.prueba_fs.cl.Prueba_fs.repository.DetalleVentaRepository;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<DetalleVenta> obtenerTodos() {
        return detalleVentaRepository.findAll();
    }

    public Optional<DetalleVenta> obtenerPorId(Long id) {
        return detalleVentaRepository.findById(id);
    }

    public DetalleVenta guardar(DetalleVenta detalle) {
        return detalleVentaRepository.save(detalle);
    }

    public DetalleVenta actualizar(Long id, DetalleVenta detalle) {
        if (detalleVentaRepository.existsById(id)) {
            detalle.setId(id);
            return detalleVentaRepository.save(detalle);
        } else {
            throw new RuntimeException("Detalle de venta de id: " + id + " no encontrado");
        }
    }

    public void eliminar(Long id) {
        detalleVentaRepository.deleteById(id);
    }

    public void eliminarTodos(){
        detalleVentaRepository.deleteAll();
    }
}
