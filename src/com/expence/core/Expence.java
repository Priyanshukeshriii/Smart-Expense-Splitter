package com.expence.core;

import java.time.LocalDate;

public class Expence {
    private int id;
    private int groupId;
    private int PaidByMember;
    private double amount;
    private String description;
    private LocalDate date;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaidByMember() {
        return PaidByMember;
    }

    public void setPaidByMember(int paidByMember) {
        PaidByMember = paidByMember;
    }
}
