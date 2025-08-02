package model;

import java.io.Serializable;

public class SavingsGoal implements Serializable {
    private String name;
    private double targetAmount;
    private double currentSaved;

    public SavingsGoal(String name, double targetAmount, double currentSaved) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentSaved = currentSaved;
    }

    public void updateSavings(double amount) {
        this.currentSaved += amount;
    }

    public String toString() {
        return name + ": Saved $" + currentSaved + " / $" + targetAmount;
    }
}
