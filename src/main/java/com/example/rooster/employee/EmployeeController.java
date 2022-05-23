package com.example.rooster.employee;

import com.example.rooster.security.user.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees/")
public class EmployeeController {

    /**
     * Controller class responsible for
     * processing incoming REST API requests, preparing a model,
     * and returning the view to be rendered as a response
     */

    private final EmployeeService employeeService;
    private final UserController userController;

    public EmployeeController(EmployeeService employeeService, UserController userController) {
        this.employeeService = employeeService;
        this.userController = userController;
    }

    /**
     * select and get a specific employee based on the ID
     * @param id with data type long for a specific employee
     * @return value is a specific employee as DTO
     */
    @GetMapping("/get/{id}")
    public EmployeeDTO getEmployee(@PathVariable long id) {
        return this.employeeService.convertToDTO(employeeService.getEmployeeById(id));
    }

    /**
     * with this method we show a list of all employees
     * @return value is a list of all employees as DTO
     */
    @GetMapping("/get_all")
    List<EmployeeDTO> getEmployees() {
        return employeeService.getEmployeesAsDTO();
    }

    /**
     * to get and show a list of employees of a specific team via team ID
     * @param teamId with data type long for a specific team
     * @return value is a list of employees of a specific team as DTO
     */
    @GetMapping("/get_all/{teamId}")
    List<EmployeeDTO> getEmployeesByTeamIdAsDTO(@PathVariable long teamId) {
        return employeeService.getEmployeesByTeamIdAsDTO(teamId);
    }

    /**
     * the input parameter from frontend is saved in database and returned to frontend
     * @param employeeDTO is received from frontend as parameter
     * @return saved employee is returned to frontend as DTO
     */
    @PostMapping("/new")
    public EmployeeDTO addNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = employeeService.convertToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.setEmployee(newEmployee);
        this.userController.sendPassword(savedEmployee.getId()); //Send user a password after adding him/her to the database
        return employeeService.convertToDTO(savedEmployee);
    }

    /**
     * used the addNewEmployee() to update/edit the employee
     * @param employeeDTO is received from frontend as parameter
     * @return value is an employeeDTO
     */
    @PostMapping("/edit")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return this.addNewEmployee(employeeDTO);
    }

    /**
     * select a specific employee via ID and remove the periods and employee him/herself from database
     * @param id with data type long for a specific employee
     * @return value is http status ok with the message "Successfully Deleted"
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        employeeService.deletePeriodsOfEmployee(employeeService.getEmployeeById(id));
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

}
