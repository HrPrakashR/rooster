package com.example.rooster.generator;

import com.example.rooster.date_worker.DateWorker;
import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.period.DateDTO;
import com.example.rooster.period.Period;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.PeriodService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/generator/")
public class GeneratorController {

    private final PeriodService periodService;

    private final EmployeeService employeeService;

    private final TeamService teamService;

    // returns the generated roster
    private final List<PeriodDTO> roster = new ArrayList<>();

    // includes the predefined periods (do not overwrite or delete!)
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
        this.setTeam(this.teamService.getTeam(teamId));
        this.setDate(month, year);
        this.setPredefinedRoster();
        this.setEmployees();
        return this.roster;
    }

    // ONLY FOR TESTING: REMOVE LATER
    @GetMapping("/test/{teamId}/{year}/{month}")
    public List<DateDTO> getWorkingPeriodsNow(@PathVariable long teamId, @PathVariable int year, @PathVariable int month) {
        this.setTeam(this.teamService.getTeam(teamId));
        this.setDate(month, year);
        this.setPredefinedRoster();
        this.setEmployees();
        return this.getWorkingPeriods();
    }

    private void setPredefinedRoster(){
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

    private void setTeam(Team team) {
        this.team = team;
        this.employees = this.employeeService.getEmployees(team);
    }

    private void setDate(int month, int year) {
        this.month = month;
        this.year = year;
    }

    private void setEmployees(){
        this.employees = this.employeeService.getEmployees(this.team);
    }

    private List<DateDTO> getWorkingPeriods(){
        List<DateDTO> workingPeriods = new ArrayList<>();
        List<Calendar> allDays = DateWorker.getAllDaysOfMonth(this.year, this.month);

        // if times are the same, at that day is no working day
        boolean monday = team.getMondayFrom() == team.getMondayTo();
        boolean tuesday = team.getTuesdayFrom() == team.getTuesdayTo();
        boolean wednesday = team.getWednesdayFrom() == team.getWednesdayTo();
        boolean thursday = team.getThursdayFrom() == team.getThursdayTo();
        boolean friday = team.getFridayFrom() == team.getFridayTo();
        boolean saturday = team.getSaturdayFrom() == team.getSaturdayTo();
        boolean sunday = team.getSundayFrom() == team.getSundayTo();

        List<Calendar> workingTime = DateWorker.removeDays(allDays, monday, tuesday, wednesday, thursday, friday, saturday, sunday);

        workingTime.forEach(day -> workingPeriods.add(getDateDTOForWorkingPeriod(day)));

        return workingPeriods;
    }

    private DateDTO getDateDTOForWorkingPeriod(Calendar date){
        Date getFrom;
        Date getTo;

        switch (date.get(Calendar.DAY_OF_WEEK)) {
            case 1 -> {
                getFrom = team.getSundayFrom();
                getTo = team.getSundayTo();
            }
            case 2 -> {
                getFrom = team.getMondayFrom();
                getTo = team.getMondayTo();
            }
            case 3 -> {
                getFrom = team.getTuesdayFrom();
                getTo = team.getTuesdayTo();
            }
            case 4 -> {
                getFrom = team.getWednesdayFrom();
                getTo = team.getWednesdayTo();
            }
            case 5 -> {
                getFrom = team.getThursdayFrom();
                getTo = team.getThursdayTo();
            }
            case 6 -> {
                getFrom = team.getFridayFrom();
                getTo = team.getFridayTo();
            }
            case 7 -> {
                getFrom = team.getSaturdayFrom();
                getTo = team.getSaturdayTo();
            }
            default -> throw new IllegalStateException("Unexpected value: " + date.get(Calendar.DAY_OF_WEEK));
        }

        return new DateDTO(
                DateWorker.getDateObject(
                        DateWorker.getCalendarObject(getFrom).get(Calendar.SECOND),
                        DateWorker.getCalendarObject(getFrom).get(Calendar.MINUTE),
                        DateWorker.getCalendarObject(getFrom).get(Calendar.HOUR_OF_DAY),
                        date.get(Calendar.DAY_OF_MONTH),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.YEAR)
                ),
                DateWorker.getDateObject(
                        DateWorker.getCalendarObject(getTo).get(Calendar.SECOND),
                        DateWorker.getCalendarObject(getTo).get(Calendar.MINUTE),
                        DateWorker.getCalendarObject(getTo).get(Calendar.HOUR_OF_DAY),
                        date.get(Calendar.DAY_OF_MONTH),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.YEAR)
                )
        );
    }
}
