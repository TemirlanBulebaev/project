package com.example.project.entities;


import javax.persistence.*;

@Entity
@Table(name = "ROASTED_COFFEE")
public class RoastedCoffee{
    @Id
    @Column(name = "ITEM_ID")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")

    private String name;

    @Column(name = "WEIGHT")
    private Long weight;

    public RoastedCoffee() {
    }

    public RoastedCoffee(String name, Long weight) {
        this.name = name;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
