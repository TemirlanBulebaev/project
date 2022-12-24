package com.example.project.payload;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GreenCoffeeRequest {
    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    private String name;

    @NotNull(message = "Description cannot be blank")
    private Long weight;//g

    @NotNull(message = "Description cannot be blank")
    private Integer waterLoss;// %

    @NotNull(message = "Description cannot be blank")
    private Integer density;// g/L

    @NotNull(message = "Description cannot be blank")
    private Integer humidity; //%

    public GreenCoffeeRequest() {
    }

    public GreenCoffeeRequest(String name, Long weight, Integer waterLoss, Integer density, Integer humidity) {
        this.name = name;
        this.weight = weight;
        this.waterLoss = waterLoss;
        this.density = density;
        this.humidity = humidity;
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
        return "GreenCoffeeRequest{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", waterLoss=" + waterLoss +
                ", density=" + density +
                ", humidity=" + humidity +
                '}';
    }
}
