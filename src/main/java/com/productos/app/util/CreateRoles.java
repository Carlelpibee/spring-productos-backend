package com.productos.app.util;

import com.productos.app.security.enums.RolNombre;
import com.productos.app.security.model.RolModel;
import com.productos.app.security.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {

        RolModel rolAdmin = new RolModel(RolNombre.ROLE_ADMIN);
        RolModel rolUser = new RolModel(RolNombre.ROLE_USER);

        if (!rolService.existsByRolNombre(RolNombre.ROLE_ADMIN)){
            rolService.save(rolAdmin);
        }
        if (!rolService.existsByRolNombre(RolNombre.ROLE_USER)){
            rolService.save(rolUser);
        }
    }
}
