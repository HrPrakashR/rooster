package com.example.rooster.employee;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // TODO Liste aller Arbeiter eines Teams uebergeben


    // Liste aller Mitarbeiter
    @GetMapping("/employees")
    List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    // Neuer Mitarbeiter
    @PostMapping("/employees")
    public Employee addNewEmployee(@RequestBody Employee newEmployee) {
        return employeeService.setEmployee(newEmployee);
    }


    // Mitarbeiter loeschen
    @DeleteMapping("/employee/delete/{id}")
    public List<Employee> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
        return getEmployees();
    }


    // Einzelnen Mitarbeiter uebergeben
    @GetMapping("/employee/details/{id}")
    public Employee employeeDetails(@PathVariable long id) {
        return employeeService.getEmployee(id);
    }

    // Mitarbeiter bearbeiten
    @PostMapping("/employee/update/{id}")
    public void employeeUpdate(@PathVariable long id, Employee employee) {
        employeeService.updateFirstName(id, employee.getFirstName());
    }


    // Einzelnen Mitarbeiter uebergeben
}
