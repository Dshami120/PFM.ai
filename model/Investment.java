package model;

import java.io.Serializable;

public class Investment implements Serializable {
    private String name;
    private double amount;
    private String date;

    public Investment(String name, double amount, String date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public String toString() {
        return "Investment in " + name + ": $" + amount + " on " + date;
    }
}
