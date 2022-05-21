package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.helpers.DateWorker;
import com.example.rooster.helpers.GeneratorWorker;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return periodService.getPeriodsByEmployeeAndBetween(
                employeeService.getEmployeeById(employeeId),
                DateWorker.getDateObject(year, month, false),
                DateWorker.getDateObject(year, month, true));
    }

    @GetMapping("/employee/workingHour/{employeeId}/{year}/{month}")
    public Double returnWorkingHours(@PathVariable long employeeId, @PathVariable int year, @PathVariable int month) {


        List<PeriodDTO> workingHours = periodService.getPeriodsByEmployeeAndBetween(
                employeeService.getEmployeeById(employeeId),
                DateWorker.getDateObject(year, month, false),
                DateWorker.getDateObject(year, month, true));

        if (workingHours.isEmpty()) {
            return 0.0;
        }

        double dailyWorkingHours = employeeService.getEmployeeById(employeeId).getHoursPerWeek() / 5;

        return DateWorker.getWorkingTime(workingHours, dailyWorkingHours);
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

    @GetMapping("/generateNewRoster/{teamId}/{year}/{month}")
    public List<PeriodDTO> getGeneratedRoster(@PathVariable long teamId, @PathVariable int year, @PathVariable int month) {

        // fetching necessary information
        List<PeriodDTO> generatedPlan;
        Team team = this.teamService.getTeam(teamId);
        List<Employee> employees = this.employeeService.getEmployees(team);
        List<Calendar> days = DateWorker.getAllDaysOfMonth(year, month);

        // adding the predefined periods to the generated plan
        List<Period> predefinedPeriods = this.periodService
                .getPeriodsPerTeamAndTimeInterval(
                        team,
                        DateWorker.getDateObject(year, month, false),
                        DateWorker.getDateObject(year, month, true));

        generatedPlan = predefinedPeriods.stream().filter(period ->
                        Stream.of(Purpose.WORKING_HOURS, Purpose.CONFIRMED_VACATION, Purpose.ABSENCE, Purpose.SICK_LEAVE)
                                .anyMatch(purpose ->
                                        period.getPurpose()
                                                .equals(purpose)))
                .map(periodService::convertToPeriodDTO)
                .collect(Collectors.toList());

/*        // iterating through employees
        employees.forEach(employee -> {
            // check if they have enough time to work at another day
            if (DateWorker.getWorkingTime(
                    GeneratorWorker.filterByEmployee(generatedPlan, employee.getId()),
                    GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())
            ) <= GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())
            ) {
                // iterate through days
                days.forEach(day -> {

                    // initialize values WORK AND CALCULATE HERE
                    Purpose purpose = Purpose.WORKING_HOURS;
                    int hourFrom = 0;
                    int hourTo = 0;
                    int minuteFrom = 0;
                    int minuteTo = 0;


                    // add working times
                    generatedPlan.add(GeneratorWorker.createDTO(
                            day,
                            hourFrom,
                            minuteFrom,
                            hourTo,
                            minuteTo,
                            employee.getId(),
                            purpose.name()));
                });
            }
        });*/

        return generatedPlan;
    }

}
