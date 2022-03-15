package com.project.loppuproj.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.project.loppuproj.data.Course;
import com.project.loppuproj.data.CourseMeta;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

@Service
public class CourseService {

    protected StateService state = new StateService();
    public List<Course> courses = new ArrayList<>();

    public CourseService() {
        try {
            this.courses = this.state.readCourseState();
        } catch (FileNotFoundException e) {
            System.out.println("Course state file not found using empty state.");
        }
    }

    public void courseAdd(CourseMeta lisää) {
        this.courses.add(new Course(lisää.subject, lisää.teacher, lisää.roomNum));
        try {
            this.state.writeCourseState(this.courses);
        } catch (IOException e) {
            System.out.println("error writing to state json");
        }
    }

    public List<Course> getCourses() {
        return new ArrayList<>(this.courses);
    }

    public Course searchCoursesById(UUID identifier) throws NoSuchElementException {
        for (Course kurssi : this.courses)
            if (kurssi.getUUID().equals(identifier))
                return kurssi;
        throw new NoSuchElementException("Student not found from state");
    }

    public List<Course> searchCoursesByDesc(String searchVal) {
        List<Course> löydetyt = new ArrayList<>();
        for (Course kurssi : this.courses) {
            if (kurssi.getSubject().toLowerCase().equals(searchVal.toLowerCase())) {
                System.out
                        .println("comparing " + kurssi.getSubject().toLowerCase() + " and " + searchVal.toLowerCase());
                löydetyt.add(kurssi);
            } else if (kurssi.getTeacher().toLowerCase().equals(searchVal.toLowerCase()))
                löydetyt.add(kurssi);
        }
        return löydetyt;
    }

    public List<Course> searchCoursesByLocation(int classId) {
        List<Course> löydetyt = new ArrayList<>();
        for (Course kurssi : this.courses) {
            if (kurssi.getLocation() == classId)
                löydetyt.add(kurssi);
        }
        return löydetyt;
    }

    public List<Course> searchStudentsCourses(String studentId) {
        List<Course> löydetyt = new ArrayList<>();

        for (Course kurssi : this.courses) {
            if (kurssi.getStudents().contains(UUID.fromString(studentId)))
                löydetyt.add(kurssi);
        }
        return löydetyt;
    }

    public void addStudentToCourse(UUID studentId, UUID courseId) throws NoSuchElementException {
        for (Course kurssi : this.courses) {
            if (kurssi.getUUID().equals(courseId)) {
                kurssi.addStudent(studentId);
            }
            try {
                this.state.writeCourseState(this.courses);
                return;
            } catch (IOException e) {
                System.out.println("Error writing to state json");
            }
        }
        throw new NoSuchElementException("No such course found");
    }

}
