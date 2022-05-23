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
import java.util.stream.Stream;

/**
 * Rest Controller for the entity Period.
 * Conducts the get, post and delete methods for periods
 */
@RestController
@RequestMapping("/api/periods/")
public class PeriodController {

    private final PeriodService periodService;
    private final EmployeeService employeeService;
    private final TeamService teamService;

    private List<PeriodDTO> generatedPlan;

    public PeriodController(PeriodService periodService, EmployeeService employeeService, TeamService teamService) {
        this.periodService = periodService;
        this.employeeService = employeeService;
        this.teamService = teamService;
    }

    /**
     * Method to get a certain period
     * @param id gets the period id as parameter (long)
     * @return the requested period as DTO
     */
    //Showing a certain request
    @GetMapping("/get/{id}")
    public PeriodDTO showPeriodRequest(@PathVariable long id) {
        return periodService.convertToPeriodDTO(periodService.getPeriod(id));
    }

    /**
     * Method to get periods of a certain employee in a given month
     * @param employeeId as a path parameter, long
     * @param year as a path parameter, int
     * @param month as a path parameter, int
     * @return the list of PeriodDTOs for the given employee and month
     */
    @GetMapping("/employee/{employeeId}/{year}/{month}")
    public List<PeriodDTO> showByEmployeeAndBetween(@PathVariable long employeeId, @PathVariable int year, @PathVariable int month) {
        return periodService.getPeriodsByEmployeeAndBetween(
                employeeService.getEmployeeById(employeeId),
                DateWorker.getDateObject(year, month, false),
                DateWorker.getDateObject(year, month, true));
    }

    /**
     * Method to get total working hours of an employee in a given month
     * @param employeeId as a path parameter, long
     * @param year as a path parameter, int
     * @param month as a path parameter, int
     * @return total working of an employee in the given month as double
     */
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

        return GeneratorWorker.getWorkingTime(workingHours, dailyWorkingHours);
    }

    /**
     * Method to receive all Periods as a PeriodDTO list
     * @return the list of all periods as DTOs
     */
    @GetMapping("/get_all")
    public List<PeriodDTO> getAll() {
        return periodService.getPeriodsAsDTO();
    }

    /**
     * Method to save a new period entry
     * @param periodDTO receives a periodDTO as parameter
     * @return the periodDTO after saving it as a period object
     */
    @PostMapping("/new")
    public List<PeriodDTO> submitPeriodRequest(@RequestBody PeriodDTO periodDTO) {
        Period period = periodService.convertToPeriod(periodDTO);
        periodService.addPeriod(period);
        List<Period> periods = periodService.getPeriodsByEmployee(period.getEmployee());
        List<PeriodDTO> periodDTOs = new ArrayList<>();
        periods.forEach(p -> periodDTOs.add(periodService.convertToPeriodDTO(p)));
        return periodDTOs;
    }

    /**
     * Method to save a list of PeriodDTOs as a schedule
     * First deletes the old plan in given month, then saves the new one
     * @param periodDTOs Receives the list of DTOs as parameter
     */
    @PostMapping("/saveNewRoster")
    public void saveNewRoster(@RequestBody PeriodDTO[] periodDTOs) {
        Team periodTeam = this.employeeService.getEmployee(periodDTOs[periodDTOs.length/2].getEmployee()).getTeam();
        Period firstPeriod = this.periodService.convertToPeriod(periodDTOs[0]);
        Date date = firstPeriod.getDateTo();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int periodMonth = cal.get(Calendar.MONTH);
        int periodYear = cal.get(Calendar.YEAR);
        this.periodService.deleteAllPeriodsByTeamAndByMonthAndByYear(periodTeam, periodMonth, periodYear);
        for (PeriodDTO periodDTO : periodDTOs) {
            this.submitPeriodRequest(periodDTO);
        }
    }

    /**
     * Method to delete a certain period
     * @param id of the period, long
     * @return Response Entity with HTTP Status OK
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePeriodRequest(@PathVariable long id) {
        periodService.deletePeriod(periodService.getPeriod(id));
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    /**
     * Method to save an edited period entry
     * @param periodDTO receives a DTO as parameter
     * @return the saves period as periodDTO
     */
    @PostMapping("/edit")
    public PeriodDTO updatePeriod(@RequestBody PeriodDTO periodDTO) {
        Period newPeriod = periodService.convertToPeriod(periodDTO);
        Period savedPeriod = periodService.addPeriod(newPeriod);
        return periodService.convertToPeriodDTO(savedPeriod);
    }

    /**
     * Method to show the list of periods for a certain time in a given time interval
     * @param teamId as path variable, long
     * @param dateDTO the first and last days of said time interval as parameter
     * @return the list of periods of a certain team in a given time interval as DTOs
     */
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

    /**
     * Method to calculate and return the total working hours of an employee
     * during a certain schedule
     * @param employeeId as path variable, long
     * @return total working hours of the employee, double
     */
    @GetMapping("/generatedRoster/total/{employeeId}")
    public Double getTotalForGenerated(@PathVariable int employeeId) {
        Employee employee = this.employeeService.getEmployee(employeeId);
        return GeneratorWorker.getTotalWorkingHours(this.generatedPlan, employee, employee.getTeam());
    }

    /**
     * Method to get the generated roster for a certain team/month/year
     * @param teamId as path variable, long
     * @param year as path variable, int
     * @param month as path variable, int
     * @return generated roster plan for the given team/month/year as a list of DTOs
     */
    @GetMapping("/generateNewRoster/{teamId}/{year}/{month}")
    public List<PeriodDTO> getGeneratedRoster(@PathVariable long teamId, @PathVariable int year, @PathVariable int month) {

        // fetching necessary information
        Team team = this.teamService.getTeam(teamId);
        List<Employee> employees = this.employeeService.getEmployees(team);

        // adding the predefined periods to the generated plan
        List<Period> predefinedPeriods = this.periodService
                .getPeriodsPerTeamAndTimeInterval(
                        team,
                        DateWorker.getDateObject(year, month, false),
                        DateWorker.getDateObject(year, month, true));

        List<PeriodDTO> predefinedRequests = predefinedPeriods.stream().filter(period ->
                Stream.of(Purpose.VACATION_REQUEST, Purpose.FREE_TIME_REQUEST, Purpose.WORKING_HOUR_REQUEST)
                        .anyMatch(purpose -> period.getPurpose().equals(purpose)
                )).map(periodService::convertToPeriodDTO).toList();

        List<PeriodDTO> predefinedPlan = predefinedPeriods.stream().filter(period ->
                        Stream.of(Purpose.WORKING_HOURS, Purpose.CONFIRMED_VACATION, Purpose.ABSENCE, Purpose.SICK_LEAVE)
                                .anyMatch(purpose -> period.getPurpose().equals(purpose)
                                        && GeneratorWorker.checkIfTeamWorksAtDay(team, DateWorker.convertDateToCalendarObject(period.getDateFrom()).get(Calendar.DAY_OF_WEEK))))
                .map(periodService::convertToPeriodDTO).toList();

        this.generatedPlan = GeneratorWorker.generatePlan(predefinedPlan, predefinedRequests, employees, year, month, team);
        return this.generatedPlan;
    }

}
