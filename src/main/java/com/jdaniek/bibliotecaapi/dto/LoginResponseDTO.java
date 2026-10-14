package com.jdaniek.bibliotecaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String mensaje;
    private String nombreAdministrador;
}