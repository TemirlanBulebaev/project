package com.example.project.dto;

import com.example.project.entities.InventoryUnit;

public class InventoryUnitDto {

    private Long id;
    private String amountItems;
    private ItemDtoFromUnit item;

    public InventoryUnitDto() {
    }

    public static InventoryUnitDto fromUser (InventoryUnit inventoryUnit) {
        InventoryUnitDto inventoryUnitDto = new InventoryUnitDto();
            inventoryUnitDto.setAmountItems(inventoryUnit.getAmountItems());
            inventoryUnitDto.setId(inventoryUnit.getId());
            inventoryUnitDto.setItem(ItemDtoFromUnit.fromUnit(inventoryUnit.getItem()));
        return inventoryUnitDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmountItems() {
        return amountItems;
    }

    public void setAmountItems(String amountItems) {
        this.amountItems = amountItems;
    }

    public ItemDtoFromUnit getItem() {
        return item;
    }

    public void setItem(ItemDtoFromUnit item) {
        this.item = item;
    }
}
