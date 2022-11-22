package com.example.project.services;

import com.example.project.entities.User;
import com.example.project.entities.token.EmailVerificationToken;
import com.example.project.entities.token.TokenStatus;
import com.example.project.exceptions.InvalidTokenRequestException;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.repositories.EmailVerificationTokenRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmailVerificationTokenService {
    private static final Logger logger = LogManager.getLogger(EmailVerificationTokenService.class);
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Value("${jwt.token.expired}")
    private Long tokenExpired;

    @Autowired
    public EmailVerificationTokenService(EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    public void createVerificationToken(User user, String token) {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        emailVerificationToken.setUser(user);
        emailVerificationToken.setExpiryDate(Instant.now().plusMillis(tokenExpired));//Токен истек
        logger.info("Generated Email verification token :" );
        emailVerificationTokenRepository.save(emailVerificationToken);
    }


    public EmailVerificationToken findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("EmailVerificationToken", "token", token));
    }

    public void verifyExpiration(EmailVerificationToken verificationToken) {

        if (verificationToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            logger.info("Токен просрочен");
            throw new InvalidTokenRequestException("Email Verification Token", verificationToken.getToken(),
                    "Токен просрочен");
        }
    }

    public EmailVerificationToken save (EmailVerificationToken verificationToken) {
        return emailVerificationTokenRepository.save(verificationToken);
    }
}