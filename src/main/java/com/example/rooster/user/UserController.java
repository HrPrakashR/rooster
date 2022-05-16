package com.example.rooster.user;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final EmployeeService employeeService;
    private final UserService userService;


    public UserController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping("/sendPassword/{userId}")
    public ResponseEntity<String> sendPassword(@PathVariable long userId) {
        Employee employee = this.employeeService.getEmployeeById(userId);
        String password = this.userService.getPassword();
        employee.setPassword(password);
        this.employeeService.setEmployee(employee);
        this.userService.sendPassword(employee.getEmail(), password);
        return new ResponseEntity<>("Successfully sent", HttpStatus.OK);
    }
}
