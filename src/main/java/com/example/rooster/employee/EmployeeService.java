package com.example.rooster.employee;

import com.example.rooster.team.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Employee convertToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPassword(employeeDTO.getPassword());
        employee.setTeam(employeeDTO.getTeam());
        employee.setHoursPerWeek(employeeDTO.getHoursPerWeek());
        employee.setBalanceHours(employeeDTO.getBalanceHours());
        employee.setBreakTime(employeeDTO.getBreakTime());
        employee.setRole(employeeDTO.getRole());
        return employee;
    }

    public EmployeeDTO convertToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setTeam(employee.getTeam());
        employeeDTO.setHoursPerWeek(employee.getHoursPerWeek());
        employeeDTO.setBalanceHours(employee.getBalanceHours());
        employeeDTO.setBreakTime(employee.getBreakTime());
        employeeDTO.setRole(employee.getRole());
        return employeeDTO;
    }



    public List<Employee> getEmployees() {

        return this.employeeRepository.findAll();
    }

    public List<EmployeeDTO> getEmployeesAsDTO(){
        List<Employee> employees = this.getEmployees();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(employee -> employeeDTOS.add(this.convertToDTO(employee)));
        return employeeDTOS;
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

    public Employee getEmployeeByEmail(String email) {

        return this.employeeRepository.findEmployeeByEmail(email);
    }

    public Employee deleteEmployeeByEmail(String email ) {
        Employee employee = this.getEmployeeByEmail(email);
        this.employeeRepository.delete(employee);
        return employee;
    }

}
