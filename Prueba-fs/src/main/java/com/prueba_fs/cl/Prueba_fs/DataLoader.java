package com.prueba_fs.cl.Prueba_fs;

import com.prueba_fs.cl.Prueba_fs.model.*;
import com.prueba_fs.cl.Prueba_fs.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private GerenteRepository gerenteRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private StockRepository stockRepository;

    @Override
    public void run(String... args) throws Exception {
        if (administradorRepository.count() > 0 ||
            gerenteRepository.count() > 0 ||
            empleadoRepository.count() > 0 ||
            productoRepository.count() > 0 ||
            ventaRepository.count() > 0) {
            System.out.println("La base de datos ya está poblada. No se insertan datos de prueba.");
            return;
        }
        Faker faker = new Faker();
        Random random = new Random();
        // Crear administradores
        for (int i = 0; i < 2; i++) {
            Administrador administrador = new Administrador();
            administrador.setNombre(faker.name().fullName());
            administrador.setCorreo(faker.internet().emailAddress());
            administrador.setPassword(faker.internet().password());
            administradorRepository.save(administrador);
        }

        // Crear gerentes
        for (int i = 0; i < 4; i++) {
            Gerente gerente = new Gerente();
            gerente.setNombre(faker.name().fullName());
            gerente.setCorreo(faker.internet().emailAddress());
            gerente.setPassword(faker.internet().password());
            gerenteRepository.save(gerente);
        }
        // Crear empleados
        for (int i = 0; i < 6; i++) {
            Empleado empleado = new Empleado();
            empleado.setNombre(faker.name().fullName());
            empleado.setCorreo(faker.internet().emailAddress());
            empleado.setPassword(faker.internet().password());
            empleadoRepository.save(empleado);
        }

        // Crear sucursales
        for (int i = 0; i < 3; i++) {
            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(faker.company().name());
            sucursal.setDireccion(faker.address().fullAddress());
            sucursalRepository.save(sucursal);
        }
        // Crear productos
        for (int i = 0; i < 10; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setPrecio(Double.parseDouble(faker.commerce().price()));
            productoRepository.save(producto);
        }

        // Crear ventas
        List<Producto> productos = productoRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Venta venta = new Venta();
            Date fechaAleatoria = faker.date().past(730, java.util.concurrent.TimeUnit.DAYS);
            LocalDateTime fecha = fechaAleatoria.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            venta.setFecha(fecha);
            venta.setEmpleado(empleadoRepository.findById((long) (random.nextInt(5) + 7)).orElse(null));
            ventaRepository.save(venta);

            // Crear detalles de venta
            for (int j = 0; j < 3; j++) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setProducto(productos.get(random.nextInt(productos.size())));
                detalleVenta.setCantidad(random.nextInt(5) + 1);
                detalleVenta.setPrecioUnitario(detalleVenta.getProducto().getPrecio());
                detalleVentaRepository.save(detalleVenta);
            }
        }

        // Crear envíos
        List<Venta> ventas = ventaRepository.findAll();
        int cantidadEnvios = Math.min(10, ventas.size());
        for (int i = 0; i < cantidadEnvios; i++) {
            Envio envio = new Envio();
            envio.setVenta(ventas.get(i)); // Ahora cada venta solo se usa una vez
            envio.setDireccionDestino(faker.address().fullAddress());
            envio.setEstado(faker.options().option("Pendiente", "Enviado", "Entregado"));
            Date fechaAleatoria = faker.date().past(730, java.util.concurrent.TimeUnit.DAYS);
            LocalDate fecha = fechaAleatoria.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            envio.setFechaEnvio(fecha);
            envioRepository.save(envio);
        }

        // Crear stocks
        List<Producto> productosParaStock = productoRepository.findAll();
        List<Sucursal> sucursales = sucursalRepository.findAll(); // Obtén todas las sucursales existentes
        for (int i = 0; i < productosParaStock.size(); i++) {
            Stock stock = new Stock();
            stock.setProducto(productosParaStock.get(i));
            stock.setSucursal(sucursales.get(random.nextInt(sucursales.size())));
            stock.setCantidad(random.nextInt(100) + 1); // Cantidad aleatoria entre 1 y 100
            stockRepository.save(stock);
        }
    }
}
