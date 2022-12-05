package com.example.project.security;


import com.example.project.advice.cache.LoggedOutJwtTokenCache;
import com.example.project.event.UserLogoutSuccess;
import com.example.project.exceptions.InvalidTokenRequestException;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Валидация токена по подписи, сроку действия и недавнему Logout
 */
@Component
public class JwtTokenValidator {

    private static final Logger logger = LogManager.getLogger(JwtTokenValidator.class);

    private final String secret;
    private final LoggedOutJwtTokenCache loggedOutJwtTokenCache;

    @Autowired
    public JwtTokenValidator(
            @Value("${jwt.token.secret}") String secret,
            LoggedOutJwtTokenCache loggedOutJwtTokenCache) {
        this.secret = secret;
        this.loggedOutJwtTokenCache = loggedOutJwtTokenCache;
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
            throw new InvalidTokenRequestException("JWT", token, "Incorrect signature");

        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
            throw new InvalidTokenRequestException("JWT", token, "Malformed jwt token");

        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            throw new InvalidTokenRequestException("JWT", token, "Token expired. Refresh required");

        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
            throw new InvalidTokenRequestException("JWT", token, "Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
            throw new InvalidTokenRequestException("JWT", token, "Illegal argument token");
        }
        valifateTokenIsNotForALoggedOutDevice(token);
        return true;
    }

    private void valifateTokenIsNotForALoggedOutDevice(String token) {

        UserLogoutSuccess previouslyLoggedOutEvent = loggedOutJwtTokenCache.getLogoutEventForToken(token);
        if (previouslyLoggedOutEvent != null) {
            String userEmail = previouslyLoggedOutEvent.getUserEmail();
            Date logoutEventDate = previouslyLoggedOutEvent.getEventTime();
            String errorMessage = String.format(
                    "Вы недавно вышли из системы. Авторизируйтесь повторно", userEmail,
                    logoutEventDate.toString());
            logger.info(errorMessage);
            throw new InvalidTokenRequestException("tokenType", token, errorMessage);
        }
    }


}

