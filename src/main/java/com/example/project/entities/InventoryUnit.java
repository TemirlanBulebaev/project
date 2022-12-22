package com.example.project.entities;

import com.example.project.entities.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "INVENTORY_UNIT")
public class InventoryUnit extends DateAudit {

    @Id
    @Column(name = "UNIT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq")
    @SequenceGenerator(name = "unit_seq", allocationSize = 1)
    private Long id;

    @Column(name = "AMOUNT_ITEMS")
    private String amountItems;

    @OneToOne
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "INVENTORY_ID", insertable = false)
    private UserInventory userInventory;

    @Column(name = "IS_ORDERED")
    private Boolean isOrdered;//Заказано

    @Column(name = "DELIVERY_ID")
    private Long deliveryId;


    public InventoryUnit() {
        super();
    }

    public InventoryUnit(Long id,
                         String amountItems,
                         Item item,
                         UserInventory userInventory,
                         Long deliveryId) {
        this.id = id;
        this.amountItems = amountItems;
        this.item = item;
        this.userInventory = userInventory;
        this.isOrdered = false;
        this.deliveryId = deliveryId;
    }

    public InventoryUnit(Long id){
        this.isOrdered = true;
        this.deliveryId = id;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public UserInventory getUserInventory() {
        return userInventory;
    }

    public void setUserInventory(UserInventory userInventory) {
        this.userInventory = userInventory;
    }

    public Boolean getOrdered() {
        return isOrdered;
    }

    public void setOrdered(Boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }
}
