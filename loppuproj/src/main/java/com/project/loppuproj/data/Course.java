package com.project.loppuproj.data;

import java.util.ArrayList;
import java.util.UUID;

public class Course {
    private String subject;
    private String teacher;
    private int roomNum;
    private ArrayList<UUID> students = new ArrayList<>();
    protected UUID classId = UUID.randomUUID();

    public Course() {
    }

    public Course(String subject, String teacher, int roomNum) {
        this.subject = subject;
        this.teacher = teacher;
        this.roomNum = roomNum;
    }

    public Course(String subject, String teacher, int roomNum, UUID classId) {
        this.subject = subject;
        this.teacher = teacher;
        this.roomNum = roomNum;
        this.classId = classId;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public String getSubject() {
        return this.subject;
    }

    public int getLocation() {
        return this.roomNum;
    }

    public void addStudent(UUID studentId) {
        this.students.add(studentId);
    }

    public ArrayList<UUID> getStudents() {
        return this.students;
    }

    public UUID getUUID() {
        return this.classId;
    }

}
