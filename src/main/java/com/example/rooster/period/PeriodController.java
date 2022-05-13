package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeDTO;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    // ToDo: write new Period method in PeriodDTO -> public Period getPeriod(){}. (You can take a look at TeamDTO)
    // Look at Team getAkk
    @GetMapping("/get_all")
    public List<PeriodDTO> getAll() {
        return periodService.getPeriodsAsDTO();
    }

    //Showing the form for a new period request/entry
    @GetMapping("/new")
    public PeriodDTO createPeriodRequest() {
        long miliseconds = System.currentTimeMillis();
        Date today = new Date(miliseconds);
        Date tomorrow = new Date(miliseconds + 86_400_000); //miliseconds in a day
        //Today and tomorrow are the default days for the request
        PeriodDTO periodDTO = new PeriodDTO(Purpose.WORKING_HOURS, today, tomorrow, new Employee());
        return periodDTO;
    }


    @PostMapping("/new")
    public List<Period> submitPeriodRequest(@RequestBody PeriodDTO periodDTO) {
        Period period = periodService.convertToPeriod(periodDTO);
        periodService.addPeriod(period);
        return periodService.getPeriodsByEmployee(period.getEmployee());
    }

    // ToDo: Use employeeDTO and Postmapping -> DONE
    //Showing the requests/entries of a certain employee (employee id as request parameter)
    //Purpose can be filtered at Frontend
    //TODO: We need a method in EmployerService to determine the related user
    //For example: findEmployeeByEMail(String email)
    @PostMapping("/employee/get_all")
    public List<Period> showPeriodRequestFromEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.getEmployeeByEmail(employeeDTO.getEmail());
        return periodService.getPeriodsByEmployee(employee);
    }

    // ToDo: Use period DTO instead of id -> DONE
    //Showing a certain request
    @GetMapping("/get")
    public Period showPeriodRequest(@RequestBody PeriodDTO periodDTO) {
        return periodService.getPeriodFromPeriodDTO(periodDTO);
    }

    // ToDo: Use period DTO instead of id -> DONE
    //Deleting a certain request. Returns the list of remaining requests of the employee.
    @DeleteMapping("/delete")
    public List<Period> deletePeriodRequest(@RequestBody PeriodDTO periodDTO) {
        Period period = periodService.getPeriodFromPeriodDTO(periodDTO);
        Employee employee = period.getEmployee();
        periodService.deletePeriod(period);
        return periodService.getPeriodsByEmployee(employee);
    }

    //  ToDo: Use period DTO instead of id - new DateDTO -> DONE
    //Displaying the periods of a certain team in a certain time interval
    @PostMapping("/time_plan/{teamId}")
    public List<Period> showPeriodsPerTeamAndTimeInterval(@PathVariable long teamId,
                                                          @RequestBody DateDTO dateDTO) {
        Team team = teamService.getTeam(teamId);
        Date dateFrom = dateDTO.getDateFrom();
        Date dateTo = dateDTO.getDateTo();
        return periodService.getPeriodsPerTeamAndTimeInterval(team, dateFrom, dateTo);
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
