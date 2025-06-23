package com.prueba_fs.cl.Prueba_fs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba_fs.cl.Prueba_fs.model.Producto;
import com.prueba_fs.cl.Prueba_fs.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerProductos(){
       return productoRepository.findAll(); 
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }
    
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setId(id);
            return productoRepository.save(producto);
        } else {
            throw new RuntimeException("Producto de id: " + id + " no encontrado");
        }
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public void eliminarTodos(){
        productoRepository.deleteAll();
    }
}
