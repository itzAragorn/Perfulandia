package com.prueba_fs.cl.Prueba_fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba_fs.cl.Prueba_fs.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
}
