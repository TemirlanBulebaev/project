package com.example.project.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DeliveryRequest {
    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    private String address;
    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
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

    public Boolean getPayment() {
        return isPayment;
    }

    public void setPayment(Boolean payment) {
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
