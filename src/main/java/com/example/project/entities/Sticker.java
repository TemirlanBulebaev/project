package com.example.project.entities;

import javax.persistence.*;

@Entity
@Table(name = "STICKER")
public class Sticker {

    @Id
    @Column(name = "STICKER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sticker_seq")
    @SequenceGenerator(name = "sticker_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    @Enumerated(value = EnumType.STRING)
    private StickerType name;
    @Column(name = "AMOUNT")
    private Long amount;

    public Sticker() {
    }

    public Sticker(Long id, StickerType name, Long amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StickerType getName() {
        return name;
    }

    public void setName(StickerType name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
