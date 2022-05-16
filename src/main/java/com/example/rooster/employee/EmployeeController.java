package com.example.rooster.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees/")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Showing a certain employee
    @GetMapping("/get/{id}")
    public EmployeeDTO getEmployee(@PathVariable long id) {
        return this.employeeService.convertToDTO(employeeService.getEmployeeById(id));
    }

    //Showing all employees
    @GetMapping("/get_all")
    List<EmployeeDTO> getEmployees() {
        return employeeService.getEmployeesAsDTO();
    }

    //Creating a new employee
    @PostMapping("/new")
    public EmployeeDTO addNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = employeeService.convertToEmployee(employeeDTO);
        employeeService.setEmployee(newEmployee);
        return employeeService.convertToDTO(newEmployee);
    }

    //Editing an existing employee
    @PostMapping("/edit")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = employeeService.convertToEmployee(employeeDTO);
        employeeService.setEmployee(newEmployee);
        return employeeService.convertToDTO(newEmployee);
    }

    //Deleting an existing employee
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        employeeService.deletePeriodsOfEmployee(employeeService.getEmployeeById(id));
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

}
