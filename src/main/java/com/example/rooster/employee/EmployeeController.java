package com.example.rooster.employee;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees/")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/get_all")
    List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @PostMapping("/new")
    public Employee addNewEmployee(@RequestBody Employee newEmployee) {
        return employeeService.setEmployee(newEmployee);
    }

    // ToDo: Use DTO instead of ID and search employee by email (like new team service method)
    @DeleteMapping("/delete")
    public List<Employee> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
        return getEmployees();
    }


    // ToDo: do the same with a DTO and post mapping, without path-variable
    @GetMapping("/get")
    public Employee employeeDetails(@PathVariable long id) {
        return employeeService.getEmployee(id);
    }

    // ToDo: do the same with a DTO and post mapping, without path-variable
    @PostMapping("/edit")
    public void employeeUpdate(@PathVariable long id, Employee employee) {
        employeeService.updateFirstName(id, employee.getFirstName());
    }


    // Einzelnen Mitarbeiter uebergeben
}
