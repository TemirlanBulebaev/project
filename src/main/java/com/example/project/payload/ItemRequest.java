package com.example.project.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@ApiModel(value = "Item Request", description = "Item Request")
public class ItemRequest {

    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    //@ApiModelProperty(value = "Название Item", required = true, allowableValues = "NonEmpty String")
    private String name;

    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    private String coffeeName;

    @NotBlank(message = "Description cannot be blank")
    //@ApiModelProperty(value = "Описание Item", required = true, allowableValues = "NonEmpty String")
    private String description;

    @NotNull(message = "Description cannot be blank")
    private Integer weight;//g

    @NotNull(message = "One price must be specified")
    //@ApiModelProperty(value = "Цены во внутренней валюте для Item", required = true, allowableValues = "NonEmpty String")
    private Long price;

    @NotNull
    private Long amount;

    public ItemRequest() {
    }

    public ItemRequest(String name, String coffeeName, String description, Integer weight, Long price, Long amount) {
        this.name = name;
        this.coffeeName = coffeeName;
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

