package com.ltp.sunday_school_management_app.entity;

public class DepartmentEntity {
    int id;
    String name;

    public DepartmentEntity() {
    }

    public DepartmentEntity(int id, String name) {
        this.id = id;
        this.name = name;
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
