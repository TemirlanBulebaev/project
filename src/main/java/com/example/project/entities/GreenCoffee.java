package com.example.project.entities;

import com.example.project.entities.audit.DateAudit;

import javax.persistence.*;

@Entity
@Table(name = "GREEN_COFFEE")
public class GreenCoffee extends DateAudit {

    @Id
    @Column(name = "GREEN_COFFEE_ID")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "green_coffee_seq")
    @SequenceGenerator(name = "green_coffee_seq", allocationSize = 1)
    private Long id;

    @Column(name = "GREEN_COFFEE_NAME", nullable = false)
    private String name;

    @Column(name = "GREEN_COFFEE_WEIGHT")
    private Long weight;

    @Column(name = "WATER_LOSS")
    private Integer waterLoss;

    @Column(name = "DENSITY")
    private Integer density;

    @Column(name = "HUMIDITY")
    private Integer humidity;

    public GreenCoffee() {
    }

    public GreenCoffee(String name, Long weight, Integer waterLoss, Integer density, Integer humidity) {
        this.name = name;
        this.weight = weight;
        this.waterLoss = waterLoss;
        this.density = density;
        this.humidity = humidity;
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

    public Integer getWaterLoss() {
        return waterLoss;
    }

    public void setWaterLoss(Integer waterLoss) {
        this.waterLoss = waterLoss;
    }

    public Integer getDensity() {
        return density;
    }

    public void setDensity(Integer density) {
        this.density = density;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}
