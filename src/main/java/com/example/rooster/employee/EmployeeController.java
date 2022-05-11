package com.example.rooster.employee;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/employees")
    List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @PostMapping("/employee/new")
    public Employee addNewEmployee(@RequestBody Employee newEmployee) {
        return employeeService.setEmployee(newEmployee);
    }

    // ToDo: Use DTO instead of ID and search employee by email (like new team service method)
    @DeleteMapping("/employee/delete/{id}")
    public List<Employee> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
        return getEmployees();
    }


    // ToDo: do the same with a DTO and post mapping, without path-variable
    @GetMapping("/employee/{id}")
    public Employee employeeDetails(@PathVariable long id) {
        return employeeService.getEmployee(id);
    }

    // ToDo: do the same with a DTO and post mapping, without path-variable
    @PostMapping("/employee/update/{id}")
    public void employeeUpdate(@PathVariable long id, Employee employee) {
        employeeService.updateFirstName(id, employee.getFirstName());
    }


    // Einzelnen Mitarbeiter uebergeben
}
