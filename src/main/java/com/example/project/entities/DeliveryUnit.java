package com.example.project.entities;

import com.example.project.entities.audit.DateAudit;

import javax.persistence.*;

@Entity
@Table(name = "DELIVERY_UNIT")
public class DeliveryUnit extends DateAudit {

    @Id
    @Column(name = "DELIVERY_UNIT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_unit_seq")
    @SequenceGenerator(name = "delivery_unit_seq", allocationSize = 1)
    private Long id;


    @Column(name = "AMOUNT_ITEMS")
    private String amountItems;

    @OneToOne
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    private Item item;

    @Column(name = "USER_DELIVERY_ID")
    private Long deliveryId;

    public DeliveryUnit() {
        super();
    }

    public DeliveryUnit(Long id, String amountItems, Item item, Long deliveryId) {
        this.id = id;
        this.amountItems = amountItems;
        this.item = item;
        this.deliveryId = deliveryId;
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

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }
}
