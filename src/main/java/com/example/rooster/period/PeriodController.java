package com.example.rooster.period;

import com.example.rooster.employee.EmployeeDTO;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return periodService.getPeriodsByEmployee(employeeService.getEmployeeById(employeeDTO.getId()));
    }

    // ToDo: Use period DTO instead of id -> DONE
    //Showing a certain request
    @GetMapping("/get/{id}")
    public Period showPeriodRequest(@PathVariable long id) {
        return periodService.getPeriod(id);
    }

    // ToDo: Use period DTO instead of id -> DONE
    //Deleting a certain request. Returns the list of remaining requests of the employee.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePeriodRequest(@PathVariable long id) {
        periodService.deletePeriod(periodService.getPeriod(id));
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
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
