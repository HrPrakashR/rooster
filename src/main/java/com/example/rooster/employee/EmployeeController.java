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


    @GetMapping("/get_all")
    List<EmployeeDTO> getEmployees() {
        return employeeService.getEmployeesAsDTO();
    }

    @PostMapping("/new")
    public Employee addNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.setEmployee(employeeService.convertToEmployee(employeeDTO));
    }

    // ToDo: Use DTO instead of ID and search employee by email (like new team service method)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }


    // ToDo: do the same with a DTO and post mapping, without path-variable
    @GetMapping("/get/{id}")
    public EmployeeDTO getEmployee(@PathVariable long id) {
        return this.employeeService.convertToDTO(employeeService.getEmployeeById(id));
    }


    // ToDo: do the same with a DTO and post mapping, without path-variable
    @PostMapping("/edit")
    public Employee update() {

        return null;
    }


    // Einzelnen Mitarbeiter uebergeben
}
