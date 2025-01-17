package horses.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "your_secret_key";
    private static final long EXPIRATION_TIME = 864000; // 1 day in milliseconds

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username for which the token is generated
     * @return the generated JWT token
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * Authenticates a user based on a Bearer token from an HTTP request.
     *
     * @param authorizationHeader the Authorization header containing the Bearer token
     * @param request the HTTP request
     * @return an Authentication object if valid, otherwise null
     */
    public static Authentication authenticateUser(String authorizationHeader, HttpServletRequest request) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        return authenticateToken(token, request);
    }

    /**
     * Validates and extracts claims from a token.
     *
     * @param token the JWT token
     * @return the claims if the token is valid, otherwise null
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Invalid token signature: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing token: " + e.getMessage());
        }
        return null;
    }

    /**
     * Authenticates a user based on a JWT token and sets the security context.
     *
     * @param token the JWT token
     * @param request the HTTP request (optional, can be null for non-HTTP contexts)
     * @return an Authentication object if valid, otherwise null
     */
    public static Authentication authenticateToken(String token, HttpServletRequest request) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null; // Invalid or expired token
        }

        String username = claims.getSubject();
        if (username != null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            if (request != null) {
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        }
        return null;
    }
}
