package com.example.project.entities;

import com.example.project.entities.audit.DateAudit;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item extends DateAudit {

    @Id
    @Column(name = "ITEM_ID")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "COFFEE_NAME", nullable = false)
    private String coffeeName;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "PACKAGE")
    @Enumerated(value = EnumType.STRING)
    private PackageType packageType;

    @Column(name = "STICKER_TYPE")
    @Enumerated(value = EnumType.STRING)
    private StickerType stickerType;
    @Column(name = "PRICE")
    private Long price;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;


    public Item() {
        super();
    }

    public Item(Long id,
                String name,
                String coffeeName,
                String description,
                Integer weight,
                Long price,
                Long amount,
                Boolean active) {
        this.id = id;
        this.name = name;
        this.coffeeName = coffeeName;
        this.description = description;
        this.weight = weight;
        setPackageType(weight);
        setStickerType(weight);
        this.price = price;
        this.amount = amount;
        this.active = active;
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

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
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

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer weight) {
        if (weight == 100) {
            this.packageType = PackageType.CRAFT_100;
        } if (weight == 250) {
            this.packageType = PackageType.BLACK_250;
        } if (weight == 1000) {
            this.packageType = PackageType.BLACK_1000;
        }
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

    public StickerType getStickerType() {
        return stickerType;
    }

    public void setStickerType(Integer weight) { //TODO Сделать через switch
        if (weight == 100) {
            this.stickerType = StickerType.STICKER_100;
        } if (weight == 250) {
            this.stickerType = StickerType.STICKER_250;
        } if (weight == 1000) {
            this.stickerType = StickerType.STICKER_1000;
        }
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
