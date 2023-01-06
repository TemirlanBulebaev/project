package com.example.project.entities;

import com.example.project.entities.audit.DateAudit;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_DELIVERY")
public class UserDelivery extends DateAudit {

    @Id
    @Column(name = "DELIVERY_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_delivery_seq")
    @SequenceGenerator(name = "user_delivery_seq", allocationSize = 1)
    private Long id;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "COMMENT", columnDefinition = "text")
    private String comment;

    @Column(name = "IS_PAYMENT")
    private Boolean isPayment;// Оплачено

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "DELIVERY_ID")
    private Set<DeliveryUnit> inventoryUnit = new HashSet<>();

    @Column(name = "USER_INVENTORY_ID")
    private Long userInventoryID;


    public UserDelivery() {
        super();
    }

    public UserDelivery(String address,
                        String comment,
                        Boolean isPayment,
                        Set<DeliveryUnit> inventoryUnit,
                        Long userInventoryID) {
        this.address = address;
        this.comment = comment;
        this.isPayment = isPayment;
        this.inventoryUnit = inventoryUnit;
        this.userInventoryID = userInventoryID;
    }

    public Long getId() {
        return id;
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

    public Set<DeliveryUnit> getInventoryUnit() {
        return inventoryUnit;
    }

    public void setInventoryUnit(Set<DeliveryUnit> inventoryUnit) {
        this.inventoryUnit = inventoryUnit;
    }

    public Long getUserInventoryID() {
        return userInventoryID;
    }

    public void setUserInventoryID(Long userInventoryID) {
        this.userInventoryID = userInventoryID;
    }
}
