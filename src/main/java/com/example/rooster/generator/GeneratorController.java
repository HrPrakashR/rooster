package com.example.rooster.generator;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.period.Period;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.PeriodService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/periods/")
public class GeneratorController {

    private final PeriodService periodService;

    private final EmployeeService employeeService;

    private final TeamService teamService;
    private final List<Period> roster = new ArrayList<Period>();
    private List<Employee> employees;
    private Team team;
    private int month;
    private int year;


    public GeneratorController(PeriodService periodService, EmployeeService employeeService, TeamService teamService) {
        this.periodService = periodService;
        this.employeeService = employeeService;
        this.teamService = teamService;
    }


    @GetMapping("/get")
    public List<PeriodDTO> getAll() {
        this.generate(1, 5, 2022);
        return periodService.getPeriodsAsDTO();
    }


    public List<Period> generate(long teamId, int month, int year) {
        this.setTeam(this.teamService.getTeam(teamId));
        this.setDate(month, year);
        this.roster.addAll(this.periodService.getPeriodsPerTeamAndTimeInterval(this.team, getDate(false), getDate(true)));

        return this.roster;
    }

    private Date getDate(boolean lastDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        int day = lastDay ? calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : 1;
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public void setTeam(Team team) {
        this.team = team;
        this.employees = this.employeeService.getEmployees(team);
    }

    public void setDate(int month, int year) {
        this.month = month;
        this.year = year;
    }
}
