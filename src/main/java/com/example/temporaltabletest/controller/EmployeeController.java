package com.example.temporaltabletest.controller;

import com.example.temporaltabletest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeesHistoryRepository employeesHistoryRepository;

    @Autowired
    private EmployeeRepository employeesRepository;

    @GetMapping("/test")
    public String test() {
        EmployeesHistory e =employeesHistoryRepository.findEmployeesHistoryByTimestamp("Bernard Marx", "2024-07-27 16:19:05.416386+00").get();
        Employee e2 = employeesRepository.findByMitarbeiterID(3).get();
        return "test";
    }

    @GetMapping("")
    public String getEmployeeList(Model model) {
       // EmployeesHistory e =employeesHistoryRepository.findEmployeesHistoryByTimestamp("Bernard Marx", "2024-07-27 16:19:05.416386+00").get();
        List<Employee> employees = employeesRepository.findAll();
        model.addAttribute("employees", employees);
        if (employees.size() >0) {
            return "employees";
        } else {
            return "empty";
        }
    }

    @GetMapping("/{employeeId}")
    public String getEmployeeList(Model model, @PathVariable int employeeId) {
        Employee employee= employeesRepository.findByMitarbeiterID(employeeId).get();
        model.addAttribute("employee", employee);
        return "employee";
    }

    @GetMapping("/new")
    public String getNewEmployeeForm() {
        return "new";
    }

    @PostMapping(value="/{employeeId}", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveEmployee(@PathVariable int employeeId, @RequestParam Map<String, String> formData) {
        Employee employee = employeesRepository.findByMitarbeiterID(employeeId).get();
        employee.setName(formData.get("name"));
        employee.setDepartment(formData.get("department"));
        employee.setSalary(new BigDecimal(formData.get("salary")));
        employeesRepository.save(employee);
        String redirectUrl = "http://localhost:8080/employees";
        return "redirect:" + redirectUrl;
    }

    @PostMapping(value="", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addEmployee( @RequestParam Map<String, String> formData) {
        Employee employee = new Employee();
        employee.setName(formData.get("name"));
        employee.setDepartment(formData.get("department"));
        employee.setSalary(new BigDecimal(formData.get("salary")));
        employeesRepository.save(employee);
        String redirectUrl = "http://localhost:8080/employees";
        return "redirect:" + redirectUrl;
    }

}



