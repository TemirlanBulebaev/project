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
    private Long amountItems;

    @OneToOne
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "INVENTORY_ID", insertable = false)
    private UserInventory userInventory;

    public InventoryUnit() {
        super();
    }

    public InventoryUnit(Long id, Long amountItems, Item item, UserInventory userInventory) {
        this.id = id;
        this.amountItems = amountItems;
        this.item = item;
        this.userInventory = userInventory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmountItems() {
        return amountItems;
    }

    public void setAmountItems(Long amountItems) {
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

}
