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

    // init state service and courses array for keeping track of courses
    protected StateService state = new StateService();
    public List<Course> courses = new ArrayList<>();

    public CourseService() {
        try {
            // on object creation read previous state from file
            this.courses = this.state.readCourseState();
        } catch (FileNotFoundException e) {
            System.out.println("Course state file not found using empty state.");
        }
    }

    public void courseAdd(CourseMeta lisää) {
        // create a new course and add it to array of courses.
        this.courses.add(new Course(lisää.subject, lisää.teacher, lisää.roomNum));
        try {
            // write changes to course array to json file for persitency
            this.state.writeCourseState(this.courses);
        } catch (IOException e) {
            System.out.println("error writing to state json");
        }
    }

    public List<Course> getCourses() {
        // return current course state
        return new ArrayList<>(this.courses);
    }

    public Course searchCoursesById(UUID identifier) throws NoSuchElementException {
        // loop trough all the courses
        for (Course kurssi : this.courses)
            // if UUID mathces return specific course
            if (kurssi.getUUID().equals(identifier))
                return kurssi;
        throw new NoSuchElementException("Student not found from state");
    }

    public List<Course> searchCoursesByDesc(String searchVal) {
        // create array for courses that match search values
        List<Course> löydetyt = new ArrayList<>();
        for (Course kurssi : this.courses) {
            // if subject matches add to array
            if (kurssi.getSubject().toLowerCase().equals(searchVal.toLowerCase())) {
                System.out
                        .println("comparing " + kurssi.getSubject().toLowerCase() + " and " + searchVal.toLowerCase());
                löydetyt.add(kurssi);
            } else if (kurssi.getTeacher().toLowerCase().equals(searchVal.toLowerCase()))
                // if teacher matches add to array
                löydetyt.add(kurssi);
        }
        // return all the courses that had descriptive items that matched search terms
        return löydetyt;
    }

    public List<Course> searchCoursesByLocation(int classId) {
        // create a list for courses that are located in the classroom
        List<Course> löydetyt = new ArrayList<>();
        for (Course kurssi : this.courses) {
            // if location matches add to array
            if (kurssi.getLocation() == classId)
                löydetyt.add(kurssi);
        }
        // return all courses that are held in same room
        return löydetyt;
    }

    public List<Course> searchStudentsCourses(String studentId) {
        List<Course> löydetyt = new ArrayList<>();

        for (Course kurssi : this.courses) {
            // loop trough courses and course has specific student attendig add to array
            if (kurssi.getStudents().contains(UUID.fromString(studentId)))
                löydetyt.add(kurssi);
        }
        // return all the courses student is attending to
        return löydetyt;
    }

    public void addStudentToCourse(UUID studentId, UUID courseId) throws NoSuchElementException {
        // loop trough courses
        for (Course kurssi : this.courses) {
            // if course we were looking for is found
            if (kurssi.getUUID().equals(courseId)) {
                // add student specified to the course
                kurssi.addStudent(studentId);
            }
            try {
                // save state since state changes were made
                this.state.writeCourseState(this.courses);
                return;
            } catch (IOException e) {
                System.out.println("Error writing to state json");
            }
        }
        // if there was no such course throw exception
        throw new NoSuchElementException("No such course found");
    }

}
