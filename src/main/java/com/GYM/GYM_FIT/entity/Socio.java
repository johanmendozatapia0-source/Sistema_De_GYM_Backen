package com.GYM.GYM_FIT.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "socios")
public class Socio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String codigoSocio;
    @NotBlank @Column(nullable = false, length = 120)
    private String nombres;
    @Column(unique = true, length = 20)
    private String dni;
    private String telefono;
    private String fotoUrl;
    @Column(nullable = false)
    private boolean activo = true;
    private LocalDate fechaRegistro = LocalDate.now();
    private LocalDate membresiaHasta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membresia_id")
    private Membresia membresia;

    public Socio() {}
    public Long getId() { return id; }
    public String getCodigoSocio() { return codigoSocio; }
    public void setCodigoSocio(String codigoSocio) { this.codigoSocio = codigoSocio; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public LocalDate getMembresiaHasta() { return membresiaHasta; }
    public void setMembresiaHasta(LocalDate membresiaHasta) { this.membresiaHasta = membresiaHasta; }
    public Membresia getMembresia() { return membresia; }
    public void setMembresia(Membresia membresia) { this.membresia = membresia; }
}
