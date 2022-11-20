package com.example.project.entities.token;

import com.example.project.entities.UserDevice;
import com.example.project.entities.audit.DateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "REFRESH_TOKEN")
public class RefreshToken extends DateAudit {

    @Id
    @Column(name = "TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", allocationSize = 1)
    private Long id;

    @Column(name = "TOKEN", nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String token;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_DEVICE_ID", unique = true)
    private UserDevice userDevice;

    @Column(name = "REFRESH_COUNT")
    private Long refreshCount;

    @Column(name = "EXPIRY_DT", nullable = false)
    private Instant expityDate;

    public RefreshToken() {
    }

    public RefreshToken(Long id,
                        String token,
                        UserDevice userDevice,
                        Long refreshCount,
                        Instant expityDate) {
        this.id = id;
        this.token = token;
        this.userDevice = userDevice;
        this.refreshCount = refreshCount;
        this.expityDate = expityDate;
    }

    public void incrementRefreshCount() { // TODO: ?
        refreshCount = refreshCount + 1;
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

    public UserDevice getUserDevice() {
        return this.userDevice;
    }

    public void setUserDevice(UserDevice userDevice) {
        this.userDevice = userDevice;
    }

    public Long getRefreshCount() {
        return this.refreshCount;
    }

    public void setRefreshCount(Long refreshCount) {
        this.refreshCount = refreshCount;
    }

    public Instant getExpityDate() {
        return this.expityDate;
    }

    public void setExpityDate(Instant expityDate) {
        this.expityDate = expityDate;
    }

}
