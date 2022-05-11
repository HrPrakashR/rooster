package com.example.rooster.employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Liste aller Arbeiter eines Teams uebergeben

    @GetMapping("/employees")
    List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    // Neuer Mitarbeiter

    // Mitarbeiter loeschen

    // Mitarbeiter bearbeiten

    // Einzelnen Mitarbeiter uebergeben
}
