package com.example.project.exceptions;

import com.example.project.payload.LogOutRequest;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.Date;

public class UserLogoutSuccess extends ApplicationEvent {

    private final String userEmail;
    private final String token;
    private final transient LogOutRequest logoutRequestDto;
    private final Date eventTime;

    public UserLogoutSuccess(String userEmail, String token,
                             LogOutRequest logoutRequestDto) {
        super(userEmail);
        this.userEmail = userEmail;
        this.token = token;
        this.logoutRequestDto = logoutRequestDto;
        this.eventTime = Date.from(Instant.now());
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getToken() {
        return this.token;
    }

    public Date getEventTime() {
        return this.eventTime;
    }

    public LogOutRequest getLogoutRequestDto () {
        return logoutRequestDto;
    }

}
