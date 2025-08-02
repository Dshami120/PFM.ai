package model;

import java.io.Serializable;

public class RecurringTransaction implements Serializable {
    private String category;
    private double amount;
    private String frequency;

    public RecurringTransaction(String category, double amount, String frequency) {
        this.category = category;
        this.amount = amount;
        this.frequency = frequency;
    }

    public String toString() {
        return category + ": $" + amount + " (" + frequency + ")";
    }
}
