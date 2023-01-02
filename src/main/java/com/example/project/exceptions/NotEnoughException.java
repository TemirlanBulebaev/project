package com.example.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotEnoughException extends RuntimeException{

    private final String title;
    private final String fieldName;
    private final Object fieldValue;


    public NotEnoughException(String title, String fieldName, Object fieldValue) {
        super(String.format("%s : %s не хватает:'%s'", title, fieldName, fieldValue));
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
