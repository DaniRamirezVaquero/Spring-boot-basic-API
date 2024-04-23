package com.viewnext.springbootpractice.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

  @Bean
  CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
    return args -> {
      Student Mariam = new Student(
        1L,
        "Mariam",
        LocalDate.of(2000, Month.APRIL, 1),
        "mariam.jamal@gmail.com"
      );

      Student Alex = new Student(
        2L,
        "Alex",
        LocalDate.of(2004, Month.APRIL, 12),
        "alex.garcia@gmail.com"
      );

      studentRepository.saveAll(
        List.of(Mariam, Alex)
      );
    };
  }
}
