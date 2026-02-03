package com.scrape.dmart;


public class ProductData {

    private String category;
    private String name;
    private String imgUrl;
    private String brand;
    private String description;
    // private double priceMRP;
    // private double weight;


    public ProductData(String category, String name, String imgUrl, String brand, String description) {
        this.category = category;
        this.name = name;
        this.imgUrl = imgUrl;
        this.brand = brand;
        this.description = description;

    }

    public String toString() {
        return String.format("Category: %s | Name: %s |URL %s |Brand %s", category, name, imgUrl, brand);
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }
}

