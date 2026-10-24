package com.jdaniek.bibliotecaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EjemplarResponseDTO {

    private Long id;
    private Long idLibro;
    private boolean prestado;
}
