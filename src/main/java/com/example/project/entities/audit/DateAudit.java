package com.example.project.entities.audit;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

// позволяет вынести общие поля в родительский класс, но при этом не создавать для него отдельную таблицу
@MappedSuperclass
public abstract class DateAudit implements Serializable {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant dateOfCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant dateOfUpdated;


    public Instant getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(Instant dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public Instant getDateOfUpdated() {
        return dateOfUpdated;
    }

    public void setDateOfUpdated(Instant dateOfUpdated) {
        this.dateOfUpdated = dateOfUpdated;
    }
}
