package com.project.loppuproj.data;

import java.util.ArrayList;
import java.util.UUID;

public class Course {
    // class variables for keeping needed data on course object
    private String subject;
    private String teacher;
    private int roomNum;
    private ArrayList<UUID> students = new ArrayList<>();
    protected UUID classId = UUID.randomUUID();

    // empty intializer for springboot
    public Course() {
    }

    // initializer for objects generated based on metadata received from REST api
    public Course(String subject, String teacher, int roomNum) {
        this.subject = subject;
        this.teacher = teacher;
        this.roomNum = roomNum;
    }

    // initializer for objects generated based on data read from json file
    public Course(String subject, String teacher, int roomNum, UUID classId) {
        this.subject = subject;
        this.teacher = teacher;
        this.roomNum = roomNum;
        this.classId = classId;
    }

    // returns teacer var value
    public String getTeacher() {
        return this.teacher;
    }

    // return subject var value
    public String getSubject() {
        return this.subject;
    }

    // return roomNumber var value
    public int getLocation() {
        return this.roomNum;
    }

    // append student uuid to array of students in course
    public void addStudent(UUID studentId) {
        this.students.add(studentId);
    }

    // return array of student uuid in course
    public ArrayList<UUID> getStudents() {
        return this.students;
    }

    // return courses uuid
    public UUID getUUID() {
        return this.classId;
    }

}
