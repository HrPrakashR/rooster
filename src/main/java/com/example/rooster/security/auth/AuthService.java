package com.example.rooster.security.auth;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;


@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;
//    private final UserService userService;

    public AuthService(PasswordEncoder passwordEncoder, EmployeeService employeeService) {
        this.passwordEncoder = passwordEncoder;
        this.employeeService = employeeService;
    }

    protected Employee login(String email, String password) {
        Employee employee = this.employeeService.findEmployeeByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (passwordEncoder.matches(password, employee.getPassword())) {
            return employee;
        } else {
            throw new BadCredentialsException(format("Wrong password for user '%s'.", email));
        }
    }
}
