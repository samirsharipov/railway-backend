package uz.optimit.railway.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Role;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class JwtProvider {

    private static final String SECRET_KEY = "eyJhbGciOiJIUzI1NiJ9eyJhZG1pbiI6InJvb3QxMjMifQqZyeL3Q6NWpOJeWvbSyY0Nh45S81Biv3yJ5cQx5uw";// Tokenni imzolash uchun maxfiy kalit
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // Tokenning amal qilish muddati (24 soat)

    public String generateToken(String username, Role role) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME); // Tokenning tugash sanasi
        return Jwts.builder()
                .setSubject(username) // Token ichidagi foydalanuvchi nomi
                .setIssuedAt(new Date()) // Token chiqarilgan vaqt
                .setExpiration(expireDate) // Tokenning tugash sanasi
                .signWith(SignatureAlgorithm.HS256, getSignInKey()) // Tokenni imzolash
                .claim("role", role.getRoleType().name()) // Token ichiga foydalanuvchi roli qo'shish
                .compact(); // Tokenni yaratish
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey()) // Tokenni imzolash uchun kalit
                .parseClaimsJws(token) // Tokenni tahlil qilish
                .getBody()
                .getSubject(); // Token ichidagi foydalanuvchi nomi
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSignInKey()).parseClaimsJws(token); // Tokenni tahlil qilish
            return true; // Token haqiqiy
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage()); // Xatolikni qayd etish
            return false; // Token noto'g'ri
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
