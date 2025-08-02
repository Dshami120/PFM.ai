package main;

import ui.ConsoleUI;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

// author - daniyal shami - group 6

public class Main {

    private static final String USERS_FILE = "data/users.txt";
    private static String currentUser;
    private static String currentRole;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nFinance Management System");
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> signUp(scanner);
                case 2 -> {
                    if (logIn(scanner)) {
                        new ConsoleUI().start(currentUser, currentRole);
                    }
                }
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void signUp(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = hashPassword(scanner.nextLine());
        System.out.print("Enter role (admin/user): ");
        String role = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password + "," + role + "\n");
            System.out.println("User registered successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to users file.");
        }
    }

    private static boolean logIn(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = hashPassword(scanner.nextLine());

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    currentUser = username;
                    currentRole = parts[2];
                    System.out.println("Login successful! Role: " + currentRole);
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file.");
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password");
        }
    }
}
