package com.example.rooster.employee;

public class EmployeeConverter {

    public static EmployeeDTO convertToDTO(Employee employee){
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

}
