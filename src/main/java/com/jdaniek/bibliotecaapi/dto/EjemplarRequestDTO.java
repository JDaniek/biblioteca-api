package com.jdaniek.bibliotecaapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EjemplarRequestDTO {

    @NotNull(message = "La cantidad de ejemplares es obligatoria")
    @Min(value = 1, message = "La cantidad mínima de ejemplares es 1")
    @Max(value = 100,message = "La cantidad máxima de ejemplares son 100")
    private Integer cantidad;
}
