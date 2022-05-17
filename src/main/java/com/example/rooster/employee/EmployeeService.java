package com.example.rooster.employee;

import com.example.rooster.period.Period;
import com.example.rooster.period.PeriodRepository;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final TeamService teamService;

    private final PeriodRepository periodRepository;

    public EmployeeService(EmployeeRepository employeeRepository, TeamService teamService, PeriodRepository periodRepository) {
        this.employeeRepository = employeeRepository;
        this.teamService = teamService;
        this.periodRepository = periodRepository;
    }

    public Employee getEmployee(long id) {
        return this.employeeRepository.findEmployeeById(id);
    }

    private Role findRole(int id) {
        return Arrays.stream(Role.values()).filter(role -> role.ordinal() == id).findFirst().orElse(null);
    }

    public Employee convertToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setTeam(this.teamService.getTeam(employeeDTO.getTeam()));
        employee.setHoursPerWeek(employeeDTO.getHoursPerWeek());
        employee.setBalanceHours(employeeDTO.getBalanceHours());
        employee.setBreakTime(employeeDTO.getBreakTime());
        employee.setRole(Role.valueOf(employeeDTO.getRole()));
        return employee;
    }

    public EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setTeam(employee.getTeam().getId());
        employeeDTO.setHoursPerWeek(employee.getHoursPerWeek());
        employeeDTO.setBalanceHours(employee.getBalanceHours());
        employeeDTO.setBreakTime(employee.getBreakTime());
        employeeDTO.setRole(employee.getRole().name());
        return employeeDTO;
    }


    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }

    public List<EmployeeDTO> getEmployeesAsDTO() {
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

    public Optional<Employee> findEmployeeByEmail(String email){
        return this.employeeRepository.findEmployeeByEmail(email);
    }

    public Employee getEmployeeById(long id) {

        return this.employeeRepository.findEmployeeById(id);
    }

    public Employee deleteEmployeeById(long id) {
        Employee employee = this.getEmployeeById(id);
        this.employeeRepository.delete(employee);
        return employee;
    }

    public void deletePeriodsOfEmployee(Employee employee) {
        List<Period> periodsList;
        periodsList = this.periodRepository.findAllByEmployee(employee);
        this.periodRepository.deleteAll(periodsList);
    }

    public List<EmployeeDTO> getEmployeesByTeamIdAsDTO(long teamId) {
        List<Employee> employees = this.employeeRepository.findAllByTeamId(teamId);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(employee -> employeeDTOS.add(this.convertToDTO(employee)));
        return employeeDTOS;
    }
}
