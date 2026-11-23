package com.GYM.GYM_FIT.dto;
import jakarta.validation.constraints.NotBlank;
public record ValidarAccesoRequest(@NotBlank String codigoSocio) {}
