package com.example.rooster.user;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

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
        Employee employee = this.employeeService.getEmployeeById(userId);
        String password = this.getPassword();
        employee.setPassword(password);
        this.employeeService.setEmployee(employee);
        this.sendPassword(employee.getEmail(), password);

        return new ResponseEntity<>("Successfully sent", HttpStatus.OK);
    }

    private void sendPassword(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javamailer545@gmail.com");
        message.setTo(to);
        message.setSubject("Your new Password");
        message.setText("New Password: " + password);
        this.emailSender.send(message);
    }

    private String getPassword() {
        int length = (int) (Math.random() * 12) + 10;

        // Code from https://www.tutorialspoint.com/Generating-password-in-Java
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$+-*";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }

        return new String(password);
    }
}
