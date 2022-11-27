package com.example.project.event.listener;

import com.example.project.entities.User;
import com.example.project.event.UserRegistrationComplete;
import com.example.project.exceptions.MailSendException;
import com.example.project.services.EmailVerificationTokenService;
import com.example.project.services.MailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationCompleteListener implements ApplicationListener<UserRegistrationComplete> {

    private static final Logger logger = LogManager.getLogger(UserRegistrationCompleteListener.class);

    private final EmailVerificationTokenService emailVerificationTokenService;
    private final MailService mailService;

    @Autowired
    public UserRegistrationCompleteListener(
            EmailVerificationTokenService emailVerificationTokenService,
            MailService mailService) {
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.mailService = mailService;
    }

    @Override
    @Async // TODO: ?
    public void onApplicationEvent(UserRegistrationComplete userRegistrationComplete) { // событие
        sendEmailVerification(userRegistrationComplete);//передаем событие в фунцкию
    }

    /**
     * Отправка подтверждения по электронной почте
     */
    private void sendEmailVerification(UserRegistrationComplete userRegistrationComplete) {


        User user = userRegistrationComplete.getUser();//получаем юзера
        String token = emailVerificationTokenService.createNewToken(); // Создаем новый токен
        emailVerificationTokenService.createVirficationToken(user, token);// создаем подтвержденный токен

        String userEmail =  user.getEmail();// получаем почту изера
        //создаем сообщение для подтверждения
        String emailConfirmationUrl =
                userRegistrationComplete.getRedirectUrl().queryParam("token", token).toUriString();

        try {
            mailService.sendMessageVerification(userEmail, emailConfirmationUrl);
            logger.info("Cообщение о потверждении отправленно на почту " + userEmail);
        } catch (Exception e) {
            logger.error(e);
            throw new MailSendException(userEmail, "Email Verification");
        }

    }

}

