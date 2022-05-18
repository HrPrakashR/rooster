package com.example.rooster.security.auth;

import com.example.rooster.employee.EmployeeConverter;
import com.example.rooster.employee.EmployeeDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    private final AuthService authService;

    public Authcontroller(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public EmployeeDTO login(@RequestBody UserLogin userLogin) {
        return EmployeeConverter.convertToDTO(this.authService.login(userLogin.getUsername(), userLogin.getPassword()));
    }

    private static class UserLogin{

        private String email;
        private String password;

        public String getUsername() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
