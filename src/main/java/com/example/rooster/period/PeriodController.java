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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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

    @PostMapping("/saveNewRoster")
    public void saveNewRoster(@RequestBody PeriodDTO[] periodDTOs) {
        //TODO: Get team, month and year from the incoming periods
        //TODO: Then delete all periods from this team within this month
        Team periodTeam = this.employeeService.getEmployee(periodDTOs[0].getEmployee()).getTeam();
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

    @GetMapping("/generatedRoster/total/{employeeId}")
    public Double getTotalForGenerated(@PathVariable int employeeId) {
        Employee employee = this.employeeService.getEmployee(employeeId);
        return GeneratorWorker.getTotalWorkingHours(this.generatedPlan, employee, employee.getTeam());
    }

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

        List<PeriodDTO> predefinedPlan = predefinedPeriods.stream().filter(period ->
                        Stream.of(Purpose.WORKING_HOURS, Purpose.CONFIRMED_VACATION, Purpose.ABSENCE, Purpose.SICK_LEAVE)
                                .anyMatch(purpose -> period.getPurpose().equals(purpose)
                                        && DateWorker.checkIfTeamWorksAtDay(team, DateWorker.getCalendarObject(period.getDateFrom()).get(Calendar.DAY_OF_WEEK))))
                .map(periodService::convertToPeriodDTO).toList();

        List<PeriodDTO> generatedPlan = new ArrayList<>(predefinedPlan);

        // iterate through days and employees
        employees.forEach(employee -> {
            AtomicInteger i = new AtomicInteger();
            i.incrementAndGet();
            DateWorker.getAllDaysOfMonth(year, month).forEach(day -> {

                // check if they have enough time to work at another day
                if (GeneratorWorker.CompulsoryWorkingHourDifference(
                        GeneratorWorker.getCompulsory(year, month, employee),
                        GeneratorWorker.getTotalWorkingHours(generatedPlan, employee, team),
                        employee
                ) > GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())
                        // check if there is no other period at this day
                        && predefinedPlan.stream()
                        .noneMatch(periodDTO -> periodDTO.getEmployee() == employee.getId() &&
                                periodDTO.getDateFrom().startsWith(String.format("%04d-%02d-%02d", year, month, i.get())))
                        // check the teams working times and the employees rest day
                        && GeneratorWorker.isWorkingDay(year, month, i.get(), team, employee)
                ) {
                    // initialize values WORK AND CALCULATE HERE


                    // TODO: zwischen hourTo und nächster hourFrom eines gleichen employees muessen team.getRestHours Stunden liegen
                    // TODO: beruecksichtige Requests
                    // TODO: ueberpruefe, ob alle Zeiten abgedeckt sind

                    // init values
                    int hourFrom = 0;
                    int hourTo = 0;
                    int minuteFrom = 0;
                    int minuteTo = 0;
                    Purpose purpose = Purpose.WORKING_HOURS;

                    Calendar calendarFrom = GeneratorWorker.getFrom(team, year, month, i.get());
                    Calendar calendarTo = GeneratorWorker.getTo(team, year, month, i.get());
                    double workingHours = GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek());
                    // early shift, late shift, middle shift
                    int randomNumber = (new Random()).nextInt(0,99);
                    if(randomNumber < 33){
                        hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
                        minuteFrom = calendarFrom.get(Calendar.MINUTE);
                        calendarFrom.add(Calendar.MINUTE, (int) Math.round(workingHours * 60));
                        calendarFrom.add(Calendar.MINUTE, (int) Math.round(team.getMinBreakTime() * 60));
                        hourTo = calendarFrom.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarFrom.get(Calendar.MINUTE);
                    }else if(randomNumber < 66){
                        hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarTo.get(Calendar.MINUTE);
                        calendarTo.add(Calendar.MINUTE, ((int) Math.round(workingHours*60)) * (-1));
                        calendarTo.add(Calendar.MINUTE, ((int) Math.round(team.getMinBreakTime() * 60)) * (-1));
                        hourFrom = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteFrom = calendarTo.get(Calendar.MINUTE);
                    }else{
                        hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY) + differenceHour;
                        hourTo = calendarTo.get(Calendar.HOUR_OF_DAY) + differenceHour;
                        minuteFrom = calendarFrom.get(Calendar.MINUTE) + differenceMinute;
                        minuteTo = calendarTo.get(Calendar.MINUTE) + differenceMinute;
                    }

                    if(calendarTo.before(calendarFrom)){
                        hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarTo.get(Calendar.MINUTE);
                    }
                    if(calendarFrom.after(calendarTo)){
                        hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarTo.get(Calendar.MINUTE);
                    }


                    // add working times
                    PeriodDTO createdPeriodDTO = GeneratorWorker.createPeriodDTO(
                            i.get(),
                            month,
                            year,
                            hourFrom,
                            minuteFrom,
                            hourTo,
                            minuteTo,
                            employee.getId(),
                            purpose.name());

                    // calculate with breakTime
                    createdPeriodDTO.setDateTo(GeneratorWorker.addHoursToDateString(createdPeriodDTO.getDateTo(), team.getMinBreakTime()));

                    // a little randomizing the creation
                    if ((new Random()).nextInt(0, 7) < GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())) {
                        generatedPlan.add(createdPeriodDTO);
                    }
                }
                i.incrementAndGet();
            });
        });

        this.generatedPlan = generatedPlan;
        return generatedPlan;
    }
}
