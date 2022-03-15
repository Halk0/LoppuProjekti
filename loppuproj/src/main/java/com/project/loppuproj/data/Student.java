package com.project.loppuproj.data;

import java.util.ArrayList;
import java.util.UUID;

public class Student {
    // create class variables for neccessary data on student
    private String name;
    private String email;
    private int osp = 0;
    private ArrayList<UUID> courses = new ArrayList<>();
    protected UUID identifier = UUID.randomUUID();

    // empty initializer for student
    public Student() {
    }

    // initializer for student based on metadata from rest api
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // initializer for student based on metadata from json file
    public Student(String name, String email, UUID identifier) {
        this.name = name;
        this.email = email;
        this.identifier = identifier;
    }

    public UUID getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    // change name of student
    public void changeName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    // change email of student
    public void changeEmail(String email) {
        this.email = email;
    }

    public int getOsp() {
        return this.osp;
    }

    // adds amoun provided to osp value
    public void addOsp(int addables) {
        this.osp += addables;
    }

    // add course UUID to list of courses student is in
    public void addToCourse(UUID kurssiId) {
        this.courses.add(kurssiId);
    }

    // return array of courses student is in
    public ArrayList<UUID> getCourses() {
        return this.courses;
    }

}
