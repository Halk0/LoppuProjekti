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

    // create both statefile objects
    File studStateFile;
    File courStateFile;

    public StateService() {
        try {
            // on object init create new resource file from them to access them in funcionts
            // Spring boot does class path abstraction so this is a necessary step
            this.studStateFile = new ClassPathResource("static/StudentState.json").getFile();
            this.courStateFile = new ClassPathResource("static/KurssiState.json").getFile();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public List<Student> readStudState() throws FileNotFoundException {
        // init array for students and a parser for josn
        List<Student> studentit = new ArrayList<>();
        JSONParser parseri = new JSONParser();
        try {
            // parse the values read from file to JSONArray
            JSONArray obj = (JSONArray) parseri.parse(new FileReader(studStateFile));
            // loop trough objects in json
            for (Object o : obj) {
                // convert object o to JSONObject student
                JSONObject student = (JSONObject) o;
                // create new instance of student class and add data from json to it
                Student studentti = new Student(student.get("name").toString(), student.get("email").toString(),
                        UUID.fromString(student.get("identifier").toString()));
                // add the osp from json to that specific student
                studentti.addOsp(Integer.parseInt(student.get("osp").toString()));
                // create JSONArray from students courses
                JSONArray kursseja = (JSONArray) student.get("courses");
                // loop trough objects in kursseja json array
                for (Object obje : kursseja) {
                    // convert object to string to parse UUID from that string not very elegant, but
                    // didnt find a better way
                    studentti.addToCourse(UUID.fromString(obje.toString()));
                }
                // add parsed Student object to array
                studentit.add(studentti);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return the array created from the json file
        return studentit;
    }

    public List<Course> readCourseState() throws FileNotFoundException {
        // this does basically the same thing as readStudState() but keywords to access
        // data have changed
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
        // Create Object mapper to write json to file
        ObjectMapper mapperi = new ObjectMapper();
        // map the opsikelijat array of Student objects to the file to create json
        mapperi.writeValue(new FileWriter(studStateFile), opiskelijat);
    }

    public void writeCourseState(List<Course> kurssit) throws IOException {
        // Exactly like before but with array of Course objects
        ObjectMapper mapperi = new ObjectMapper();
        mapperi.writeValue(new FileWriter(courStateFile), kurssit);
    }

}
