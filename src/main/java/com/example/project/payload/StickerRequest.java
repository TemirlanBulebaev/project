package com.example.project.payload;

import com.example.project.entities.PackageType;
import com.example.project.entities.StickerType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StickerRequest {
    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    private StickerType name;

    @NotNull(message = "Description cannot be blank")
    private Long amount;

    public StickerRequest() {
    }

    public StickerRequest(StickerType name, Long amount) {
        this.name = name;
        this.amount = amount;
    }

    public StickerType getName() {
        return name;
    }

    public void setName(StickerType name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
