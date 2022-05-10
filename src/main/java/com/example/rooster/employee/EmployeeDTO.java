package com.example.rooster.employee;

import com.example.rooster.team.Team;

public class EmployeeDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Team team;

    private double hoursPerWeek;

    private double balanceHours;

    private double breakTime;

    private Role role;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String firstName, String lastName, String email, String password, Team team, double hoursPerWeek, double balanceHours, double breakTime, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.team = team;
        this.hoursPerWeek = hoursPerWeek;
        this.balanceHours = balanceHours;
        this.breakTime = breakTime;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public double getBalanceHours() {
        return balanceHours;
    }

    public void setBalanceHours(double balanceHours) {
        this.balanceHours = balanceHours;
    }

    public double getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(double breakTime) {
        this.breakTime = breakTime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
