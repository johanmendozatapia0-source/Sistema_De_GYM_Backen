package com.GYM.GYM_FIT.dto;
import com.GYM.GYM_FIT.entity.Membresia;
import java.math.BigDecimal;
public record MembresiaResponse(Long id, String nombre, int duracionDias, BigDecimal precio, boolean activa) {
    public static MembresiaResponse from(Membresia m) { return new MembresiaResponse(m.getId(), m.getNombre(), m.getDuracionDias(), m.getPrecio(), m.isActiva()); }
}
