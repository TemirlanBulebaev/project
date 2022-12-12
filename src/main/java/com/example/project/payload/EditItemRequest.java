package com.example.project.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditItemRequest {

    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    //@ApiModelProperty(value = "Название Item", required = true, allowableValues = "NonEmpty String")
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    //@ApiModelProperty(value = "Описание Item", required = true, allowableValues = "NonEmpty String")
    private String description;

    @NotNull(message = "Description cannot be blank")
    private Integer weight;//g

    @NotNull(message = "One price must be specified")
    //@ApiModelProperty(value = "Цены во внутренней валюте для Item", required = true, allowableValues = "NonEmpty String")
    private Long price;
    @NotNull(message = "Whether the Item will be active or not")
    //@ApiModelProperty(value = "Указывает будет ли Item активным", required = true,
    //dataType = "boolean", allowableValues = "true, false")
    private Boolean active;

    public EditItemRequest() {
    }

    public EditItemRequest(String name, String description, Integer weight, Long price, Boolean active) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "EditItemRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", active=" + active +
                '}';
    }
}

