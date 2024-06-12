package me.basedatos2.pencaucu.util;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.basedatos2.pencaucu.persistance.entities.Admin;
import me.basedatos2.pencaucu.persistance.entities.Student;
import me.basedatos2.pencaucu.persistance.repositories.AdminRepository;
import me.basedatos2.pencaucu.persistance.repositories.StudentRepository;
import me.basedatos2.pencaucu.services.auth.AuthService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j2
@Service
@RequiredArgsConstructor
public class JWTUtils {
    private final AuthService authService;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private static final String SECRET = "POLVORONES";
    private static final long EXPIRATION_TIME = 864_000_00;

    public String generateToken(Integer ci) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        String role = "USER";
        Student tokenUser = studentRepository.getStudent(ci).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        String name = tokenUser.getName();
        String lastName = tokenUser.getLastname();
        String email = tokenUser.getEmail();
        String birthdate = tokenUser.getBirthdate().toString();
        String career = "Ing. Informatica";

        return Jwts.builder()
                .setSubject(ci.toString())
                .claim("role", role)
                .claim("name", name)
                .claim("lastName", lastName)
                .claim("email", email)
                .claim("birthdate", birthdate)
                .claim("career", career)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    public String generateAdminToken(Integer ci) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        String role = "ADMIN";
        Admin tokenAdmin = adminRepository.getAdmin(ci).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        String name = tokenAdmin.getName();
        String lastName = tokenAdmin.getLastname();
        String email = tokenAdmin.getEmail();
        String birthdate = tokenAdmin.getBirthdate().toString();
        return Jwts.builder()
                .setSubject(ci.toString())
                .claim("role", role)
                .claim("name", name)
                .claim("lastName", lastName)
                .claim("email", email)
                .claim("birthdate", birthdate)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature for token: {} asociated with user: {}", token, getSubjectFromToken(token));
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token for token: {} asociated with user: {}", token, getSubjectFromToken(token));
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token for token: {} asociated with user: {}", token, getSubjectFromToken(token));
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token for token: {} asociated with user: {}", token, getSubjectFromToken(token));
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty for token: {} asociated with user: {}", token, getSubjectFromToken(token));
        }
        return false;
    }

    public UserDetails getUserDetailsFromToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            Integer ci = Integer.valueOf(claims.getSubject());

            return authService.loadUserByCI(ci);
        } catch (Exception e) {
            throw new RuntimeException("Could not extract user details from token", e);
        }
    }

    public GrantedAuthority getUserAuthorities(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return (GrantedAuthority) claims.get("role");
        } catch (Exception e) {
            throw new RuntimeException("Could not extract authorities from token", e);
        }
    }


    public String getSubjectFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            //Retorna -1 cuando el token a analizar tiene una firma que no es de confianza
            return "-1";
        }
    }

    public String getAuthoritiesFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("role").toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not extract authorities from token", e);
        }
    }


}