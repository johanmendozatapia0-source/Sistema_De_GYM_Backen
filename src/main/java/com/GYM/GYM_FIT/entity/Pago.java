package com.GYM.GYM_FIT.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    @Column(nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private EstadoPago estado = EstadoPago.PAGADO;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "socio_id")
    private Socio socio;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "membresia_id")
    private Membresia membresia;
    public Pago() {}
    public Long getId() { return id; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }
    public Socio getSocio() { return socio; }
    public void setSocio(Socio socio) { this.socio = socio; }
    public Membresia getMembresia() { return membresia; }
    public void setMembresia(Membresia membresia) { this.membresia = membresia; }
}
