package com.GYM.GYM_FIT.dto;
import java.time.LocalDateTime;
public record AccesoResponse(String codigoSocio, String nombres, boolean accesoPermitido, String motivo, LocalDateTime fechaHora) {}
