package com.example.rooster.employee;

public class EmployeeConverter {

    /**
     * this class method helps to convert an employee entity to a DTO object
     * @param employee is an entity
     * @return value is an employee DTO
     */
    public static EmployeeDTO convertToDTO(Employee employee) {
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
