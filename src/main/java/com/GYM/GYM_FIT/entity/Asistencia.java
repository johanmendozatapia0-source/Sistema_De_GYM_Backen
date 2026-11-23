package com.GYM.GYM_FIT.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime fechaHoraEntrada = LocalDateTime.now();
    @Column(nullable = false)
    private boolean accesoPermitido;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "socio_id")
    private Socio socio;
    @Column(length = 200)
    private String motivo;
    public Asistencia() {}
    public Long getId() { return id; }
    public LocalDateTime getFechaHoraEntrada() { return fechaHoraEntrada; }
    public boolean isAccesoPermitido() { return accesoPermitido; }
    public void setAccesoPermitido(boolean accesoPermitido) { this.accesoPermitido = accesoPermitido; }
    public Socio getSocio() { return socio; }
    public void setSocio(Socio socio) { this.socio = socio; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
