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

    // neuen Eintrag erstellen
    private final PeriodService periodService;
    private final EmployeeService employeeService;

    private final TeamService teamService;

    public PeriodController(PeriodService periodService, EmployeeService employeeService, TeamService teamService) {
        this.periodService = periodService;
        this.employeeService = employeeService;
        this.teamService = teamService;
    }

    @GetMapping("/leaveRequest")
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
    @GetMapping("/periodRequest")
    public PeriodDTO createPeriodRequest() {
        long miliseconds = System.currentTimeMillis();
        Date today = new Date(miliseconds);
        Date tomorrow = new Date(miliseconds + 86_400_000); //miliseconds in a day
        //Today and tomorrow are the default days for the request
        PeriodDTO periodDTO = new PeriodDTO(Purpose.WORKING_HOURS, today, tomorrow, new Employee());
        return periodDTO;
    }

    //Submitting the filled form, saving it as a converted period
    @PostMapping("/periodRequest")
    public List<Period> submitPeriodRequest(PeriodDTO periodDTO) {
        Period period = periodService.convertToPeriod(periodDTO);
        periodService.addPeriod(period);
        return periodService.getPeriodsByEmployee(period.getEmployee());
    }

    //Showing the requests/entries of a certain employee (employee id as request parameter)
    //Purpose can be filtered at Frontend
    @GetMapping("/periodRequests/employee/{employeeId}")
    public List<Period> showPeriodRequestFromEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return periodService.getPeriodsByEmployee(employee);
    }

    //Showing a certain request
    @GetMapping("periodRequest/{id}")
    public Period showPeriodRequest(@PathVariable long id) {
        return periodService.getPeriod(id);
    }

    //Deleting a certain request. Returns the list of remaining requests of the employee.
    @DeleteMapping("/periodRequest/delete/{id}")
    public List<Period> deletePeriodRequest(@PathVariable long id) {
        Period period = periodService.getPeriod(id);
        Employee employee = period.getEmployee();
        periodService.deletePeriod(period);
        return periodService.getPeriodsByEmployee(employee);
    }

    //Displaying the periods of a certain team in a certain time interval
    @GetMapping("/periodRequests/team/{id}")
    public List<Period> showPeriodsPerTeamAndTimeInterval(@PathVariable long id,
                                                          @RequestParam Date start,
                                                          @RequestParam Date end) {
        Team team = teamService.getTeam(id);

        return periodService.getPeriodsPerTeamAndTimeInterval(team, start, end);

    }


    // Eintrag anzeigen ==> done

    // Eintrag loeschen ==> done

    // Eintrag bearbeiten

    // Gib Daten eines bestimmten Purposes eines bestimmten Mitarbeiters zurueck
    // ==> would be better at frontend, we already have the list of a certain employee

    // Alle Daten eines Mitarbeiters zurueckgeben ==> done

    // Alle Zeiten eines Teams in einem bestimmten Monat weitergeben = Dienstplan !!!! Wichtig!

}
