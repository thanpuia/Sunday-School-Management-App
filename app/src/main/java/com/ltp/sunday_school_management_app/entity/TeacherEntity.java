package com.ltp.sunday_school_management_app.entity;

public class TeacherEntity {
    int id;
    String name;
    String dob;
    String phone;
    String bial;
    String section;
    String location;
    String photo;

    public TeacherEntity() {
    }

    public TeacherEntity(int id, String name, String dob, String phone, String bial, String section, String location, String photo) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.bial = bial;
        this.section = section;
        this.location = location;
        this.photo = photo;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBial() {
        return bial;
    }

    public void setBial(String bial) {
        this.bial = bial;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
