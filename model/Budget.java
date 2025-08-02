package model;

import java.io.Serializable;

public class Budget implements Serializable {
    private String category;
    private double limit;
    private double spent;

    public Budget(String category, double limit) {
        this.category = category;
        this.limit = limit;
        this.spent = 0;
    }

    public void addSpent(double amount) { this.spent += amount; }
    public double getRemaining() { return limit - spent; }
    public String getCategory() { return category; }

    public String toString() {
        return category + ": Limit $" + limit + ", Spent $" + spent + ", Remaining $" + getRemaining();
    }
}
