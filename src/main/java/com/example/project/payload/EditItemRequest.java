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

    private Long price;
    @NotNull(message = "Whether the Item will be active or not")
    //@ApiModelProperty(value = "Указывает будет ли Item активным", required = true,
    //dataType = "boolean", allowableValues = "true, false")
    private Boolean active;

    public EditItemRequest() {
    }

    public EditItemRequest(String name, String description, Boolean active) {
        this.name = name;
        this.description = description;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "EditItemRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", active=" + active +
                '}';
    }
}

