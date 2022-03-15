package com.project.loppuproj.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.project.loppuproj.data.*;
import com.project.loppuproj.service.CourseService;
import com.project.loppuproj.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MooRestController {

    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;

    @GetMapping("courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("courses/desc/{desc}")
    public List<Course> getByCDesc(@PathVariable String desc) {
        return courseService.searchCoursesByDesc(desc);
    }

    @GetMapping("courses/uuid/{id}")
    public Course getById(@PathVariable UUID id) {
        return courseService.searchCoursesById(id);
    }

    @GetMapping("courses/classnum/{classNum}")
    public List<Course> getByRoom(@PathVariable int classNum) {
        return courseService.searchCoursesByLocation(classNum);
    }

    @GetMapping("courses/studentid/{id}")
    public List<Course> getByStudent(@PathVariable String id) {
        return courseService.searchStudentsCourses(id);
    }

    @PostMapping("add/course")
    public String addCourse(@RequestBody CourseMeta kurssi) {
        courseService.courseAdd(kurssi);
        return "Success";
    }

    @PostMapping("add/student")
    public String addStudent(@RequestBody StudentMeta student) {
        studentService.studentAdd(student);
        return "Success";
    }

    @PostMapping("add/student/courses")
    public String addStudentToCourse(@RequestBody Map<String, String> json) {
        courseService.addStudentToCourse(UUID.fromString(json.get("studentId")), UUID.fromString(json.get("kurssiId")));
        studentService.addKurssi(UUID.fromString(json.get("studentId")), UUID.fromString(json.get("kurssiId")));
        return "Success";
    }

    @GetMapping("students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("students/desc/{desc}")
    public List<Student> getBySDesc(@PathVariable String desc) {
        return studentService.searchStudentsContact(desc);
    }

    @GetMapping("students/studentid/{id}")
    public Student getStudentById(@PathVariable UUID id) {
        return studentService.searchStudentsById(id);
    }

    @PostMapping("students/studentid/{id}/addosp")
    public String addOspToStudnet(@PathVariable UUID id, @RequestBody int osp) {
        studentService.addOsp(osp, id);
        return "Success";
    }
}
