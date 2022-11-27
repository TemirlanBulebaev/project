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
import java.util.UUID;

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

    /**
     * Создание подтвержденного токена
     */
    public void createVirficationToken(User user, String token) {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();//создаем токен подтвержденной почты
        emailVerificationToken.setToken(token); // устанавливаем токен
        emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);// Устанавливаем изменение статуса
        emailVerificationToken.setUser(user);//устанавливаем юзера
        emailVerificationToken.setExpiryDate(Instant.now().plusMillis(tokenExpired));//обновляем истечение срока действия токена
        logger.info("Generated Email verification token :" );
        emailVerificationTokenRepository.save(emailVerificationToken); //сохраняем подтвержденый токен
    }

    public void verifyExpiration(EmailVerificationToken verificationToken) {

        if (verificationToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            logger.info("Токен просрочен");
            throw new InvalidTokenRequestException("Email Verification Token", verificationToken.getToken(),
                    "Токен просрочен");
        }
    }

    /**
     * Создание токена
     */
    public String createNewToken() {
        return UUID.randomUUID().toString();
    }

    public EmailVerificationToken findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("EmailVerificationToken", "token", token));
    }

    public EmailVerificationToken save (EmailVerificationToken verificationToken) {
        return emailVerificationTokenRepository.save(verificationToken);
    }

}
