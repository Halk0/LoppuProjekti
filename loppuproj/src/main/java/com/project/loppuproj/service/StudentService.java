package com.project.loppuproj.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.project.loppuproj.data.Student;
import com.project.loppuproj.data.StudentMeta;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    // init state service and array of students
    protected StateService state = new StateService();
    private List<Student> students = new ArrayList<>();

    public StudentService() {
        try {
            // read previous state and make the returned array current state
            this.students = this.state.readStudState();
        } catch (FileNotFoundException fe) {
            System.out.println("File not found, continuing with empty state");
        }
    }

    public void studentAdd(StudentMeta lisää) {
        // add new student based on metadata provided
        students.add(new Student(lisää.name, lisää.email));
        try {
            // write state to file
            this.state.writeStudentState(this.students);
        } catch (IOException e) {
            System.out.println("Error writing to state json");
        }
    }

    public List<Student> getStudents() {
        // return current student state
        return new ArrayList<>(this.students);
    }

    public Student searchStudentsById(UUID identifier) throws NoSuchElementException {
        // loop troug students in state
        for (Student studentti : this.students)
            // if student with provided uuid is found
            if (studentti.getIdentifier().equals(identifier))
                // return that student object
                return studentti;
        throw new NoSuchElementException("Student not found from state");
    }

    public List<Student> searchStudentsContact(String searchVal) {
        // init array for found students
        List<Student> löydetyt = new ArrayList<>();
        // loop trough students in state
        for (Student studentti : this.students) {
            // if name matches search value add to array
            if (studentti.getName().toLowerCase().equals(searchVal.toLowerCase()))
                löydetyt.add(studentti);
            // if email matches search value add to array
            else if (studentti.getEmail().toLowerCase().equals(searchVal.toLowerCase()))
                löydetyt.add(studentti);
        }
        // return array of students with matching descriptions
        return löydetyt;
    }

    public void addOsp(int addables, UUID identifier) {
        // loop trough student in state
        for (Student studentti : this.students) {
            // if uuid matches
            if (studentti.getIdentifier().equals(identifier))
                // add amount of osp provided to the student
                studentti.addOsp(addables);
        }
        try {
            // write state for persistency
            this.state.writeStudentState(this.students);
        } catch (IOException e) {
            System.out.println("Error writing to state json");
        }
    }

    public void addKurssi(UUID studentId, UUID kurssiId) throws NoSuchElementException {
        // loop trough students
        for (Student studentti : this.students) {
            // if student id matches
            if (studentti.getIdentifier().equals(studentId)) {
                // add course uuid to list of courses in the student object
                studentti.addToCourse(kurssiId);
            }
            try {
                // write state to file for persistency
                this.state.writeStudentState(this.students);
                return;
            } catch (IOException e) {
                System.out.println("Error writing to state json");
            }
        }
        throw new NoSuchElementException("No such student");
    }

}
