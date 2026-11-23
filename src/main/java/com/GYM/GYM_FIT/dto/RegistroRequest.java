package com.GYM.GYM_FIT.dto;
import jakarta.validation.constraints.*;
public record RegistroRequest(@NotBlank String nombre,@Email @NotBlank String email,@NotBlank @Size(min=8) String password){}
