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
    private Long weight; //g

    @Column(name = "WATER_LOSS")//потеря влаги
    private Integer waterLoss; //%

    @Column(name = "DENSITY") //Плотность
    private Integer density;

    @Column(name = "HUMIDITY") // Влажность
    private Integer humidity;

    public GreenCoffee() {
    }

    public GreenCoffee(Long id, String name, Long weight, Integer waterLoss, Integer density, Integer humidity) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.waterLoss = waterLoss;
        this.density = density;
        this.humidity = humidity;
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

    @Override
    public String toString() {
        return "GreenCoffee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", waterLoss=" + waterLoss +
                ", density=" + density +
                ", humidity=" + humidity +
                '}';
    }
}
