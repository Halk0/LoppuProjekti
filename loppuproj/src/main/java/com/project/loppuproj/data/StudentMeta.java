package com.project.loppuproj.data;

public class StudentMeta {

    public String name;
    public String email;

    // metadata for typechecking data provided from REST api
    StudentMeta(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
