package com.example.project.entities;

import com.example.project.entities.audit.DateAudit;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_DELIVERY")
public class UserDelivery extends DateAudit {

    @Id
    @Column(name = "USER_DELIVERY_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_delivery_seq")
    @SequenceGenerator(name = "user_delivery_seq", allocationSize = 1)
    private Long id;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "COMMENT", columnDefinition = "text")
    private String comment;

    @Column(name = "IS_PAYMENT")
    private Boolean isPayment;// Оплачено


    public UserDelivery() {
        super();
    }

    public UserDelivery(Long id, String address, String comment, Boolean isPayment) {
        this.id = id;
        this.address = address;
        this.comment = comment;
        this.isPayment = isPayment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getPayment() {
        return isPayment;
    }

    public void setPayment(Boolean payment) {
        isPayment = payment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
