package com.jdaniek.bibliotecaapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank(message = "EL usuario debe tener un nombre")
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank (message = "Indique el sexo del usuario")
    private String sexo;

    @NotBlank (message = "email obligatorio")
    private String email;

    @NotBlank (message = "telefono obligatirio")
    private String telefono;
}
