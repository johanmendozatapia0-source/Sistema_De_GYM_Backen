package com.GYM.GYM_FIT.security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.GYM.GYM_FIT.repository.UsuarioRepository;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import java.util.List;
@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    // El filtro JWT pertenece a la cadena de Spring Security; evita que Boot lo registre también como filtro servlet.
    @Bean FilterRegistrationBean<JwtAuthFilter> jwtFilterServletRegistration(JwtAuthFilter filter){
        FilterRegistrationBean<JwtAuthFilter> registration=new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
    @Bean UserDetailsService userDetailsService(UsuarioRepository users){return email->users.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));}
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception{return c.getAuthenticationManager();}
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthFilter jwtFilter) throws Exception {
        return http.csrf(csrf->csrf.disable()).cors(cors->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a->a.requestMatchers("/api/auth/register","/api/auth/login").permitAll().anyRequest().authenticated())
                .exceptionHandling(e->e.authenticationEntryPoint((request,response,error)->response.sendError(401,"Autenticación requerida"))
                        .accessDeniedHandler((request,response,error)->response.sendError(403,"Acceso denegado")))
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class).httpBasic(b->b.disable()).formLogin(f->f.disable()).build();
    }
    @Bean CorsConfigurationSource corsConfigurationSource(@Value("${app.cors.allowed-origin:http://localhost:4200}") String origin){
        CorsConfiguration c=new CorsConfiguration(); c.setAllowedOrigins(List.of(origin));c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        c.setAllowedHeaders(List.of("Authorization","Content-Type"));c.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;
    }
}
