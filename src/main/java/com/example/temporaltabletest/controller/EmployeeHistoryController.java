package com.example.temporaltabletest.controller;

import com.example.temporaltabletest.Employee;
import com.example.temporaltabletest.EmployeesHistory;
import com.example.temporaltabletest.EmployeesHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/employeehistory")
@Controller
public class EmployeeHistoryController {

    @Autowired
    private EmployeesHistoryRepository employeesHistoryRepository;

    @GetMapping("/{employeeId}")
    public String getEmployeeHistoryList(Model model, @PathVariable int employeeId) {
        List<EmployeesHistory> employees= employeesHistoryRepository.findByMitarbeiterID(employeeId);
        model.addAttribute("employee", employees);
        model.addAttribute("employeeId", employeeId);
        return "employeehistory";
    }

}
