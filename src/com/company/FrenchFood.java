package com.company;

public class FrenchFood extends Food {
    final String taste = "Gracieux";

    public FrenchFood(String type, double price, String kitchen) {
        super(type, price, kitchen);
        this.setKitchen("French");
    }
}