package com.GYM.GYM_FIT.dto;
import jakarta.validation.constraints.NotBlank;
public record CrearEntrenadorRequest(@NotBlank String nombres,String especialidad,String telefono){}
