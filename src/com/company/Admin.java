package com.company;

import java.util.Scanner;

public class Admin extends Account {
    Scanner in = new Scanner(System.in);

    public Admin(String login, String password) {
        super(login, password);
    }

    public void productsManagement(AIShop aiShop) {
        System.out.println("Welcome to the products management!!!!!!!!!!!!!");
        boolean over = false;
        String[] input;
        int option;
        int kind;
        int factory;
        int id;

        while (!over) {
            System.out.println("Choose an option: 1-add the product, " +
                    "2-remove the product, 3-get products, 4-finish work");

            input = in.nextLine().split(" ");
            option = -1;

            if (input.length == 1 && input[0].length() == 1 && 48 < input[0].charAt(0) && input[0].charAt(0) < 57) {
                option = Integer.parseInt(input[0]);
            }

            switch (option) {
                case 1 -> {
                    Product product;

                    System.out.println("Choose a kind of the product: 1-food, 2-item");

                    input = in.nextLine().split(" ");
                    kind = -1;

                    if (input.length == 1 && input[0].length() == 1 && 48 < input[0].charAt(0) && input[0].charAt(0) < 57) {
                        kind = Integer.parseInt(input[0]);
                    }

                    System.out.println("Choose a factory: 1-Italian factory, 2-French factory");

                    input = in.nextLine().split(" ");
                    factory = -1;

                    if (input.length == 1 && input[0].length() == 1 && 48 < input[0].charAt(0) && input[0].charAt(0) < 57) {
                        factory = Integer.parseInt(input[0]);
                    }

                    switch (kind) {
                        case 1:
                            switch (factory) {
                                case 1 -> {
                                    aiShop.setFactory(new ItalianFactory());
                                    product = aiShop.getFactory().createFood();
                                }
                                case 2 -> {
                                    aiShop.setFactory(new FrenchFactory());
                                    product = aiShop.getFactory().createFood();
                                }
                                default -> {
                                    System.out.println("We have no such option. Try again");
                                    continue;
                                }
                            }
                            break;
                        case 2:
                            switch (factory) {
                                case 1 -> {
                                    aiShop.setFactory(new ItalianFactory());
                                    product = aiShop.getFactory().createClothes();
                                }
                                case 2 -> {
                                    aiShop.setFactory(new FrenchFactory());
                                    product = aiShop.getFactory().createClothes();
                                }
                                default -> {
                                    System.out.println("We have no such option. Try again");
                                    continue;
                                }
                            }
                            break;
                        default:
                            System.out.println("We have no such option. Try again");
                            continue;
                    }

                    aiShop.addProduct(product);
                }
                case 2 -> {
                    System.out.println("Enter the ID of the product, please:");

                    input = in.nextLine().split(" ");
                    id = -1;

                    boolean isNumber = true;
                    if (input.length == 1) {
                        for (int i = 0; i < input[0].length(); i++) {
                            if (48 > input[0].charAt(i) || input[0].charAt(i) > 57) {
                                isNumber = false;
                                break;
                            }
                        }
                    }

                    if (isNumber) {
                        id = Integer.parseInt(input[0]);
                    }

                    aiShop.removeProduct(id);
                    System.out.println("the product is removed");
                }
                case 3 -> aiShop.printProducts();
                case 4 -> {
                    System.out.println("Work is finished. Thank you!");
                    over = true;
                }
                default -> System.out.println("We have no such option. Try again");
            }
        }
    }

}
