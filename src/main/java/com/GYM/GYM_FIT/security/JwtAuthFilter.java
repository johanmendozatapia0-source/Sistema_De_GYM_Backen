package com.GYM.GYM_FIT.security;
import com.GYM.GYM_FIT.repository.UsuarioRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwt; private final UsuarioRepository usuarios;
    public JwtAuthFilter(JwtService jwt,UsuarioRepository usuarios){this.jwt=jwt;this.usuarios=usuarios;}
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain) throws ServletException,IOException{
        String header=request.getHeader("Authorization");
        if(header!=null&&header.startsWith("Bearer ")&&SecurityContextHolder.getContext().getAuthentication()==null){
            try { var user=usuarios.findByEmail(jwt.subject(header.substring(7))).orElse(null);
                if(user!=null){var auth=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));SecurityContextHolder.getContext().setAuthentication(auth);}
            } catch(RuntimeException ignored){ SecurityContextHolder.clearContext(); }
        }
        chain.doFilter(request,response);
    }
}
