package com.example.Barebones.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Barebones.model.Student;


public interface StudentRepository extends JpaRepository<Student, Long>{

}
