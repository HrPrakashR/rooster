package com.example.rooster.employee;

import com.example.rooster.security.user.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees/")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserController userController;

    public EmployeeController(EmployeeService employeeService, UserController userController) {
        this.employeeService = employeeService;
        this.userController = userController;
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

    @GetMapping("/get_all/{teamId}")
    List<EmployeeDTO> getEmployeesByTeamIdAsDTO(@PathVariable long teamId) {
        return employeeService.getEmployeesByTeamIdAsDTO(teamId);
    }

    //Creating a new employee
    @PostMapping("/new")
    public EmployeeDTO addNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = employeeService.convertToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.setEmployee(newEmployee);
        this.userController.sendPassword(savedEmployee.getId()); //Send user a password after adding him/her to the database
        return employeeService.convertToDTO(savedEmployee);
    }

    //Editing an existing employee
    @PostMapping("/edit")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return this.addNewEmployee(employeeDTO);
    }

    //Deleting an existing employee
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        employeeService.deletePeriodsOfEmployee(employeeService.getEmployeeById(id));
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

}
