package logic;

import model.*;
import storage.FileSystem;

import java.util.*;

public class FinanceManager {
    private final List<Transaction> transactions;
    private final List<SavingsGoal> goals;
    private final List<RecurringTransaction> recurring;
    private final List<Investment> investments;
    private final Map<String, Budget> budgets;

    private final FileSystem fileSystem = FileSystem.getInstance();

    private final String TX_FILE = "data/transactions.txt";
    private final String BUDGET_FILE = "data/budgets.txt";
    private final String SAVINGS_FILE = "data/savings.txt";
    private final String RECURRING_FILE = "data/recurring.txt";
    private final String INVESTMENTS_FILE = "data/investments.txt";

    public FinanceManager() {
        transactions = fileSystem.loadList(TX_FILE);
        budgets = fileSystem.loadMap(BUDGET_FILE);
        goals = fileSystem.loadList(SAVINGS_FILE);
        recurring = fileSystem.loadList(RECURRING_FILE);
        investments = fileSystem.loadList(INVESTMENTS_FILE);
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
        if ("expense".equals(t.getType()) && budgets.containsKey(t.getCategory())) {
            budgets.get(t.getCategory()).addSpent(t.getAmount());
        }
    }

    public void deleteTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
        }
    }

    public double getCurrentBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getType().equals("income") ? t.getAmount() : -t.getAmount();
        }
        return balance;
    }

    public void setBudget(String category, double limit) {
        budgets.put(category, new Budget(category, limit));
    }

    public void addSavingsGoal(SavingsGoal goal) {
        goals.add(goal);
    }

    public void addRecurring(RecurringTransaction rt) {
        recurring.add(rt);
    }

    public void addInvestment(Investment i) {
        investments.add(i);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Map<String, Budget> getBudgets() {
        return budgets;
    }

    public List<SavingsGoal> getGoals() {
        return goals;
    }

    public List<RecurringTransaction> getRecurring() {
        return recurring;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void saveAll() {
        fileSystem.saveList(TX_FILE, transactions);
        fileSystem.saveMap(BUDGET_FILE, budgets);
        fileSystem.saveList(SAVINGS_FILE, goals);
        fileSystem.saveList(RECURRING_FILE, recurring);
        fileSystem.saveList(INVESTMENTS_FILE, investments);
    }

    public String generateReport() {
        StringBuilder sb = new StringBuilder("--- Financial Report ---\n");
        sb.append("Balance: $").append(getCurrentBalance()).append("\n\nTransactions:\n");
        for (Transaction t : transactions) sb.append(t).append("\n");
        return sb.toString();
    }
}
