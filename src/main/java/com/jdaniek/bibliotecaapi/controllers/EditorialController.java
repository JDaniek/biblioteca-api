package com.jdaniek.bibliotecaapi.controllers;

import com.jdaniek.bibliotecaapi.dto.EditorialRequestDTO;
import com.jdaniek.bibliotecaapi.dto.EditorialResponseDTO;
import com.jdaniek.bibliotecaapi.services.EditorialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editoriales")
@CrossOrigin(origins = "*")
public class EditorialController {

    private final EditorialService editorialService;

    public EditorialController(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    @PostMapping
    public ResponseEntity<EditorialResponseDTO> crear(@Valid @RequestBody EditorialRequestDTO editorialDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(editorialService.crear(editorialDTO));
    }

    @GetMapping
    public ResponseEntity<List<EditorialResponseDTO>> listarTodos() {
        return ResponseEntity.ok(editorialService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorialResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(editorialService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditorialResponseDTO> actualizar(@PathVariable Long id,
                                                           @Valid @RequestBody EditorialRequestDTO editorialDTO) {
        return ResponseEntity.ok(editorialService.actualizar(id, editorialDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        editorialService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
