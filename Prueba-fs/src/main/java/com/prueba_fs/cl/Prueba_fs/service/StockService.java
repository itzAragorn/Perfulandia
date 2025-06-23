package com.prueba_fs.cl.Prueba_fs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba_fs.cl.Prueba_fs.model.Stock;
import com.prueba_fs.cl.Prueba_fs.repository.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> obtenerTodos() {
        return stockRepository.findAll();
    }

    public Optional<Stock> obtenerPorId(Long id) {
        return stockRepository.findById(id);
    }

    public Stock guardar(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock actualizar(Long id, Stock stock) {
        if (stockRepository.existsById(id)) {
            stock.setId(id);
            return stockRepository.save(stock);
        } else {
            throw new RuntimeException("Stock de id: " + id + " no encontrado");
        }
    }

    public void eliminar(Long id) {
        stockRepository.deleteById(id);
    }

    public void eliminarTodos(){
        stockRepository.deleteAll();
    }

}
