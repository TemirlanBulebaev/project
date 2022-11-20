package com.example.project.entities.token;

import com.example.project.entities.User;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "VERIFICATION_TOKEN")
public class EmailVerificationToken {
    @Id
    @Column(name = "TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

    //targetEntity = User.class Настроить сопоставление "многие ко многим"
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "USER_ID")
    private User user;

    @Column(name = "TOKEN_STATUS")
    @Enumerated (EnumType.STRING)
    private TokenStatus tokenStatus;

    @Column(name = "EXPIRY_DT", nullable = false)//ИСТЕКАЮЩИЙ СРОК ГОДНОСТИ
    private Instant expiryDate;//expiryDate

    public EmailVerificationToken() {}

    public EmailVerificationToken(EmailVerificationToken verificationToken) {
        this.id = verificationToken.id;
        this.token = verificationToken.token;
        this.user = verificationToken.user;
        this.tokenStatus = verificationToken.tokenStatus;
        this.expiryDate = verificationToken.expiryDate;
    }

    public void setConfirmedStatus() {
        setTokenStatus(TokenStatus.STATUS_CONFIRMED);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TokenStatus getTokenStatus() {
        return this.tokenStatus;
    }

    public void setTokenStatus(TokenStatus tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", token='" + getToken() + "'" +
                ", user='" + getUser() + "'" +
                ", tokenStatus='" + getTokenStatus() + "'" +
                ", expiryDate='" + getExpiryDate() + "'" +
                "}";
    }
}
