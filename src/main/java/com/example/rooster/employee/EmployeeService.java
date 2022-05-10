package com.example.rooster.employee;

import com.example.rooster.team.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findEmployeeById(long id) {
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAllEmployeesByTeam (Team team) {
        return employeeRepository.findAllByTeam(team);
    }

    public List<Employee> findAllEmployeesByRole (Role role) {
        return employeeRepository.findAllByRole(role);
    }

    public Employee addNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }
}
