package com.example.romhub_ronanlina.ligtasbaonbeta;

public class Restaurants {

    private String name;
    private Double average_price;
    private String barangay;

    public Restaurants(String name, Double average_price, String barangay) {
        this.name = name;
        this.average_price = average_price;
        this.barangay = barangay;
    }

    public Restaurants() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAverage_price() {
        return average_price;
    }

    public void setAverage_price(Double average_price) {
        this.average_price = average_price;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }
}
