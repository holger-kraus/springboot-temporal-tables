package com.example.temporaltabletest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    public Optional<Employee> findByMitarbeiterID(Integer id);

    List<Employee> findAll();
}