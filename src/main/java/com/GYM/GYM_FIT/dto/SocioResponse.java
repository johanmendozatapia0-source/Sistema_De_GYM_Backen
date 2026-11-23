package com.GYM.GYM_FIT.dto;
import com.GYM.GYM_FIT.entity.Socio;
import java.time.LocalDate;
public record SocioResponse(Long id, String codigoSocio, String nombres, String dni, String telefono,
                            String fotoUrl, boolean activo, LocalDate fechaRegistro,
                            LocalDate membresiaHasta, Long membresiaId) {
    public static SocioResponse from(Socio s) {
        return new SocioResponse(s.getId(), s.getCodigoSocio(), s.getNombres(), s.getDni(), s.getTelefono(),
                s.getFotoUrl(), s.isActivo(), s.getFechaRegistro(), s.getMembresiaHasta(),
                s.getMembresia() == null ? null : s.getMembresia().getId());
    }
}
