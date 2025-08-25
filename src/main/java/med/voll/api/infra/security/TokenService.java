package med.voll.api.infra.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private static final String EMISSOR = "API Voll.med";
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    @Value("${api.password.secret}")
    private String secret;

    public String gerarToken(String login) {
        return Jwts.builder()
                .setIssuer(EMISSOR)
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String tokenJWT) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(tokenJWT)
                .getBody()
                .getSubject();
    }
}
