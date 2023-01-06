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

    @Column(name = "DELIVERY_ID")
    private Long deliveryID;

    public DeliveryUnit() {
        super();
    }

    public DeliveryUnit(String amountItems, Item item, Long deliveryID) {
        this.amountItems = amountItems;
        this.item = item;
        this.deliveryID = deliveryID;
    }

    public Long getId() {
        return id;
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

    public Long getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(Long deliveryID) {
        this.deliveryID = deliveryID;
    }
}
