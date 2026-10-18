package com.jdaniek.bibliotecaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibroResponseDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private String autor;
    private String genero;
    private String editorial;
}