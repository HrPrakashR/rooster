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

    public Employee getEmployee(long id) {
        return this.employeeRepository.findEmployeeById(id);
    }

    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }

    public List<Employee> getEmployees(Team team) {
        return this.employeeRepository.findAllByTeam(team);
    }

    public List<Employee> getEmployee(Role role) {
        return this.employeeRepository.findAllByRole(role);
    }

    public Employee setEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public void deleteEmployeeById(long id) {
        this.employeeRepository.deleteById(id);
    }

    public void updateFirstName(long id, String firstName) {
        this.employeeRepository.updateFirstName(id, firstName);
    }
}
