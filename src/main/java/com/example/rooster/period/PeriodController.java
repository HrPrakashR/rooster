package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PeriodController {

    private final PeriodService periodService;
    private final EmployeeService employeeService;

    private final TeamService teamService;

    public PeriodController(PeriodService periodService, EmployeeService employeeService, TeamService teamService) {
        this.periodService = periodService;
        this.employeeService = employeeService;
        this.teamService = teamService;
    }

    // ToDo: write new Period method in PeriodDTO -> public Period getPeriod(){}. (You can take a look at TeamDTO)
    @GetMapping("/periods")
    public List<PeriodDTO> createLeaveRequest() {
        return this.periodService
                .getPeriods()
                .stream()
                .map(period ->
                        new PeriodDTO(period.getPurpose(),
                                period.getDateFrom(),
                                period.getDateTo(),
                                period.getEmployee()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    //Showing the form for a new period request/entry
    @GetMapping("/period/new")
    public PeriodDTO createPeriodRequest() {
        long miliseconds = System.currentTimeMillis();
        Date today = new Date(miliseconds);
        Date tomorrow = new Date(miliseconds + 86_400_000); //miliseconds in a day
        //Today and tomorrow are the default days for the request
        PeriodDTO periodDTO = new PeriodDTO(Purpose.WORKING_HOURS, today, tomorrow, new Employee());
        return periodDTO;
    }

    //ToDo: Does not work: empty result, but id is set correctly
    //Submitting the filled form, saving it as a converted period
    @PostMapping("/period/new")
    public List<Period> submitPeriodRequest(PeriodDTO periodDTO) {
        Period period = periodService.convertToPeriod(periodDTO); // ToDo: look at todo line 28
        periodService.addPeriod(period);
        return periodService.getPeriodsByEmployee(period.getEmployee());
    }

    // ToDo: Use employeeDTO and Postmapping
    //Showing the requests/entries of a certain employee (employee id as request parameter)
    //Purpose can be filtered at Frontend
    @GetMapping("/period/employee/{employeeId}")
    public List<Period> showPeriodRequestFromEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return periodService.getPeriodsByEmployee(employee);
    }

    // ToDo: Use period DTO instead of id
    //Showing a certain request
    @GetMapping("/period/{id}")
    public Period showPeriodRequest(@PathVariable long id) {
        return periodService.getPeriod(id);
    }

    // ToDo: Use period DTO instead of id
    //Deleting a certain request. Returns the list of remaining requests of the employee.
    @DeleteMapping("/period/delete/{id}")
    public List<Period> deletePeriodRequest(@PathVariable long id) {
        Period period = periodService.getPeriod(id);
        Employee employee = period.getEmployee();
        periodService.deletePeriod(period);
        return periodService.getPeriodsByEmployee(employee);
    }

    //  ToDo: Use period DTO instead of id
    //Displaying the periods of a certain team in a certain time interval
    @GetMapping("/periods/team/{id}")
    public List<Period> showPeriodsPerTeamAndTimeInterval(@PathVariable long id,
                                                          @RequestParam Date start,
                                                          @RequestParam Date end) {
        Team team = teamService.getTeam(id);

        return periodService.getPeriodsPerTeamAndTimeInterval(team, start, end);

    }

    // Eintrag anzeigen ==> done

    // Eintrag loeschen ==> done

    // Neuer Eintrag

    // Eintrag bearbeiten

    // Gib Daten eines bestimmten Purposes eines bestimmten Mitarbeiters zurueck
    // ==> would be better at frontend, we already have the list of a certain employee

    // Alle Daten eines Mitarbeiters zurueckgeben ==> done

    // Alle Zeiten eines Teams in einem bestimmten Monat weitergeben = Dienstplan !!!! Wichtig!

}
