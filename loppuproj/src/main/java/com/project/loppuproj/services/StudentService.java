package com.project.loppuproj.services;

import com.project.loppuproj.DataModels.Student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    protected StateService state = new StateService();

    @Autowired
    private List<Student> students = new ArrayList<>();

    public class StudentMeta {
        private String name;
        private String email;
    }

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
            if (studentti.getIdentifier() == identifier)
                return studentti;
        throw new NoSuchElementException("Student not found from state");
    }

    public List<Student> searchStudentsContact(String searchVal) {
        List<Student> löydetyt = new ArrayList<>();
        for (Student studentti : this.students) {
            if (studentti.getName() == searchVal)
                löydetyt.add(studentti);
            else if (studentti.getEmail() == searchVal)
                löydetyt.add(studentti);
        }
        return löydetyt;
    }

    public void addOsp(int addables, UUID identifier) {
        for (Student studentti : this.students) {
            if (studentti.getIdentifier() == identifier)
                studentti.addOsp(addables);
        }
        try {
            this.state.writeStudentState(this.students);
        } catch (IOException e) {
            System.out.println("Error writing to state json");
        }
    }

    public void addKurssi(UUID studentId, UUID kurssiId) {
        for (Student studentti : this.students) {
            if (studentti.getIdentifier() == studentId) {
                studentti.addToCourse(kurssiId);
            }
        }
        try {
            this.state.writeStudentState(this.students);
        } catch (IOException e) {
            System.out.println("Error writing to state json");
        }
    }

}
