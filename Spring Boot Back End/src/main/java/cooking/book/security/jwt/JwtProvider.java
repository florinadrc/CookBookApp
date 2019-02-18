package cooking.book.security.jwt;

import cooking.book.security.services.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${cooking.book.jwtSecret}")
    private String jwtSecret;

    @Value("${cooking.book.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        byte[] signingKey = DatatypeConverter.parseBase64Binary(jwtSecret);

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }

    boolean validateJwtToken(String authToken){
        byte[] signingKey = DatatypeConverter.parseBase64Binary(jwtSecret);

        try {
            Jwts.parser().setSigningKey(signingKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {}", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    String getUserNameFromJwtToken(String token){
        byte[] signingKey = DatatypeConverter.parseBase64Binary(jwtSecret);

        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
