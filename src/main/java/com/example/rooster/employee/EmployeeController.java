package com.example.rooster.employee;

import com.example.rooster.team.TeamDTO;
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
        Employee employee = employeeService.convertToEmployee(employeeDTO);
        return employeeService.setEmployee(employee);
    }

    // ToDo: Use DTO instead of ID and search employee by email (like new team service method)
    @DeleteMapping("/delete/{email}")
    public EmployeeDTO deleteEmployee(@PathVariable String email) {
        Employee employee = employeeService.deleteEmployeeByEmail(email);
        return this.employeeService.convertToDTO(employee);
    }


    // ToDo: do the same with a DTO and post mapping, without path-variable
    @GetMapping("/get/{email}")
    public EmployeeDTO getEmployee(@PathVariable String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        return this.employeeService.convertToDTO(employee);
    }


    // ToDo: do the same with a DTO and post mapping, without path-variable
    @PostMapping("/edit")
    public Employee update() {

        return null;
    }


    // Einzelnen Mitarbeiter uebergeben
}
