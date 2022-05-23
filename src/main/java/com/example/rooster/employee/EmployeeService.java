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

/**
 * service class is used to write business logic in a different layer, separated from controller class.
 */
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

    /**
     * used to find a specific employee via ID
     * @param id is a unique long number
     * @return value is an employee entity from employee repository
     */
    public Employee getEmployee(long id) {
        return this.employeeRepository.findEmployeeById(id);
    }

    private Role findRole(int id) {
        return Arrays.stream(Role.values()).filter(role -> role.ordinal() == id).findFirst().orElse(null);
    }

    /**
     * this method converts an employeeDTO to an employee entity
     * @param employeeDTO
     * @return is a converted entity of employee
     */
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

    /**
     * used to convert an employee to DTO
     * @param employee is an entity object from employee
     * @return value is an employeeDTO object
     */
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

    /**
     * this method shows a list of all employees in database
     * @return value is a list of all employee objects saved in database
     */
    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }

    /**
     * used to convert and save each employee to DTO object in a list
     * @return is a list of employeeDTO objects
     */
    public List<EmployeeDTO> getEmployeesAsDTO() {
        List<Employee> employees = this.getEmployees();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(employee -> employeeDTOS.add(this.convertToDTO(employee)));
        return employeeDTOS;
    }

    /**
     * to get a list of employee objects via team
     * @param team is an object of team to find all its
     * @return returns a list of employee objects
     */
    public List<Employee> getEmployees(Team team) {
        return this.employeeRepository.findAllByTeam(team);
    }

    /**
     * returns a list of employee objects found by role
     * @param role
     * @return value is a list of employees
     */
    public List<Employee> getEmployee(Role role) {
        return this.employeeRepository.findAllByRole(role);
    }

    /**
     * used to save an employee object in database
     * @param employee is an employee object
     * @return returns a saved employee object
     */
    public Employee setEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    /**
     * returns an Optional employee object via email that either exists or not
     * @param email is a string datatype for employees email
     * @return value is an optional of employee
     */
    public Optional<Employee> findEmployeeByEmail(String email) {
        return this.employeeRepository.findEmployeeByEmail(email);
    }

    /**
     * used to return a specific employee object found by ID
     * @param id is an unique long number
     * @return returns an employee entity
     */
    public Employee getEmployeeById(long id) {

        return this.employeeRepository.findEmployeeById(id);
    }

    /**
     * used to find and remove an employee object via ID, returns void
     * @param id unique long number
     */
    public void deleteEmployeeById(long id) {
        Employee employee = this.getEmployeeById(id);
        this.employeeRepository.delete(employee);
    }

    /**
     * returns void an removes all periods of a specific employee
     * @param employee
     */
    public void deletePeriodsOfEmployee(Employee employee) {
        List<Period> periodsList;
        periodsList = this.periodRepository.findAllByEmployee(employee);
        this.periodRepository.deleteAll(periodsList);
    }

    /**
     * used to return a list of DTO objects of employee found by team id
     * @param teamId is an unique long number
     * @return returns a list of employee DTOs
     */
    public List<EmployeeDTO> getEmployeesByTeamIdAsDTO(long teamId) {
        List<Employee> employees = this.employeeRepository.findAllByTeamId(teamId);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(employee -> employeeDTOS.add(this.convertToDTO(employee)));
        return employeeDTOS;
    }
}
