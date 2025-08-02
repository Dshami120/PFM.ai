package ui;

import logic.FinanceManager;
import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ConsoleUI {
    private final FinanceManager manager = new FinanceManager();
    private final Scanner scanner = new Scanner(System.in);
    private final Stack<Transaction> undoStack = new Stack<>();

    public void start(String username, String role) {
        System.out.println("Welcome, " + username + " (role: " + role + ")");

        while (true) {
            printMenu(role);
            System.out.print("Select: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": addTransaction(); break;
                case "2": viewTransactions(); break;
                case "3": deleteTransaction(); break;
                case "4": reverseTransaction(); break;
                case "5": viewBalance(); break;
                case "6": setBudget(); break;
                case "7": viewBudgets(); break;
                case "8": addSavingsGoal(); break;
                case "9": viewSavingsGoals(); break;
                case "10": addRecurring(); break;
                case "11": viewRecurring(); break;
                case "12": addInvestment(); break;
                case "13": viewInvestments(); break;
                case "14": System.out.println(manager.generateReport()); break;
                case "15": if ("admin".equals(role)) viewUsers(); break;
                case "16": manager.saveAll(); System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void printMenu(String role) {
        System.out.println("--- Main Menu ---");
        System.out.println("1. Add Transaction");
        System.out.println("2. View Transactions");
        System.out.println("3. Delete Transaction");
        System.out.println("4. Reverse Last Transaction");
        System.out.println("5. View Balance");
        System.out.println("6. Set Budget");
        System.out.println("7. View Budgets");
        System.out.println("8. Add Savings Goal");
        System.out.println("9. View Savings Goals");
        System.out.println("10. Add Recurring Transaction");
        System.out.println("11. View Recurring Transactions");
        System.out.println("12. Add Investment");
        System.out.println("13. View Investments");
        System.out.println("14. View Financial Report");
        if ("admin".equals(role)) System.out.println("15. View Registered Users");
        System.out.println("16. Save and Exit");
    }

    private void addTransaction() {
        System.out.println("Amount:");
        double amt = Double.parseDouble(scanner.nextLine());
        System.out.println("Type (income/expense):");
        String type = scanner.nextLine();
        System.out.println("Category:");
        String category = scanner.nextLine();
        System.out.println("Date:");
        String date = scanner.nextLine();
        System.out.println("Description:");
        String desc = scanner.nextLine();

        Transaction t = new Transaction(amt, category, date, desc, type);
        manager.addTransaction(t);
        undoStack.push(t);
    }

    private void viewTransactions() {
        List<Transaction> list = manager.getTransactions();
        if (list.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private void deleteTransaction() {
        viewTransactions();
        System.out.println("Delete which #: ");
        int index = Integer.parseInt(scanner.nextLine());
        manager.deleteTransaction(index);
    }

    private void reverseTransaction() {
        if (!undoStack.isEmpty()) {
            Transaction t = undoStack.pop();
            manager.getTransactions().remove(t);
            System.out.println("Last transaction reversed.");
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    private void viewBalance() {
        System.out.println("Current Balance: $" + manager.getCurrentBalance());
    }

    private void setBudget() {
        System.out.println("Category:");
        String cat = scanner.nextLine();
        System.out.println("Limit:");
        double lim = Double.parseDouble(scanner.nextLine());
        manager.setBudget(cat, lim);
    }

    private void viewBudgets() {
        for (Budget b : manager.getBudgets().values()) {
            System.out.println(b);
        }
    }

    private void addSavingsGoal() {
        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("Target:");
        double target = Double.parseDouble(scanner.nextLine());
        System.out.println("Saved:");
        double saved = Double.parseDouble(scanner.nextLine());
        manager.addSavingsGoal(new SavingsGoal(name, target, saved));
    }

    private void viewSavingsGoals() {
        for (SavingsGoal goal : manager.getGoals()) {
            System.out.println(goal);
        }
    }

    private void addRecurring() {
        System.out.println("Category:");
        String cat = scanner.nextLine();
        System.out.println("Amount:");
        double amt = Double.parseDouble(scanner.nextLine());
        System.out.println("Frequency:");
        String freq = scanner.nextLine();
        manager.addRecurring(new RecurringTransaction(cat, amt, freq));
    }

    private void viewRecurring() {
        for (RecurringTransaction rt : manager.getRecurring()) {
            System.out.println(rt);
        }
    }

    private void addInvestment() {
        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("Amount:");
        double amt = Double.parseDouble(scanner.nextLine());
        System.out.println("Date:");
        String date = scanner.nextLine();
        manager.addInvestment(new Investment(name, amt, date));
    }

    private void viewInvestments() {
        for (Investment inv : manager.getInvestments()) {
            System.out.println(inv);
        }
    }

    private void viewUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/users.txt"))) {
            System.out.println("Registered Users:");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("- " + parts[0]);
            }
        } catch (IOException e) {
            System.out.println("Error reading users.txt");
        }
    }
}
