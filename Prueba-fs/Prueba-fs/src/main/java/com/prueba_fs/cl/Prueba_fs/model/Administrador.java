package com.prueba_fs.cl.Prueba_fs.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Administrador extends Usuario {
    public Administrador() {
        super();
    }
}
