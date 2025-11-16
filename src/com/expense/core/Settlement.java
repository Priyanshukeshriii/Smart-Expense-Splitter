package com.expense.core;

public class Settlement {
    private int fromMember;
    private int toMember;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFromMember() {
        return fromMember;
    }

    public void setFromMember(int fromMember) {
        this.fromMember = fromMember;
    }

    public int getToMember() {
        return toMember;
    }

    public void setToMember(int toMember) {
        this.toMember = toMember;
    }
}
