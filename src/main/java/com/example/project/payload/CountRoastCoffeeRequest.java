package com.example.project.payload;

public class CountRoastCoffeeRequest {

    private Long id;
    private Long amount;//загрузка в батчах

    public CountRoastCoffeeRequest() {
    }

    public CountRoastCoffeeRequest(Long id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
