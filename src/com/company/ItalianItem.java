package com.company;

public class ItalianItem extends Item {
    final String style = "Ordinario";

    public ItalianItem(String type, double price, String color, String print, String origin) {
        super(type, price, color, print, origin);
        this.setOrigin("Italy");
    }
}