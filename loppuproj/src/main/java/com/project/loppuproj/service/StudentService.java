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

    protected StateService state = new StateService();
    private List<Student> students = new ArrayList<>();

    public StudentService() {
        try {
            this.students = this.state.readStudState();
        } catch (FileNotFoundException fe) {
            System.out.println("File not found, continuing with empty state");
        }
    }

    public void studentAdd(StudentMeta lisää) {
        students.add(new Student(lisää.name, lisää.email));
        try {
            this.state.writeStudentState(this.students);
        } catch (IOException e) {
            System.out.println("Error writing to state json");
        }
    }

    public List<Student> getStudents() {
        return new ArrayList<>(this.students);
    }

    public Student searchStudentsById(UUID identifier) throws NoSuchElementException {
        for (Student studentti : this.students)
            if (studentti.getIdentifier().equals(identifier))
                return studentti;
        throw new NoSuchElementException("Student not found from state");
    }

    public List<Student> searchStudentsContact(String searchVal) {
        List<Student> löydetyt = new ArrayList<>();
        for (Student studentti : this.students) {
            if (studentti.getName().toLowerCase().equals(searchVal.toLowerCase()))
                löydetyt.add(studentti);
            else if (studentti.getEmail().toLowerCase().equals(searchVal.toLowerCase()))
                löydetyt.add(studentti);
        }
        return löydetyt;
    }

    public void addOsp(int addables, UUID identifier) {
        for (Student studentti : this.students) {
            if (studentti.getIdentifier().equals(identifier))
                studentti.addOsp(addables);
        }
        try {
            this.state.writeStudentState(this.students);
        } catch (IOException e) {
            System.out.println("Error writing to state json");
        }
    }

    public void addKurssi(UUID studentId, UUID kurssiId) throws NoSuchElementException {
        for (Student studentti : this.students) {
            if (studentti.getIdentifier().equals(studentId)) {
                studentti.addToCourse(kurssiId);
            }
            try {
                this.state.writeStudentState(this.students);
                return;
            } catch (IOException e) {
                System.out.println("Error writing to state json");
            }
        }
        throw new NoSuchElementException("No such student");
    }

}
