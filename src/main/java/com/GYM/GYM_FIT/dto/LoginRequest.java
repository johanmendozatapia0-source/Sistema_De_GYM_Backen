package com.GYM.GYM_FIT.dto;
import jakarta.validation.constraints.*;
public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
