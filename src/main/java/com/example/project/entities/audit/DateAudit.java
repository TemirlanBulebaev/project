package com.example.project.entities.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"create", "update"}, allowGetters = true)
public abstract class DateAudit implements Serializable {

    @CreatedDate
    @Column(name = "date_of_created")
    private Instant dateOfCreated;

    @LastModifiedDate
    @Column(name = "date_of_updated")
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
