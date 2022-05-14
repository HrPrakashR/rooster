package com.example.rooster.user;

import com.example.rooster.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final EmployeeService employeeService;
    private final JavaMailSender emailSender;

    public UserController(EmployeeService employeeService, JavaMailSender emailSender) {
        this.employeeService = employeeService;
        this.emailSender = emailSender;
    }

    @GetMapping("/sendPassword/{userId}")
    public ResponseEntity<String> sendPassword(@PathVariable long userId) {
        String email = this.employeeService.getEmployeeById(userId).getEmail();
        this.sendPassword(email);
        return new ResponseEntity<>("Successfully sent", HttpStatus.OK);
    }

    public void sendPassword(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javamailer545@gmail.com");
        message.setTo(to);
        message.setSubject("Your new Password");
        message.setText("New Password: "+ this.getPassword());
        emailSender.send(message);
    }

    private String getPassword() {
        return "12345";
    }
}
