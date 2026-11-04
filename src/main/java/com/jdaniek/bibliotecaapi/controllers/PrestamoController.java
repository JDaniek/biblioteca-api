package com.jdaniek.bibliotecaapi.controllers;

import com.jdaniek.bibliotecaapi.dto.PrestamoRequest;
import com.jdaniek.bibliotecaapi.dto.PrestamoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jdaniek.bibliotecaapi.services.PrestamoService;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoRequest request) {
        try {
            PrestamoResponse response = prestamoService.crearPrestamo(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/{id}/devolucion")
    public ResponseEntity<?> registrarDevolucion(@PathVariable Long id) {
        try {
            PrestamoResponse response = prestamoService.registrarDevolucion(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PrestamoResponse>> listarTodos() {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PrestamoResponse>> listarActivos() {
        return ResponseEntity.ok(prestamoService.listarActivos());
    }

}
