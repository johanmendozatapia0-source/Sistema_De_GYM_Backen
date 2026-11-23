package com.GYM.GYM_FIT.entity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=120) private String nombre;
    @Column(nullable=false,unique=true,length=180) private String email;
    @Column(nullable=false) private String password;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private RolUsuario rol=RolUsuario.RECEPCION;
    public Usuario() {}
    public void setNombre(String nombre){this.nombre=nombre;}
    public void setEmail(String email){this.email=email;}
    public void setPassword(String password){this.password=password;}
    public void setRol(RolUsuario rol){this.rol=rol;}
    public Long getId(){return id;}
    public String getNombre(){return nombre;}
    public String getEmail(){return email;}
    public RolUsuario getRol(){return rol;}
    @Override public Collection<? extends GrantedAuthority> getAuthorities(){return List.of(new SimpleGrantedAuthority("ROLE_"+rol.name()));}
    @Override public String getPassword(){return password;}
    @Override public String getUsername(){return email;}
}
