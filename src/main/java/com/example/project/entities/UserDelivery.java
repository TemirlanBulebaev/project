package com.example.project.entities;

import com.example.project.entities.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_DELIVERY")
public class UserDelivery extends DateAudit {

    @Id
    @Column(name = "DELIVERY_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_seq")
    @SequenceGenerator(name = "delivery_seq", allocationSize = 1)
    private Long id;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "IS_PAYMENT")
    private Boolean isPayment;// Оплачено

    public UserDelivery() {
        super();
    }

    public UserDelivery(Long id, String address, Boolean isPayment) {
        this.id = id;
        this.address = address;
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
}
