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

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    public Item() {
        super();
    }

    public Item(Long id,
                String name,
                String description,
                Long price,
                String author,
                Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.active = active;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                ", active=" + active +
                '}';
    }
}
