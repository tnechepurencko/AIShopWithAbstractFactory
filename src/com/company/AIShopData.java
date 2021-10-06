package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AIShopData {
    Scanner scanner = new Scanner(System.in);
    private List<Account> admins = new ArrayList<>();
    private List<Account> users = new ArrayList<>();
    AIShop aiShop;

    public AIShopData(AIShop aiShop) {
        this.aiShop = aiShop;
    }

    public void initialise() {
        admins.add(new Admin("Admin", "00000000"));
        admins.add(new Admin("DeputyAdmin", "11111111"));
    }

    public void Registration() {
        boolean end = false;
        while (!end) {
            System.out.println("Enter your login");
            String login = scanner.nextLine();

            if (!containsLogin(users, login)) {
                System.out.println("Enter your password");
                String password = scanner.nextLine();

                users.add(new User(login, password));
                System.out.println("You are registered");
                end = true;
            } else {
                System.out.println("This login already exists");
            }
        }
    }

    public void LogIn() {
        boolean end = false;
        int option;
        while (!end) {
            System.out.println("Choose type of your account: 1-Admin, 2-Customer");
            option = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter your login");
            String login = scanner.nextLine();

            System.out.println("Enter your password");
            String password = scanner.nextLine();

            switch (option) {
                case 1 -> {
                    if (containsLogin(admins, login)) {
                        if (findLogin(admins, login).getPassword().equals(password)) {
                            ((Admin) findLogin(admins, login)).productsManagement(aiShop);
                            end = true;
                        } else System.out.println("Your password is incorrect");
                    } else {
                        System.out.println("This login does not exist");
                    }
                }

                case 2 -> {
                    if (containsLogin(users, login)) {
                        if (findLogin(users, login).getPassword().equals(password)) {
                            User user = (User)findLogin(users, login);
                            user.orderAssembly(aiShop);
                            end = true;
                        } else System.out.println("Your password is incorrect");
                    } else {
                        System.out.println("This login does not exist");
                    }
                }

                default -> {
                    System.out.println("We do not have such option");
                }
            }


        }
    }

    public boolean containsLogin(List<Account> accounts, String login) {
        for (Account account : accounts) {
            if (account.getLogin().equals(login))
                return true;
        }
        return false;
    }

    public Account findLogin(List<Account> accounts, String login) {
        for (Account account : accounts) {
            if (account.getLogin().equals(login))
                return account;
        }
        return null;
    }
}
