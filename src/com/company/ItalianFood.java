package com.company;

public class ItalianFood extends Food {
    String taste = "Aggraziato";

    public ItalianFood(String type, double price, String kitchen) {
        super(type, price, kitchen);
        this.setKitchen("Italian");
    }
}
