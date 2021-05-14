package com.sikdev.diplomankas.Objects;

import java.util.List;
import java.util.stream.Collector;

public class Product {

    int id_;
    String name;
    int price;
    String brand_country;
    String brand_name;
    String name_image;
    // Подробности
    int quantity;
    String description;
    List<String> listImage;
    List<Characteristics> listCharacteristics;

    public Product(int id_, String name, int price, String brand_country, String brand_name, String name_image, int quantity, String description, List<String> listImage, List<Characteristics> listCharacteristics) {
        this.id_ = id_;
        this.name = name;
        this.price = price;
        this.brand_country = brand_country;
        this.brand_name = brand_name;
        this.name_image = name_image;
        this.quantity = quantity;
        this.description = description;
        this.listImage = listImage;
        this.listCharacteristics = listCharacteristics;
    }

    public Product(int id_, String name, int price, String brand_country, String brand_name, String name_image) {
        this.id_ = id_;
        this.name = name;
        this.price = price;
        this.brand_country = brand_country;
        this.brand_name = brand_name;
        this.name_image = name_image;
    }

    public String getName_image() {
        return name_image;
    }

    public int getId_() {
        return id_;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getBrand_country() {
        return brand_country;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getListImage() {
        return listImage;
    }

    public List<Characteristics> getListCharacteristics() {
        return listCharacteristics;
    }
}
