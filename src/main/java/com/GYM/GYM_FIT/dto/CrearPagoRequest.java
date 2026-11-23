package com.GYM.GYM_FIT.dto;
import jakarta.validation.constraints.NotNull;
public record CrearPagoRequest(@NotNull Long socioId, @NotNull Long membresiaId) {}
