package com.example.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class InventoryIsEmptyException extends RuntimeException {
    private final String title;

    public InventoryIsEmptyException(String title) {
        super(String.format("%s пуста ", title));
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

}
