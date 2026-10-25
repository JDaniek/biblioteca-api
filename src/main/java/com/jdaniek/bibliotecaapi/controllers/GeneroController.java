package com.jdaniek.bibliotecaapi.controllers;

import com.jdaniek.bibliotecaapi.dto.GeneroRequestDTO;
import com.jdaniek.bibliotecaapi.dto.GeneroResponseDTO;
import com.jdaniek.bibliotecaapi.services.GeneroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@CrossOrigin(origins = "*")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @PostMapping
    public ResponseEntity<GeneroResponseDTO> crear(@Valid @RequestBody GeneroRequestDTO generoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generoService.crear(generoDTO));
    }

    @GetMapping
    public ResponseEntity<List<GeneroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(generoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(generoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody GeneroRequestDTO generoDTO) {
        return ResponseEntity.ok(generoService.actualizar(id, generoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        generoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
