package com.project.loppuproj.Controllers;

import java.util.List;
import java.util.UUID;

import com.project.loppuproj.DataModels.*;
import com.project.loppuproj.services.CourseService;
import com.project.loppuproj.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class RestController {

    @Autowired
    StudentService studentService;
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
    public List<Course> getByStudent(@PathVariable UUID id) {
        return courseService.searchStudentsCourses(id);
    }

    @PostMapping("add/course")
    public String addCourse(@RequestBody CourseService.CourseMeta kurssi) {
        courseService.courseAdd(kurssi);
        return "Success";
    }

    @PostMapping("add/student")
    public String addStudent(@RequestBody StudentService.StudentMeta student) {
        studentService.studentAdd(student);
        return "Success";
    }

    @PostMapping("add/student/courses")
    public String addStudentToCourse(@RequestBody UUID kurssiId, @RequestBody UUID studentId) {
        courseService.addStudentToCourse(studentId, kurssiId);
        studentService.addKurssi(studentId, kurssiId);
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

    @PostMapping("students/{id}/addosp")
    public String addOspToStudnet(@PathVariable UUID id, @RequestBody int osp) {
        studentService.addOsp(osp, id);
        return "Success";
    }
}
