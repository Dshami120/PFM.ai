package model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private double amount;
    private String category;
    private String date;
    private String description;
    private String type;

    public Transaction(double amount, String category, String date, String description, String type) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public String getType() { return type; }

    public String toString() {
        return type.toUpperCase() + ": $" + amount + " | " + category + " | " + date + " | " + description;
    }
}
