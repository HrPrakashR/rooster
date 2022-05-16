package com.example.rooster.user;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.IntStream;

@Service
public class UserService {

    private final JavaMailSender emailSender;

    public UserService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendPassword(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javamailer545@gmail.com");
        message.setTo(to);
        message.setSubject("Your new Password");
        message.setText("New Password: " + password);
        this.emailSender.send(message);
    }

    public String getPassword() {
        int length = (int) (Math.random() * 12) + 10;

        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String specialCharacters = "!@#$+-*";
        String numbers = "1234567890";
        String combinedChars = letters + letters.toLowerCase() + specialCharacters + numbers;

        Random random = new Random();
        char[] password = new char[length];

        password[0] = letters.toLowerCase().charAt(random.nextInt(letters.toLowerCase().length()));
        password[1] = letters.charAt(random.nextInt(letters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        IntStream.range(4, length).forEach(i -> password[i] = combinedChars.charAt(random.nextInt(combinedChars.length())));

        return new String(password);
    }
}
