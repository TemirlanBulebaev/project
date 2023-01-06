package com.example.project.services;

import com.example.project.payload.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${jwt.token.password.reset.duration}")
    private Long expiration;

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Подтверждение отправкии сообщения
     */
    public void sendMessageVerification (String emailTo, String emailConfirmationUrl) throws MessagingException {

        Mail mailMessage = new Mail();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject("Email Verification ");
        mailMessage.setMessage("Добро пожаловать! " + "\n Чтобы завершить регистрацию перейдите по ссылке \n " + "" + emailConfirmationUrl);
        send(mailMessage);
    }

    private void send(Mail mailMessage) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        mimeMessageHelper.setTo(mailMessage.getTo());
        mimeMessageHelper.setText(mailMessage.getMessage(), true);
        mimeMessageHelper.setSubject(mailMessage.getSubject());
        mimeMessageHelper.setFrom(mailMessage.getFrom());
        mailSender.send(message);

    }
}
