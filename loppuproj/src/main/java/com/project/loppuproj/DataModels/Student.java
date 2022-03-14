package com.project.loppuproj.DataModels;

import java.util.ArrayList;
import java.util.UUID;

public class Student {
    private String name;
    private String email;
    private int osp = 0;
    ArrayList<UUID> cources = new ArrayList<>();
    protected UUID identifier = UUID.randomUUID();

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

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

    public void changeName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public int getOsp() {
        return this.osp;
    }

    public void addOsp(int addables) {
        this.osp += addables;
    }

    public void addToCourse(UUID kurssiId) {
        this.cources.add(kurssiId);
    }

}
