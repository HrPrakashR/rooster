package com.example.rooster;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeController;
import com.example.rooster.employee.EmployeeRepository;
import com.example.rooster.security.auth.AuthService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SetupComponent implements ApplicationListener<ApplicationReadyEvent> {

    private final EmployeeRepository employeeRepository;
    private final EmployeeController employeeController;
    private final AuthService authService;

    public SetupComponent(EmployeeRepository employeeRepository, EmployeeController employeeController, AuthService authService) {
        this.employeeRepository = employeeRepository;
        this.employeeController = employeeController;
        this.authService = authService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (this.employeeRepository.count() < 10) { // only create users if the database is empty
            Employee bob = new Employee();
            bob.setEmail("bob@gmail.com");
            bob.setPassword("abcdefg");
            this.employeeController.addNewEmployee(bob);
        }
    }
}

