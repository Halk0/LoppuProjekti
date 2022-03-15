package com.project.loppuproj.data;

public class CourseMeta {
    public String subject;
    public String teacher;
    public int roomNum;

    // init metadata to do typechecking on requests from REST-api
    CourseMeta(String subject, String teacher, int roomNum) {
        this.subject = subject;
        this.teacher = teacher;
        this.roomNum = roomNum;
    }
}
