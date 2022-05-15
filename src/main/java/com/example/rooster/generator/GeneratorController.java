package com.example.rooster.generator;

import com.example.rooster.date_worker.DateWorker;
import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.period.Period;
import com.example.rooster.period.PeriodService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/periods/")
public class GeneratorController {

    private final PeriodService periodService;

    private final EmployeeService employeeService;

    private final TeamService teamService;
    private final List<Period> roster = new ArrayList<>();
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
    public List<Period> getAll() {
        this.generate(2, 5, 2022);
        return this.roster;
    }

    public List<Period> generate(long teamId, int month, int year) {
        this.setTeam(this.teamService.getTeam(teamId));
        this.setDate(month, year);
        this.roster.addAll(
                this.periodService
                        .getPeriodsPerTeamAndTimeInterval(
                                this.team,
                                DateWorker.getDate(false, this.year, this.month),
                                DateWorker.getDate(true, this.year, this.month)
                        )
        );
        return this.roster;
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
