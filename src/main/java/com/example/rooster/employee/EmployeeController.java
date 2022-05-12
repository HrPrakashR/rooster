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

    @GetMapping("/new")
    public EmployeeDTO addNewEmployee(){
        return new EmployeeDTO();
    }

    @PostMapping("/new")
    public Employee addNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.convertToEmployee(employeeDTO);
        return employeeService.setEmployee(employee);
    }

    // ToDo: Use DTO instead of ID and search employee by email (like new team service method)
    @DeleteMapping("/delete")
    public List<EmployeeDTO> deleteEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.deleteEmployeeByEmail(employeeDTO.getEmail());
        return getEmployees();
    }


    // ToDo: do the same with a DTO and post mapping, without path-variable
    @PostMapping("/get")
    public Employee getEmployee(@RequestBody EmployeeDTO employeeDTO) {
    return employeeService.getEmployeeByEmail(employeeDTO.getEmail());
    }

    // ToDo: do the same with a DTO and post mapping, without path-variable
    @PostMapping("/edit")
    public Employee update() {

        return null;
    }


    // Einzelnen Mitarbeiter uebergeben
}
