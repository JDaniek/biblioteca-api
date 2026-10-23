package com.jdaniek.bibliotecaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String sexo;
    private String email;
    private String telefono;
}
