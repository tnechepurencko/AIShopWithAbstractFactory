package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Scanner;

class Random {
    static com.company.Random random = new com.company.Random();

    public static int getRandInt(int limit) {
        return random.getRandInt(limit);
    }
}

class Account {
    private String login;
    private String password;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Admin extends com.company.Account {
    Scanner in = new Scanner(System.in);

    public Admin(String login, String password) {
        super(login, password);
    }

    public void orderAssembly(com.company.User user) {
        boolean over = false;

        while (!over) {
            System.out.println("Choose an option: 1-search for the item, " +
                    "2-remove an item from the cart, 3-finish order assembly\n");
            int option = in.nextInt();

            switch (option) {
                case 1: {
                    System.out.println("Enter keywords for an item, please:\n");
                    String keywords = in.nextLine();
                    System.out.println("Thank you!\n");


                } case 2: {

                } case 3: {
                    System.out.println("Thank you!\n");
                    over = true;
                } default: {
                    System.out.println("We have no such option. Try again\n");
                    break;
                }
            }
        }
//        if (user.calculateTotalPrice() > 0){System.out.println("The total price: " + user.calculateTotalPrice());
//
//            while (!over) {
//                System.out.println("Choose the way of payment: ");
//                System.out.println("1-Card, 2-Cash");
//                int wayOfPayment = in.nextInt();
//                switch (wayOfPayment) {
//                    case 1: {
//                        System.out.println("Waiting for confirmation ...");
//                        System.out.println("Payment confirmed");
//                        over = true;
//                        break;
//                    }
//                    case 2: {
//                        System.out.println("You will pay for your order upon receiving");
//                        over = true;
//                        break;
//                    }
//                    default: {
//                        System.out.println("We have no such option. Try again");
//                        break;
//                    }
//                }
//            }
//            System.out.println("Thank you for choosing our shop!");
//        }
    }

}

class User extends com.company.Account {

    private List<com.company.Product> cart = new ArrayList<>();// корзина

    private Set<String> searchHistory = new HashSet<>();

    public User(String login, String password) {
        super(login, password);
    }

    /**
     * add the product to the card
     * @param product : the product
     */
    public void addToCart(com.company.Product product) {
        searchHistory.addAll(product.getKeywords());
        cart.add(product);
    }

    /**
     * remove the product from the card
     * @param product : the product
     */
    public void removeFromCart(com.company.Product product) {
        cart.remove(product);
    }

    public Set<String> getSearchHistory() {
        return this.searchHistory;
    }

    public float calculateTotalPrice() {
        float sum = 0;

        for (com.company.Product product : cart) {
            sum += product.getPrice();
        }

        return sum;
    }

    public void clearCart() {
        cart.clear();
    }

}

class Product {
    private String type;
    private float price;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    public Product(String type, float price) {
        this.type = type;
        this.price = price;
        this.keywords.add(type);
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public float getPrice() {
        return this.price;
    }

    public String getType() {
        return type;
    }
}

class Food extends com.company.Product {
    private String type;
    private float price;
    private String kitchen;
    private String origin;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    public Food(String type, float price, String kitchen, String origin) {
        super(type, price);
        this.type = type;
        this.price = price;
        this.kitchen = kitchen;
        this.origin = origin;

        this.keywords.add(type);
        this.keywords.add(origin);
        this.keywords.add(kitchen);
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public float getPrice() {
        return this.price;
    }
}

class Item extends com.company.Product {
    private String type;
    private float price;
    private String color;
    private String print;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    // Danechka????
    public Item(String type, float price, String color, String print) {
        super(type, price);
        this.type = type;
        this.price = price;
        this.color = color;
        this.print = print;

        this.keywords.add(type);
        this.keywords.add(color);
        this.keywords.add(print);
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public float getPrice() {
        return this.price;
    }

}

interface AbstractFactory {
    com.company.Item createCloth();

    com.company.Food createFood();
}

class FirstFactory implements com.company.AbstractFactory {
    Scanner scanner = new Scanner(System.in);

    @Override
    public com.company.Item createCloth() { // TODO
        System.out.println("Enter type of cloth");
        String type = scanner.nextLine();

        System.out.println("Enter price of cloth");
        float price = scanner.nextFloat();

        System.out.println("Enter color of cloth");
        String color = scanner.nextLine();

        System.out.println("Enter print of cloth");
        String print = scanner.nextLine();

        return new com.company.Item(type, price, color, print);
    } // TODO

    @Override
    public com.company.Food createFood() {
        return null;
    } // TODO
}

class SecondFactory implements com.company.AbstractFactory {

    @Override
    public com.company.Item createCloth() {
        return null;
    } // TODO

    @Override
    public com.company.Food createFood() {
        return null;
    } // TODO
}

class AIShop {
    Scanner in = new Scanner(System.in);

    private final static int KEYWORDS_SUBSET_SIZE = 5;
    private final static int RECOMMENDATIONS_SIZE = 5;

    private List<com.company.Product> products = new ArrayList<>();

    /**
     * @param keyword : the keyword
     * @return : list of products with this keyword
     */
    public List<com.company.Product> getItemsByKey(String keyword) {
        return products.stream().filter(products -> products.isApplicable(keyword)).collect(Collectors.toList());
    }

    /**
     * gets random keywords from history & puts corresponding products in the list of recommendations
     * @param user : the user
     * @return : the list of recommendations
     */
    public List<com.company.Product> getRecommendations(com.company.User user) {
        Set<String> searchHistory = user.getSearchHistory();
        int fromIndex = com.company.Random.getRandInt(searchHistory.size() - KEYWORDS_SUBSET_SIZE);
        int toIndex = fromIndex + KEYWORDS_SUBSET_SIZE;

        List<String> keywords = new ArrayList<>(searchHistory).subList(fromIndex, toIndex);
        List<com.company.Product> recommendations =
                keywords.stream().map(this::getItemsByKey).flatMap(Collection::stream).collect(Collectors.toList());

        if (recommendations.size() > RECOMMENDATIONS_SIZE) {
            return recommendations.subList(0, RECOMMENDATIONS_SIZE);
        } else {
            return recommendations;
        }
    }

    /**
     * Suggests the way of payment & provides the payment
     * @param user : the user
     */
    public void payment(com.company.User user) {
        if (user.calculateTotalPrice() > 0) {
            System.out.println("The total price: " + user.calculateTotalPrice() + "\n");

            boolean over = false;
            while (!over) {
                System.out.println("Choose the way of payment: 1-Card, 2-Cash\n");
                int wayOfPayment = in.nextInt();

                switch (wayOfPayment) {
                    case 1: {
                        System.out.println("Waiting for confirmation ...\nPayment confirmed");
                        over = true;
                        break;
                    }
                    case 2: {
                        System.out.println("You will pay for your order upon receiving\n");
                        over = true;
                        break;
                    }
                    default: {
                        System.out.println("We have no such option. Try again\n");
                        break;
                    }
                }
            }
            System.out.println("Thank you for choosing our shop!\n");
        } else {
            System.out.println("It is ok, you do not need to pay this time:)\n");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        com.company.AIShop ourAIShop = new com.company.AIShop();
    }
}