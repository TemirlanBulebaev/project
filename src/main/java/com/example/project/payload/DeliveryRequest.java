package com.example.project.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DeliveryRequest {

    private String address;
    private Boolean isPayment;

    public DeliveryRequest() {
    }

    public DeliveryRequest(String address, Boolean isPayment) {
        this.address = address;
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

    @Override
    public String toString() {
        return "DeliveryRequest{" +
                "address='" + address + '\'' +
                ", isPayment=" + isPayment +
                '}';
    }
}
