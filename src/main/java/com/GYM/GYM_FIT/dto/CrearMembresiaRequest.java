package com.GYM.GYM_FIT.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record CrearMembresiaRequest(@NotBlank String nombre, @Min(1) int duracionDias,
                                   @NotNull @DecimalMin("0.01") BigDecimal precio) {}
