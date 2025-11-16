package com.expense.core;

public class Share {
    private int id;
    private int expenseId;
    private int memberId;
    private int groupId;
    private double shareAmount;

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

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public double getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(double shareAmount) {
        this.shareAmount = shareAmount;
    }

    public Share(int expenseId, int groupId, int id, int memberId, double shareAmount) {
        this.expenseId = expenseId;
        this.groupId = groupId;
        this.id = id;
        this.memberId = memberId;
        this.shareAmount = shareAmount;
    }
}
