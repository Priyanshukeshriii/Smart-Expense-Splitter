package com.expense.core;

public class Group {
    private int id;
    private int admin_id;
    private String name;

    public Group(int id, String name, int adminId) {
        this.id = id;
        this.name = name;
        this.admin_id = adminId;
    }
    public Group(){}

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
