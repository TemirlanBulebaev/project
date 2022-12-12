package com.example.project.payload;

import com.example.project.entities.PackageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditPackageRequest {
    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    private PackageType name;

    @NotNull(message = "Description cannot be blank")
    private Long amount;

    public EditPackageRequest() {
    }

    public EditPackageRequest(PackageType name, Long amount) {
        this.name = name;
        this.amount = amount;
    }

    public PackageType getName() {
        return name;
    }

    public void setName(PackageType name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
