package com.project.loppuproj.services;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.NoSuchElementException;

import com.project.loppuproj.DataModels.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    protected StateService state = new StateService();

    @Autowired
    public List<Course> courses = new ArrayList<>();

    public class CourseMeta {
        private String subject;
        private String teacher;
        private int roomNum;
    }

    public CourseService() {
        try {
            this.courses = this.state.readCourseState();
        } catch (FileNotFoundException e) {
            System.out.println("Course state file not found using empty state.");
        }
    }

    public void courseAdd(CourseMeta lisää) {
        this.courses.add(new Course(lisää.subject, lisää.teacher, lisää.roomNum));
    }

    public List<Course> getCourses() {
        return new ArrayList<>(this.courses);
    }

    public Course searchCoursesById(UUID identifier) throws NoSuchElementException {
        for (Course kurssi : this.courses)
            if (kurssi.getUUID() == identifier)
                return kurssi;
        throw new NoSuchElementException("Student not found from state");
    }

    public List<Course> searchCoursesByDesc(String searchVal) {
        List<Course> löydetyt = new ArrayList<>();
        for (Course kurssi : this.courses) {
            if (kurssi.getSubject() == searchVal)
                löydetyt.add(kurssi);
            else if (kurssi.getTeacher() == searchVal)
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

    public List<Course> searchStudentsCourses(UUID studentId) {
        List<Course> löydetyt = new ArrayList<>();
        for (Course kurssi : this.courses) {
            if (kurssi.getStudents().contains(studentId))
                löydetyt.add(kurssi);
        }
        return löydetyt;
    }

    public void addStudentToCourse(UUID studentId, UUID courseId) throws NoSuchElementException {
        for (Course kurssi : this.courses) {
            if (kurssi.getUUID() == courseId) {
                kurssi.addStudent(studentId);
            }
        }
        throw new NoSuchElementException("No such course found");
    }

}
