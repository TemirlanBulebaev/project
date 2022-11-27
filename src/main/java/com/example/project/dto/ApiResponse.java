package com.example.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL) //@JsonInclude используется при исключении свойств, имеющих значения null / empty или значения по умолчанию.

public class ApiResponse {

    private final String data;//Данные
    private final Boolean success;//Успех
    private final String timestamp;//Отметка времени
    private final String cause;//Причина
    private final String path;//Путь

    public ApiResponse(Boolean success, String data, String cause, String path) {
        this.timestamp = Instant.now().toString();
        this.data = data;
        this.success = success;
        this.cause = cause;
        this.path = path;
    }

    public ApiResponse(Boolean success, String data) {
        this.timestamp = Instant.now().toString();
        this.data = data;
        this.success = success;
        this.cause = null;
        this.path = null;
    }


    public String getData() {
        return this.data;
    }

    public Boolean isSuccess() {
        return this.success;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getCause() {
        return this.cause;
    }

    public String getPath() {
        return this.path;
    }

}
