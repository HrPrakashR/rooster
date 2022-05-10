package com.example.rooster.employee;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Liste aller Arbeiter eines Teams uebergeben

    // Neuer Mitarbeiter

    // Mitarbeiter loeschen

    // Mitarbeiter bearbeiten

    // Einzelnen Mitarbeiter uebergeben
}
