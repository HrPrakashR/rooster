package com.example.rooster.employee;

import com.example.rooster.team.Team;

public class EmployeeDTO {

    private long id;
    private String firstName;

    private String lastName;

    private String email;

    private long team;

    private double hoursPerWeek;

    private double balanceHours;

    private double breakTime;

    private int role;

    public EmployeeDTO() {
    }

    public EmployeeDTO(long id, String firstName, String lastName, String email, long team, double hoursPerWeek, double balanceHours, double breakTime, int role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.team = team;
        this.hoursPerWeek = hoursPerWeek;
        this.balanceHours = balanceHours;
        this.breakTime = breakTime;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getTeam() {
        return team;
    }

    public void setTeam(long team) {
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
