package com.productos.app.emailpassword.controllers;

import com.productos.app.dto.MensajeDTO;
import com.productos.app.emailpassword.dto.ChangePasswordDTO;
import com.productos.app.emailpassword.dto.EmailValuesDTO;
import com.productos.app.emailpassword.services.EmailService;
import com.productos.app.security.model.UsuarioModel;
import com.productos.app.security.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin("/")
public class EmailController {

    @Autowired
    EmailService emailService;
    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;
    private static final String emailSubject = "Cambio de contraseña";

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendEmailTemplate(@RequestBody EmailValuesDTO emailValuesDTO){

        Optional<UsuarioModel> usuarioModelOptional = usuarioService.getByNombreUsuarioOrEmail(emailValuesDTO.getMailTo());
        if(!usuarioModelOptional.isPresent()){
            return new ResponseEntity<Object>(new MensajeDTO("No existe ningun usuario con esas credenciales"), HttpStatus.NOT_FOUND);
        }
        UsuarioModel usuarioModel = usuarioModelOptional.get();

        emailValuesDTO.setMailFrom(emailFrom);
        emailValuesDTO.setSubject(emailSubject);
        emailValuesDTO.setMailTo(usuarioModel.getEmail());
        emailValuesDTO.setUserName(usuarioModel.getNombreUsuario());

        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        emailValuesDTO.setToken(tokenPassword);
        usuarioModel.setTokenPassword(tokenPassword);
        usuarioService.save(usuarioModel);

        emailService.sendEmailTemplate(emailValuesDTO);
        return new ResponseEntity<Object>(new MensajeDTO("Correo con plantilla enviado con exito"), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<Object>(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
        }
        if(!changePasswordDTO.getPassword().equals(changePasswordDTO.getConfirmPassword())){
            return new ResponseEntity<Object>(new MensajeDTO("Las contrasenas no coinciden"), HttpStatus.BAD_REQUEST);
        }

        Optional<UsuarioModel> usuarioModelOptional = usuarioService.getByTokenPassword(changePasswordDTO.getTokenPassword());
        if(!usuarioModelOptional.isPresent()){
            return new ResponseEntity<Object>(new MensajeDTO("Usuario no existe"), HttpStatus.NOT_FOUND);
        }
        UsuarioModel usuarioModel = usuarioModelOptional.get();
        String newPassword = passwordEncoder.encode(changePasswordDTO.getPassword());
        usuarioModel.setPassword(newPassword);
        usuarioModel.setTokenPassword(null);
        usuarioService.save(usuarioModel);

        return new ResponseEntity<Object>(new MensajeDTO("Contrasena actualizada"), HttpStatus.OK);

    }

}
