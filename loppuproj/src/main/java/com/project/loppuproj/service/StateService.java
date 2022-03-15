package com.project.loppuproj.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.loppuproj.data.Course;
import com.project.loppuproj.data.Student;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    File studStateFile;
    File courStateFile;

    public StateService() {
        try {
            this.studStateFile = new ClassPathResource("static/StudentState.json").getFile();
            this.courStateFile = new ClassPathResource("static/KurssiState.json").getFile();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public List<Student> readStudState() throws FileNotFoundException {
        List<Student> studentit = new ArrayList<>();
        JSONParser parseri = new JSONParser();
        try {
            JSONArray obj = (JSONArray) parseri.parse(new FileReader(studStateFile));
            for (Object o : obj) {
                JSONObject student = (JSONObject) o;
                Student studentti = new Student(student.get("name").toString(), student.get("email").toString(),
                        UUID.fromString(student.get("identifier").toString()));
                studentti.addOsp(Integer.parseInt(student.get("osp").toString()));
                JSONArray kursseja = (JSONArray) student.get("courses");
                for (Object obje : kursseja) {
                    studentti.addToCourse(UUID.fromString(obje.toString()));
                }
                studentit.add(studentti);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentit;
    }

    public List<Course> readCourseState() throws FileNotFoundException {
        List<Course> kurssit = new ArrayList<>();
        JSONParser parseri = new JSONParser();
        try {
            JSONArray obj = (JSONArray) parseri.parse(new FileReader(courStateFile));
            for (Object o : obj) {
                JSONObject kurssi = (JSONObject) o;
                Course course = new Course(kurssi.get("subject").toString(), kurssi.get("teacher").toString(),
                        Integer.parseInt(kurssi.get("roomNum").toString()),
                        UUID.fromString(kurssi.get("classId").toString()));
                JSONArray oppilaat = (JSONArray) kurssi.get("students");
                for (Object objop : oppilaat) {
                    course.addStudent(UUID.fromString(objop.toString()));
                }
                kurssit.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kurssit;
    }

    public void writeStudentState(List<Student> opiskelijat) throws IOException {
        ObjectMapper mapperi = new ObjectMapper();
        mapperi.writeValue(new FileWriter(studStateFile), opiskelijat);
    }

    public void writeCourseState(List<Course> kurssit) throws IOException {
        ObjectMapper mapperi = new ObjectMapper();
        mapperi.writeValue(new FileWriter(courStateFile), kurssit);
    }

}
