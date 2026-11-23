package com.GYM.GYM_FIT.dto;
import jakarta.validation.constraints.NotBlank;
public record CrearSocioRequest(@NotBlank String nombres, String dni, String telefono, String fotoUrl) {}
