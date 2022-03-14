package com.project.loppuproj.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.FileNotFoundException;

import com.project.loppuproj.DataModels.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.loppuproj.DataModels.Course;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    public List<Student> readStudState() throws FileNotFoundException {
        List<Student> studentit = new ArrayList<>();
        JSONParser parseri = new JSONParser();
        try {
            JSONArray obj = (JSONArray) parseri.parse(new FileReader("./OpiskelijaState.json"));
            for (Object o : obj) {
                JSONObject student = (JSONObject) o;
                Student studentti = new Student(student.get("name").toString(), student.get("email").toString(),
                        UUID.fromString(student.get("identifier").toString()));
                studentti.addOsp(Integer.parseInt(student.get("osp").toString()));
                JSONArray kurssit = (JSONArray) student.get("Courses");
                for (Object obje : kurssit) {
                    JSONObject opiskKurssi = (JSONObject) obje;
                    studentti.addToCourse(UUID.fromString(opiskKurssi.toString()));
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
            JSONArray obj = (JSONArray) parseri.parse(new FileReader("./KurssiState.json"));
            for (Object o : obj) {
                JSONObject kurssi = (JSONObject) o;
                Course course = new Course(kurssi.get("subject").toString(), kurssi.get("email").toString(),
                        Integer.parseInt(kurssi.get("roomNum").toString()),
                        UUID.fromString(kurssi.get("classId").toString()));
                JSONArray oppilaat = (JSONArray) kurssi.get("students");
                for (Object objop : oppilaat) {
                    JSONObject kursOpisk = (JSONObject) objop;
                    course.addStudent(UUID.fromString(kursOpisk.toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kurssit;
    }

    public void writeStudentState(List<Student> opiskelijat) throws IOException {
        ObjectMapper mapperi = new ObjectMapper();
        mapperi.writeValue(new FileWriter("./OpiskelijaState.json"), opiskelijat);
    }

    public void WriteCourseState(List<Course> kurssit) throws IOException {
        ObjectMapper mapperi = new ObjectMapper();
        mapperi.writeValue(new FileWriter("./KurssiState.json"), kurssit);
    }

}
