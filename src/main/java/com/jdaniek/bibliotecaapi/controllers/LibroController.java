package com.jdaniek.bibliotecaapi.controllers;

import com.jdaniek.bibliotecaapi.dto.LibroRequestDTO;
import com.jdaniek.bibliotecaapi.dto.LibroResponseDTO;
import com.jdaniek.bibliotecaapi.models.Libro;
import com.jdaniek.bibliotecaapi.services.LibroService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<LibroResponseDTO> crear(@Valid @RequestBody LibroRequestDTO libroDTO) {
        LibroResponseDTO libroCreado = libroService.crear(libroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(libroCreado);
    }

    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(libroService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody LibroRequestDTO libroDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(libroService.actualizar(id, libroDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}