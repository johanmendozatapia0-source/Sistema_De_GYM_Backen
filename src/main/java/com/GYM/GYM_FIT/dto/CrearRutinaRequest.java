package com.GYM.GYM_FIT.dto;
import jakarta.validation.constraints.*;
public record CrearRutinaRequest(@NotBlank String titulo,@Min(1) int duracionSemanas,@NotBlank String nivel,String imagenUrl,Long entrenadorId){}
