package com.jdaniek.bibliotecaapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrestamoRequest {
    private Long idUsuario;
    private Long idEjemplar;
    private LocalDateTime fechaDevolucionAsignada;
}
