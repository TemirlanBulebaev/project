package com.example.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class MailSendException extends RuntimeException {

    private final String email;
    private final String message;

    public MailSendException(String email, String message) {
        super(String.format("Ошибка отправки [%s] для пользователя [%s]", message, email));
        this.email = email;
        this.message = message;
    }

    public String getEmail() {
        return this.email;
    }


    public String getMessage() {
        return this.message;
    }


}

