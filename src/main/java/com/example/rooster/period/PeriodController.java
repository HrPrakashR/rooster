package com.example.rooster.period;

import com.example.rooster.employee.EmployeeService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/periods/")
public class PeriodController {

    private final PeriodService periodService;
    private final EmployeeService employeeService;
    private final TeamService teamService;

    public PeriodController(PeriodService periodService, EmployeeService employeeService, TeamService teamService) {
        this.periodService = periodService;
        this.employeeService = employeeService;
        this.teamService = teamService;
    }

    //Showing a certain request
    @GetMapping("/get/{id}")
    public PeriodDTO showPeriodRequest(@PathVariable long id) {
        return periodService.convertToPeriodDTO(periodService.getPeriod(id));
    }

    //Showing requests of a certain employee
    @GetMapping("/employee/{employeeId}/{year}/{month}")
    public List<PeriodDTO> showByEmployeeAndBetween(@PathVariable long employeeId, @PathVariable int year, @PathVariable int month) {

        Calendar from = Calendar.getInstance();
        from.set(Calendar.YEAR, year);
        from.set(Calendar.MONTH, month);
        from.set(Calendar.DAY_OF_MONTH, from.getActualMinimum(Calendar.DAY_OF_MONTH));

        Calendar to = Calendar.getInstance();
        to.set(Calendar.YEAR, year);
        to.set(Calendar.MONTH, month);
        to.set(Calendar.DAY_OF_MONTH, from.getActualMaximum(Calendar.DAY_OF_MONTH));

        return periodService.getPeriodsByEmployeeAndBetween(employeeService.getEmployeeById(employeeId), from.getTime(), to.getTime());
    }

    @GetMapping("/employee/workingHour/{employeeId}/{year}/{month}")
    public Double returnWorkingHours(@PathVariable long employeeId, @PathVariable int year, @PathVariable int month) {

        Calendar from = Calendar.getInstance();
        from.set(Calendar.YEAR, year);
        from.set(Calendar.MONTH, month);
        from.set(Calendar.DAY_OF_MONTH, from.getActualMinimum(Calendar.DAY_OF_MONTH));

        Calendar to = Calendar.getInstance();
        to.set(Calendar.YEAR, year);
        to.set(Calendar.MONTH, month);
        to.set(Calendar.DAY_OF_MONTH, from.getActualMaximum(Calendar.DAY_OF_MONTH));

        List<PeriodDTO> workingHours = periodService.getPeriodsByEmployeeAndBetween(employeeService.getEmployeeById(employeeId), from.getTime(), to.getTime());

        if (workingHours.isEmpty()) {
            return 0.0;
        }

        double dailyWorkingHours = employeeService.getEmployeeById(employeeId).getHoursPerWeek() / 5;

        double wh = workingHours
                .stream()
                .filter(period -> period.getEmployee() == employeeId)
                .filter(period -> Objects.equals(period.getPurpose(), Purpose.CONFIRMED_VACATION.name()) || Objects.equals(period.getPurpose(), Purpose.SICK_LEAVE.name()))
                .mapToDouble(period -> dailyWorkingHours)
                .sum();

        wh += workingHours
                .stream()
                .filter(period -> period.getEmployee() == employeeId)
                .filter(period -> Objects.equals(period.getPurpose(), Purpose.WORKING_HOURS.name()))
                .mapToDouble(period -> periodService.calculateHours(period.getDateFrom(), period.getDateTo()))
                .sum();

        return wh;
    }

    //Showing all of the periods
    @GetMapping("/get_all")
    public List<PeriodDTO> getAll() {
        return periodService.getPeriodsAsDTO();
    }

    //Creating a new period request. Returns all period requests of an employee.
    @PostMapping("/new")
    public List<PeriodDTO> submitPeriodRequest(@RequestBody PeriodDTO periodDTO) {
        Period period = periodService.convertToPeriod(periodDTO);
        periodService.addPeriod(period);
        List<Period> periods = periodService.getPeriodsByEmployee(period.getEmployee());
        List<PeriodDTO> periodDTOs = new ArrayList<>();
        periods.forEach(p -> periodDTOs.add(periodService.convertToPeriodDTO(p)));
        return periodDTOs;
    }

    //Deleting a certain request. Returns the list of remaining requests of the employee.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePeriodRequest(@PathVariable long id) {
        periodService.deletePeriod(periodService.getPeriod(id));
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    @PostMapping("/edit")
    public PeriodDTO updatePeriod(@RequestBody PeriodDTO periodDTO) {
        Period newPeriod = periodService.convertToPeriod(periodDTO);
        Period savedPeriod = periodService.addPeriod(newPeriod);
        return periodService.convertToPeriodDTO(savedPeriod);
    }

    //Displaying the periods of a certain team in a certain time interval
    @PostMapping("/time_plan/{teamId}")
    public List<PeriodDTO> showPeriodsPerTeamAndTimeInterval(@PathVariable long teamId,
                                                             @RequestBody DateDTO dateDTO) {
        List<PeriodDTO> periodDTOs = new ArrayList<>();
        Team team = teamService.getTeam(teamId);
        Date dateFrom = dateDTO.getDateFrom();
        Date dateTo = dateDTO.getDateTo();
        List<Period> periods = periodService.getPeriodsPerTeamAndTimeInterval(team, dateFrom, dateTo);
        periods.forEach(p -> periodDTOs.add(periodService.convertToPeriodDTO(p)));
        return periodDTOs;
    }

}
