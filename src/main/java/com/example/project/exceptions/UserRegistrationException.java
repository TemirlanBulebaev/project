package com.example.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Все аннотации уровня метода @ResponseStatus переопределяют код уровня класса, и если @ResponseStatus не связан с методом, который не создает исключение- a 200 возвращается по умолчанию:
//Код ошибки HTTP 417 Expectation Failed указывает на то, что ожидание, указанное в Expect, не может быть выполнено.
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserRegistrationException extends RuntimeException {

    private final String user;
    private final String message;

    public UserRegistrationException(String user, String message) {
        super(String.format("Failed to register User[%s] : '%s'", user, message));
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return this.user;
    }

    public String getMessage() {
        return this.message;
    }

}
