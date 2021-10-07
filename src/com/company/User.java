package com.company;

import java.util.*;

public class User extends Account {
    Scanner in = new Scanner(System.in);

    private final List<Product> cart = new ArrayList<>();// корзина

    private final Set<String> searchHistory = new HashSet<>();

    public User(String login, String password) {
        super(login, password);
    }

    public void orderAssembly(AIShop aiShop) {
        boolean over = false;
        String[] input;
        int id;
        int option;
        int decision;
        while (!over) {
            System.out.println("Choose an option: 1-search the product, 2-add the product to the cart " +
                    "3-remove an item from the cart, 4-Show the cart, 5-Clear the cart, 6-finish order assembly");

            input = in.nextLine().split(" ");
            option = -1;

            if (input.length == 1 && input[0].length() == 1 && 48 < input[0].charAt(0) && input[0].charAt(0) < 57) {
                option = Integer.parseInt(input[0]);
            }

            switch (option) {
                case 1 -> {
                    System.out.println("Enter keywords of the product, please (id / kitchen / type " +
                            "/ color / print / origin):");
                    String keywordsLine = in.nextLine();

                    String[] keywords = keywordsLine.split(" ");

                    List<Product> suggestion = aiShop.getItemsByKey(keywords[0]);
                    for (int i = 1; i < keywords.length; i++) {
                        suggestion = aiShop.getItemsByKey(suggestion, keywords[i]);
                    }

                    if (suggestion.size() == 0) {
                        System.out.println("Sorry, there are no products matching your description");
                    } else {
                        System.out.println("Search results:");
                        for (Product product : suggestion) {
                            product.printProduct();
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Enter the id of the product, please");

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

                    Product product = aiShop.findProductByID(id);
                    if (product != null) {
                        this.addToCart(product);
                        System.out.println("The product has been added");
                    }
                }
                case 3 -> {
                    System.out.println("Enter the id of the product, please:");

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

                    this.removeFromCart(id);
                }
                case 4 -> {
                    if (this.cart.size() == 0) {
                        System.out.println("Your cart is empty");
                    } else {
                        System.out.println("Your cart:");
                        for (Product product : this.cart) {
                            product.printProduct();
                        }
                    }
                }
                case 5 -> {
                    this.clearCart();
                    System.out.println("The cart is empty now");
                }
                case 6 -> {
                    List<Product> recs = aiShop.getRecommendations(this);
                    if (recs.size() != 0) {
                        System.out.println("You may also like");
                        for (Product product : recs) {
                            product.printProduct();
                        }

                        System.out.println("Continue shopping? 1-yes, 2-no");

                        input = in.nextLine().split(" ");
                        decision = -1;

                        if (input.length == 1 && input[0].length() == 1 && 48 < input[0].charAt(0) && input[0].charAt(0) < 57) {
                            decision = Integer.parseInt(input[0]);
                        }

                        if (decision == 1) {
                            continue;
                        } else if (decision != 2) {
                            System.out.println("this option does not exist: try again");
                            continue;
                        }
                    }

                    System.out.println("Order assembly is finished");
                    aiShop.payment(this);
                    this.clearCart();

                    over = true;
                }
                default -> System.out.println("We have no such option. Try again");
            }
        }
    }

    /**
     * add the product to the card
     *
     * @param product : the product
     */
    public void addToCart(Product product) {
        searchHistory.addAll(product.getKeywords());
        cart.add(product);
    }

    /**
     * find & remove the product from the card
     *
     * @param id : id of the product
     */
    public void removeFromCart(int id) {
        int ind = -1;
        for (int i = 0; i < cart.size(); i++) {
            if (this.cart.get(i).getId() == id) {
                ind = i;
            }
        }

        if (ind != -1) {
            cart.remove(ind);
            System.out.println("The product is removed successfully");
        } else {
            System.out.println("This product does not exist");
        }
    }

    public Set<String> getSearchHistory() {
        return this.searchHistory;
    }

    public double calculateTotalPrice() {
        double sum = 0;

        for (Product product : cart) {
            sum += product.getPrice();
        }

        return sum;
    }

    public void clearCart() {
        cart.clear();
    }

}
