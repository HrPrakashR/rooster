package com.example.rooster.user;

import com.example.rooster.employee.EmployeeService;
import com.example.rooster.team.Team;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private EmployeeService employeeService;

    public UserController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/sendPassword/{userId}")
    public ResponseEntity<String> sendPassword(@PathVariable long userId) {
        String email = this.employeeService.getEmployeeById(userId).getEmail();
        return new ResponseEntity<>("Successfully sent", HttpStatus.OK);
    }
}
