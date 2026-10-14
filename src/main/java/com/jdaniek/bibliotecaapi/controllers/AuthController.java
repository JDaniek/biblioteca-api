package com.jdaniek.bibliotecaapi.controllers;

import com.jdaniek.bibliotecaapi.dto.LoginRequestDTO;
import com.jdaniek.bibliotecaapi.dto.LoginResponseDTO;
import com.jdaniek.bibliotecaapi.services.AutenticacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AutenticacionService autenticacionService;

    public AuthController(AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        LoginResponseDTO respuesta = autenticacionService.autenticar(loginDTO);

        if (respuesta == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }

        return ResponseEntity.ok(respuesta);
    }
}
