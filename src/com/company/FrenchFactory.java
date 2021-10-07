package com.company;

import java.util.Scanner;

public class FrenchFactory implements AbstractFactory {
    Scanner scanner = new Scanner(System.in);


    @Override
    public Item createClothes() {
        System.out.println("Enter type of cloth");
        String type = scanner.nextLine();

        String[] input;
        double price;
        String priceStr;

        while (true) {
            System.out.println("Enter price of cloth");
            input = scanner.nextLine().split(" ");
            priceStr = input[0];

            boolean isNumber = true;
            if (input.length == 1) {
                input = input[0].split("\\.");
                if (input.length < 3) {
                    for (String s : input) {
                        for (int i = 0; i < s.length(); i++) {
                            if (48 > s.charAt(i) || s.charAt(i) > 57) {
                                isNumber = false;
                                break;
                            }
                        }
                    }
                }
            }

            if (!isNumber) {
                System.out.println("The format of price is wrong. Try Again");
            } else {
                break;
            }
        }

        price = Double.parseDouble(priceStr);

        System.out.println("Enter color of cloth");
        String color = scanner.nextLine();

        System.out.println("Enter print of cloth");
        String print = scanner.nextLine();

        return new FrenchItem(type, price, color, print, "France");
    }


    @Override
    public Food createFood() {
        System.out.println("Enter type of food");
        String type = scanner.nextLine();

        String[] input;
        double price;
        String priceStr;

        while (true) {
            System.out.println("Enter price of food");
            input = scanner.nextLine().split(" ");
            priceStr = input[0];

            boolean isNumber = true;
            if (input.length == 1) {
                input = input[0].split("\\.");
                if (input.length < 3) {
                    for (String s : input) {
                        for (int i = 0; i < s.length(); i++) {
                            if (48 > s.charAt(i) || s.charAt(i) > 57) {
                                isNumber = false;
                                break;
                            }
                        }
                    }
                }
            }

            if (!isNumber) {
                System.out.println("The format of price is wrong. Try Again");
            } else {
                break;
            }
        }

        price = Double.parseDouble(priceStr);

        return new FrenchFood(type, price, "French");
    }
}