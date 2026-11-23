package com.GYM.GYM_FIT.dto;
import com.GYM.GYM_FIT.entity.Pago;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record PagoResponse(Long id, BigDecimal monto, LocalDateTime fechaPago, String estado, String codigoSocio, String membresia) {
    public static PagoResponse from(Pago p) { return new PagoResponse(p.getId(), p.getMonto(), p.getFechaPago(), p.getEstado().name(), p.getSocio().getCodigoSocio(), p.getMembresia().getNombre()); }
}
