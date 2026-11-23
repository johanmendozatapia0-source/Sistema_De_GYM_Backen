package com.GYM.GYM_FIT.config;
import com.GYM.GYM_FIT.entity.*;
import com.GYM.GYM_FIT.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class AdminBootstrap {
    @Bean CommandLineRunner createInitialAdmin(UsuarioRepository users,PasswordEncoder encoder,
            @Value("${app.admin.email:}") String email,@Value("${app.admin.password:}") String password,
            @Value("${app.admin.name:Administrador}") String name){
        return args->{if(!email.isBlank()&&!password.isBlank()&&!users.existsByEmail(email.toLowerCase())){
            Usuario admin=new Usuario();admin.setNombre(name);admin.setEmail(email.trim().toLowerCase());admin.setPassword(encoder.encode(password));admin.setRol(RolUsuario.ADMIN);users.save(admin);
        }};
    }
}
