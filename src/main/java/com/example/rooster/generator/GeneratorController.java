package com.example.rooster.generator;

import com.example.rooster.date_worker.DateWorker;
import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.period.Period;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.PeriodService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/generator/")
public class GeneratorController {

    private final PeriodService periodService;

    private final EmployeeService employeeService;

    private final TeamService teamService;
    private final List<PeriodDTO> roster = new ArrayList<>();

    private final List<Period> rosterPredefined = new ArrayList<>();
    private List<Employee> employees;
    private Team team;
    private int month;
    private int year;

    public GeneratorController(PeriodService periodService, EmployeeService employeeService, TeamService teamService) {
        this.periodService = periodService;
        this.employeeService = employeeService;
        this.teamService = teamService;
    }

    @GetMapping("/{teamId}/{year}/{month}")
    public List<PeriodDTO> getAll(@PathVariable long teamId, @PathVariable int year, @PathVariable int month) {
        this.generate(teamId, month, year);
        return this.roster;
    }

    public void generate(long teamId, int month, int year) {
        this.setTeam(this.teamService.getTeam(teamId));
        this.setDate(month, year);

        this.rosterPredefined.addAll(
                this.periodService
                        .getPeriodsPerTeamAndTimeInterval(
                                this.team,
                                DateWorker.getDate(false, this.year, this.month),
                                DateWorker.getDate(true, this.year, this.month)
                        )
        );

        this.rosterPredefined
                .forEach(period ->
                        roster.add(
                                new PeriodDTO(
                                        period.getId(),
                                        period.getPurpose().ordinal(),
                                        period.getDateFrom(),
                                        period.getDateTo(),
                                        period.getEmployee().getId()
                                )));
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
