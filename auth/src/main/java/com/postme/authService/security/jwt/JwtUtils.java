package com.postme.authService.security.jwt;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.postme.authService.security.services.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${postme.app.cookie.jwtSecret}")
    private String jwtSecret;

    @Value("${postme.app.cookie.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${postme.app.cookie.jwtCookieName}")
    private String jwtCookie;

    @Value("${postme.app.cookie.authPath}")
    private String authCookiePath;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(UserDetails userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getEmail());
        return ResponseCookie.from(jwtCookie, jwt).path(authCookiePath).maxAge(24 * 60 * 60).httpOnly(true).secure(false).build();
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, null).path(authCookiePath).build();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
