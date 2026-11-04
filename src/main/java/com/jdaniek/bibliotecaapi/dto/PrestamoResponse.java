package com.jdaniek.bibliotecaapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrestamoResponse {
    private Long idPrestamo;
    private Long idUsuario;
    private String nombreUsuario;
    private Long idEjemplar;
    private String tituloLibro;
    private String estatus;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucionAsignada;
    private LocalDateTime fechaDevolucionReal;
}
