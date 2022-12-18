package com.example.project.dto;

import com.example.project.entities.Item;

public class ItemDtoFromUnit {

    private Long id;
    private String name;
    private String description;
    private Integer weight;
    private Long price;

    public ItemDtoFromUnit() {
    }

    public static ItemDtoFromUnit fromUnit (Item item) {
        ItemDtoFromUnit itemDtoFromUnit = new ItemDtoFromUnit();
        itemDtoFromUnit.setId(item.getId());
        itemDtoFromUnit.setName(item.getName());
        itemDtoFromUnit.setDescription(item.getDescription());
        itemDtoFromUnit.setWeight(item.getWeight());
        itemDtoFromUnit.setPrice(item.getPrice());
        return itemDtoFromUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
