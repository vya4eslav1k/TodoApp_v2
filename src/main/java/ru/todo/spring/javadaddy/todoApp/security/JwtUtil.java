package ru.todo.spring.javadaddy.todoApp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.todo.spring.javadaddy.todoApp.dto.request.AuthRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;


import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil(AuthenticationManager authManager, UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public AuthResponseDto authenticateUser(AuthRequestDto authRequestDto) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authRequestDto.getLogin(), authRequestDto.getPassword());
        authManager.authenticate(authInputToken);
        UserDetails user = userDetailsService.loadUserByUsername(authRequestDto.getLogin());
        String token = generateToken(user.getUsername());
        return new AuthResponseDto(authRequestDto.getLogin(), token);
    }
}
