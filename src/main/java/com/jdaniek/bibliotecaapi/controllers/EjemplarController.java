package com.jdaniek.bibliotecaapi.controllers;

import com.jdaniek.bibliotecaapi.dto.EjemplarRequestDTO;
import com.jdaniek.bibliotecaapi.dto.EjemplarResponseDTO;
import com.jdaniek.bibliotecaapi.services.EjemplarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros/{idLibro}/ejemplares")
@CrossOrigin(origins = "*")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    @PostMapping
    public ResponseEntity<List<EjemplarResponseDTO>> crear(@PathVariable Long idLibro,
                                                           @Valid @RequestBody EjemplarRequestDTO ejemplarDTO) {
        List<EjemplarResponseDTO> ejemplaresCreados = ejemplarService.crear(idLibro, ejemplarDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ejemplaresCreados);
    }

    @GetMapping
    public ResponseEntity<List<EjemplarResponseDTO>> listarPorLibro(@PathVariable Long idLibro) {

        return ResponseEntity.ok(ejemplarService.listarPorLibro(idLibro));
    }
}
