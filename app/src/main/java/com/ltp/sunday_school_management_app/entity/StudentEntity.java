package com.ltp.sunday_school_management_app.entity;

public class StudentEntity {
    int id;
    int teacher_id;
    int department_id;

    String name;
    String fathers_name;
    String mothers_name;
    String guardian;
    String dob;
    String phone1;
    String phone2;
    String bial;
    String section;
    String location;
    String photo;

    public StudentEntity() {
    }

    public StudentEntity(int id, int teacher_id, int department_id, String name, String fathers_name, String mothers_name, String guardian, String dob, String phone1, String phone2, String bial, String section, String location, String photo) {
        this.id = id;
        this.teacher_id = teacher_id;
        this.department_id = department_id;
        this.name = name;
        this.fathers_name = fathers_name;
        this.mothers_name = mothers_name;
        this.guardian = guardian;
        this.dob = dob;
        this.phone1 = phone1;
        this.phone2 = phone2;
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

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathers_name() {
        return fathers_name;
    }

    public void setFathers_name(String fathers_name) {
        this.fathers_name = fathers_name;
    }

    public String getMothers_name() {
        return mothers_name;
    }

    public void setMothers_name(String mothers_name) {
        this.mothers_name = mothers_name;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
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
