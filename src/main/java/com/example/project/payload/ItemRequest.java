package com.example.project.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@ApiModel(value = "Item Request", description = "Item Request")
public class ItemRequest {

    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    //@ApiModelProperty(value = "Название Item", required = true, allowableValues = "NonEmpty String")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    //@ApiModelProperty(value = "Описание Item", required = true, allowableValues = "NonEmpty String")
    private String description;

    @NotNull(message = "Description cannot be blank")
    private Integer weight;//g

    @NotNull(message = "One price must be specified")
    //@ApiModelProperty(value = "Цены во внутренней валюте для Item", required = true, allowableValues = "NonEmpty String")
    private Long price;

    public ItemRequest() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "ItemRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}

