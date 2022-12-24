package com.example.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyGreenCoffeeException extends RuntimeException {

    private final String title;
    private final String fieldName;//имя поля
    private final transient Object fieldValue;//значение поля

    public AlreadyGreenCoffeeException(String title, String fieldName, Object fieldValue) {
        super(String.format("%s already in use with %s : '%s'", title, fieldName, fieldValue));
        this.title = title;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getTitle() {
        return title;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
