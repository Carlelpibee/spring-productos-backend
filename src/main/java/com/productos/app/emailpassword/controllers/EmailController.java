package com.productos.app.emailpassword.controllers;

import com.productos.app.dto.MensajeDTO;
import com.productos.app.emailpassword.dto.EmailValuesDTO;
import com.productos.app.emailpassword.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin("/")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Value("${spring.mail.username}")
    String emailFrom;

    @GetMapping("/send-email")
    public ResponseEntity<Object> sendEmailTemplate(@RequestBody EmailValuesDTO emailValuesDTO){

        emailValuesDTO.setMailFrom(emailFrom);
        emailValuesDTO.setSubject("Cambio de contraseña");
        emailValuesDTO.setUserName("Juan");
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        emailValuesDTO.setToken(tokenPassword);

        emailService.sendEmailTemplate(emailValuesDTO);
        return new ResponseEntity<Object>(new MensajeDTO("Correo con plantilla enviado con exito"), HttpStatus.OK);
    }

}
