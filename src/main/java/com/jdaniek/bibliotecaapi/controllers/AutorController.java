package com.jdaniek.bibliotecaapi.controllers;

import com.jdaniek.bibliotecaapi.dto.AutorRequestDTO;
import com.jdaniek.bibliotecaapi.dto.AutorResponseDTO;
import com.jdaniek.bibliotecaapi.services.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "*")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<AutorResponseDTO> crear(@Valid @RequestBody AutorRequestDTO autorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.crear(autorDTO));
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody AutorRequestDTO autorDTO) {
        return ResponseEntity.ok(autorService.actualizar(id, autorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
