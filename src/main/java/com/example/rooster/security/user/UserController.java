package com.example.rooster.security.user;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeDTO;
import com.example.rooster.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final EmployeeService employeeService;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public UserController(EmployeeService employeeService, PasswordEncoder passwordEncoder, UserService userService) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    // TODO: Decide if sendPassword stays here / Alternative we will have it in user service without exclusive mapping
    @GetMapping("/sendPassword/{userId}")
    public ResponseEntity<String> sendPassword(@PathVariable long userId) {
        Employee employee = this.employeeService.getEmployeeById(userId);
        String password = this.userService.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        employee.setPassword(encodedPassword);
        this.employeeService.setEmployee(employee);
        this.userService.sendPassword(employee.getEmail(), password);
        return new ResponseEntity<>("Successfully sent", HttpStatus.OK);
    }

    @GetMapping("/sendPassword/email/{email}")
    public String sendPassword(@PathVariable String email) {
        Optional<Employee> employee = this.employeeService.findEmployeeByEmail(email);
        if(employee.isPresent()){
            sendPassword(employee.get().getId());
            return "Your new password was sent by E-Mail";
        }
        return "The email does not exist";
    }


    @GetMapping("/current")
    public EmployeeDTO current(Principal principal) {
        if (principal != null) {
            return employeeService.convertToDTO(employeeService.findEmployeeByEmail(principal.getName()).orElseThrow());
        }
        return null;
    }

}
