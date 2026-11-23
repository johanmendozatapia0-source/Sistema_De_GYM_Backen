package com.GYM.GYM_FIT.service;
import com.GYM.GYM_FIT.dto.*;
import com.GYM.GYM_FIT.entity.*;
import com.GYM.GYM_FIT.repository.UsuarioRepository;
import com.GYM.GYM_FIT.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AuthService {
    private final UsuarioRepository usuarios;private final PasswordEncoder encoder;private final AuthenticationManager auth;private final JwtService jwt;
    public AuthService(UsuarioRepository u,PasswordEncoder e,AuthenticationManager a,JwtService j){usuarios=u;encoder=e;auth=a;jwt=j;}
    @Transactional
    public AuthResponse registrar(RegistroRequest r){
        String email=r.email().trim().toLowerCase();if(usuarios.existsByEmail(email))throw new IllegalArgumentException("El correo ya está registrado");
        Usuario u=new Usuario();u.setNombre(r.nombre().trim());u.setEmail(email);u.setPassword(encoder.encode(r.password()));
        // El registro público siempre crea RECEPCION para que nadie pueda autoasignarse ADMIN.
        u.setRol(RolUsuario.RECEPCION);return respuesta(usuarios.save(u));
    }
    public AuthResponse login(LoginRequest r){auth.authenticate(new UsernamePasswordAuthenticationToken(r.email().trim().toLowerCase(),r.password()));return usuarios.findByEmail(r.email().trim().toLowerCase()).map(this::respuesta).orElseThrow();}
    private AuthResponse respuesta(Usuario u){return new AuthResponse(jwt.generar(u),"Bearer",u.getId(),u.getNombre(),u.getEmail(),u.getRol().name());}
}
