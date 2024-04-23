package com.viewnext.springbootpractice.student;

import java.util.List; // Import the correct List class
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> getStudents() {
    return studentRepository.findAll();
  }

  public void addNewStudent(Student student) {
    Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

    if (studentOptional.isPresent()) {
      throw new IllegalStateException("Email taken");
    }
    
    studentRepository.save(student);
  }

  public void deleteStudent(Long studentId) {
    boolean exists = studentRepository.existsById(studentId);

    if (!exists) {
      throw new IllegalStateException("Student with id " + studentId + " does not exist");
    }

    studentRepository.deleteById(studentId);
  }

  @Transactional
  public void updateStudent(Long studentId, Student student) {
    Student studentToUpdate = studentRepository.findById(studentId)
      .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist"));

    if (student.getName() != null && student.getName().length() > 0) {
      studentToUpdate.setName(student.getName());
    }

    if (student.getEmail() != null && student.getEmail().length() > 0) {
      Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

      if (studentOptional.isPresent() && !studentOptional.get().getId().equals(studentId)) {
        throw new IllegalStateException("Email taken");
      }

      studentToUpdate.setEmail(student.getEmail());
    }

    if (student.getDob() != null) {
      studentToUpdate.setDob(student.getDob());
    }

    studentRepository.save(studentToUpdate);
  }
}