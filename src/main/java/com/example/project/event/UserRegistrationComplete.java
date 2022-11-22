package com.example.project.event;

import com.example.project.entities.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

//Регистрация пользователя завершена
public class UserRegistrationComplete extends ApplicationEvent {//ApplicationEvent - Публикация событий

    //Поля, помеченные ключевым словом transient, не сериализуются.
    private transient UriComponentsBuilder redirectUrl; //для перенаправления
    private User user;

    public UserRegistrationComplete(User user, UriComponentsBuilder redirectUrl) {
        super(user);
        this.user = user;
        this.redirectUrl = redirectUrl;
    }
    public UriComponentsBuilder getRedirectUrl() {
        return this.redirectUrl;
    }

    public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}