package com.jdaniek.bibliotecaapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditorialRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
}