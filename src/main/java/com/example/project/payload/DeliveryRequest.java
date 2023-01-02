package com.example.project.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DeliveryRequest {

    private String address;

    private String comment;
    private Boolean isPayment;

    public DeliveryRequest() {
    }

    public DeliveryRequest(String address, String comment, Boolean isPayment) {
        this.address = address;
        this.comment = comment;
        this.isPayment = isPayment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getisPayment() {
        return isPayment;
    }

    public void setisPayment(Boolean payment) {
        isPayment = payment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
