package com.GYM.GYM_FIT.security;
import com.GYM.GYM_FIT.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
@Service
public class JwtService {
    private final SecretKey key;
    private final long expirationMs;
    public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms:86400000}") long expirationMs){
        this.key=Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret)); this.expirationMs=expirationMs;
    }
    public String generar(Usuario user){
        Date now=new Date(); return Jwts.builder().subject(user.getEmail()).claim("rol",user.getRol().name())
                .issuedAt(now).expiration(new Date(now.getTime()+expirationMs)).signWith(key).compact();
    }
    public String subject(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
