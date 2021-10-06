package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Scanner;

class Random {
    static Random random = new Random();

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

class Admin extends Account {
    Scanner in = new Scanner(System.in);

    public Admin(String login, String password) {
        super(login, password);
    }

    public void productsManagement(User user, AIShop aiShop) {
        boolean over = false;

        while (!over) {
            System.out.println("Choose an option: 1-add the product, " +
                    "2-remove the product, 3-get products, 4-finish work");
            int option = in.nextInt();

            switch (option) {
                case 1 -> {
                    Product product;

                    System.out.println("Choose a kind of the product: 1-food, 2-item");
                    int kind = in.nextInt();

                    System.out.println("Choose a factory: 1-Italian factory, 2-French factory");
                    int factory = in.nextInt();

                    switch (kind) {
                        case 1:
                            switch (factory) {
                                case 1 -> {
                                    ItalianFactory italianFactory = new ItalianFactory();
                                    product = italianFactory.createFood();
                                }
                                case 2 -> {
                                    FrenchFactory frenchFactory = new FrenchFactory();
                                    product = frenchFactory.createFood();
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
                                    ItalianFactory italianFactory = new ItalianFactory();
                                    product = italianFactory.createCloth();
                                }
                                case 2 -> {
                                    FrenchFactory frenchFactory = new FrenchFactory();
                                    product = frenchFactory.createCloth();
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
                    System.out.println("Thank you!");
                }
                case 2 -> {
                    System.out.println("Enter the ID of the product, please:");
                    int id = in.nextInt();

                    aiShop.removeProduct(id);
                    System.out.println("Thank you!");
                }
                case 3 -> {
                    aiShop.printProducts();
                }
                case 4 -> {
                    System.out.println("Work is finished. Thank you!");
                    over = true;
                }
                default -> {
                    System.out.println("We have no such option. Try again");
                }
            }
        }
    }

}

class User extends Account {
    Scanner in = new Scanner(System.in);

    private List<Product> cart = new ArrayList<>();// корзина

    private Set<String> searchHistory = new HashSet<>();

    public User(String login, String password) {
        super(login, password);
    }

    public void orderAssembly(User user, AIShop aiShop) {
        boolean over = false;

        while (!over) {
            System.out.println("Choose an option: 1-search the product, 2-add the product to the cart " +
                    "3-remove an item from the cart, 4-Show the cart, 5-Clear the cart, 6-finish order assembly");
            int option = in.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.println("Enter keywords of the product, please (id / kitchen / type " +
                            "/ color / print / origin):");
                    String[] keywords = in.nextLine().split(" ");
                    System.out.println("Thank you!");

                    List<Product> suggestion = aiShop.getItemsByKey(keywords[0]);
                    for (int i = 1; i < keywords.length; i++) {
                        suggestion = aiShop.getItemsByKey(suggestion, keywords[i]);
                    }

                    if (suggestion.size() == 0) {
                        System.out.println("Sorry, there are no products matching your description");
                    } else {
                        System.out.println("Search results:");
                        for (Product product : suggestion) {
                            System.out.println("id: " + product.getId() + "\ttype: " + product.getType() +
                                    "\tprice: " + product.getPrice());
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Enter the id of the product, please");
                    int id = in.nextInt();

                    Product product = aiShop.findProductByID(id);
                    if (product != null) {
                        this.addToCart(product);
                    }
                }
                case 3 -> {
                    System.out.println("Enter the id of the product, please:");
                    int id = in.nextInt();

                    user.removeFromCart(id);
                    System.out.println("Thank you!");
                }
                case 4 -> {
                    System.out.println("Your cart:");
                    for (Product product : this.cart) {
                        System.out.println("id: " + product.getId() + "\ttype: " + product.getType() +
                                "\tprice: " + product.getPrice());
                    }
                }
                case 5 -> {
                    this.clearCart();
                    System.out.println("The cart is empty now");
                }
                case 6 -> {
                    List<Product> recs = aiShop.getRecommendations(this);
                    System.out.println("You may also like");
                    for (Product product : recs) {
                        System.out.println("id: " + product.getId() + "\ttype: " + product.getType() +
                                "\tprice: " + product.getPrice());
                    }

                    System.out.println("Continue shopping? 1-yes, 2-no");
                    int decision = in.nextInt();
                    if (decision == 1) {
                        continue;
                    }

                    System.out.println("Order assembly is finished");
                    aiShop.payment(this);
                    over = true;
                }
                default -> {
                    System.out.println("We have no such option. Try again");
                }
            }
        }
    }

    /**
     * add the product to the card
     * @param product : the product
     */
    public void addToCart(Product product) {
        searchHistory.addAll(product.getKeywords());
        cart.add(product);
    }

    /**
     * find & remove the product from the card
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

class Product {
    protected static int idIterator = 0;

    private int id;
    private String type;
    private double price;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    public Product(String type, double price) {
        this.id = idIterator;
        idIterator++;

        this.type = type;
        this.price = price;

        this.keywords.add(type);
        this.keywords.add(String.valueOf(id));
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public double getPrice() {
        return this.price;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}

class Food extends Product {
    private int id;
    private String type;
    private double price;
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

    public Food(String type, double price, String kitchen) {
        super(type, price);

        this.id = idIterator;
        idIterator++;

        this.type = type;
        this.price = price;
        this.kitchen = kitchen;

        this.keywords.add(type);
        this.keywords.add(kitchen);
        this.keywords.add(String.valueOf(id));
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public int getId() {
        return id;
    }
}

class Item extends Product {
    private int id;
    private String type;
    private double price;
    private String color;
    private String print;
    private String origin;
    private Set<String> keywords = new HashSet<>();

    /**
     * @param userKeyword : the keyword
     * @return : does the keyword belong to this product?
     */
    public boolean isApplicable(String userKeyword) {
        return this.keywords.contains(userKeyword);
    }

    // Danechka????
    public Item(String type, double price, String color, String print, String origin) {
        super(type, price);

        this.id = idIterator;
        idIterator++;

        this.type = type;
        this.price = price;
        this.color = color;
        this.print = print;
        this.origin = origin;

        this.keywords.add(type);
        this.keywords.add(color);
        this.keywords.add(print);
        this.keywords.add(origin);
        this.keywords.add(String.valueOf(id));
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public int getId() {
        return id;
    }
}

interface AbstractFactory {
    Item createCloth();

    Food createFood();
}

class ItalianFactory implements AbstractFactory {
    Scanner scanner = new Scanner(System.in);

    public Item createCloth(String type, double price, String color, String print) {
        return new Item(type, price, color, print,"Italy");
    }

    @Override
    public Item createCloth() {
        System.out.println("Enter type of cloth");
        String type = scanner.nextLine();

        System.out.println("Enter price of cloth");
        double price = scanner.nextDouble();

        System.out.println("Enter color of cloth");
        String color = scanner.nextLine();

        System.out.println("Enter print of cloth");
        String print = scanner.nextLine();

        return new Item(type, price, color, print,"Italy");
    }

    public Food createFood(String type, double price) {
        return new Food(type, price, "Italian");
    }

    @Override
    public Food createFood() {
        System.out.println("Enter type of food");
        String type = scanner.nextLine();

        System.out.println("Enter price of food");
        double price = scanner.nextDouble();

        return new Food(type, price, "Italian");
    }
}

class FrenchFactory implements AbstractFactory {
    Scanner scanner = new Scanner(System.in);

    public Item createCloth(String type, double price, String color, String print) {
        return new Item(type, price, color, print,"France");
    }

    @Override
    public Item createCloth() {
        System.out.println("Enter type of cloth");
        String type = scanner.nextLine();

        System.out.println("Enter price of cloth");
        double price = scanner.nextDouble();

        System.out.println("Enter color of cloth");
        String color = scanner.nextLine();

        System.out.println("Enter print of cloth");
        String print = scanner.nextLine();

        return new Item(type, price, color, print,"France");
    }

    public Food createFood(String type, double price) {
        return new Food(type, price, "French");
    }

    @Override
    public Food createFood() {
        System.out.println("Enter type of food");
        String type = scanner.nextLine();

        System.out.println("Enter price of food");
        double price = scanner.nextDouble();

        return new Food(type, price, "French");
    }
}

class AIShop {
    Scanner in = new Scanner(System.in);

    private final static int KEYWORDS_SUBSET_SIZE = 5;
    private final static int RECOMMENDATIONS_SIZE = 5;

    private List<Product> products = new ArrayList<>();

    public void fillTheListOfProducts() {
        ItalianFactory italianFactory = new ItalianFactory();
        FrenchFactory frenchFactory = new FrenchFactory();

        Product product;
        
        product = italianFactory.createCloth("dress", 6.22, "red", "dinosaurs");
        this.products.add(product);

        product = italianFactory.createFood("croissant", 1.33);
        this.products.add(product);

        product = frenchFactory.createCloth("dress", 3.62, "red", "triangles");
        this.products.add(product);

        product = frenchFactory.createFood("frogs' legs", 84.44);
        this.products.add(product);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(int id) {
        int ind = -1;
        for (int i = 0; i < products.size(); i++) {
            if (this.products.get(i).getId() == id) {
                ind = i;
            }
        }

        if (ind != -1) {
            products.remove(ind);
        } else {
            System.out.println("This product does not exist");
        }
    }

    public void printProducts() {
        System.out.println("Products:");
        for (Product product : this.products) {
            System.out.println("id: " + product.getId() + "\ttype: " + product.getType() +
                    "\tprice: " + product.getPrice());
        }
    }

    /**
     * @param keyword : the keyword
     * @return : list of products with this keyword
     */
    public List<Product> getItemsByKey(String keyword) {
        return products.stream().filter(products -> products.isApplicable(keyword)).collect(Collectors.toList());
    }

    /**
     * @param  productsList : a list of products
     * @param keyword : the keyword
     * @return : list of products with this keyword
     */
    public List<Product> getItemsByKey(List<Product> productsList, String keyword) {
        return productsList.stream().filter(products -> products.isApplicable(keyword)).collect(Collectors.toList());
    }

    public Product findProductByID(int id) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getId() == id) {
                return this.products.get(i);
            }
        }

        System.out.println("The product does not exist");
        return null;
    }

    /**
     * gets random keywords from history & puts corresponding products in the list of recommendations
     * @param user : the user
     * @return : the list of recommendations
     */
    public List<Product> getRecommendations(User user) {
        Set<String> searchHistory = user.getSearchHistory();
        int fromIndex = Random.getRandInt(searchHistory.size() - KEYWORDS_SUBSET_SIZE);
        int toIndex = fromIndex + KEYWORDS_SUBSET_SIZE;

        List<String> keywords = new ArrayList<>(searchHistory).subList(fromIndex, toIndex);
        List<Product> recommendations =
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
    public void payment(User user) {
        if (user.calculateTotalPrice() > 0) {
            System.out.println("The total price: " + user.calculateTotalPrice());

            boolean over = false;
            while (!over) {
                System.out.println("Choose the way of payment: 1-Card, 2-Cash");
                int wayOfPayment = in.nextInt();

                switch (wayOfPayment) {
                    case 1 -> {
                        System.out.println("Waiting for confirmation ...\nPayment confirmed");
                        over = true;
                    }
                    case 2 -> {
                        System.out.println("You will pay for your order upon receiving");
                        over = true;
                    }
                    default -> {
                        System.out.println("We have no such option. Try again");
                    }
                }
            }
            System.out.println("Thank you for choosing our shop!");
        } else {
            System.out.println("It is ok, you do not need to pay this time:)");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        AIShop ourAIShop = new AIShop();

    }
}