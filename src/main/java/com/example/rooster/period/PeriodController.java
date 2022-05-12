package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
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

    //ToDo: Does not work: empty result, but id is set correctly
    // ToDo: look at todo line 28
    // It should be working with the converter method
    //added RequestBody annotation, should now work.
    //Submitting the filled form, saving it as a converted period
    @PostMapping("/new")
    public List<Period> submitPeriodRequest(@RequestBody PeriodDTO periodDTO) {
        Period period = periodService.convertToPeriod(periodDTO);
        periodService.addPeriod(period);
        return periodService.getPeriodsByEmployee(period.getEmployee());
    }

    // ToDo: Use employeeDTO and Postmapping
    //Showing the requests/entries of a certain employee (employee id as request parameter)
    //Purpose can be filtered at Frontend
    //TODO: We need a method in EmplyerService to determine the related user
    //For example: findEmployeeByNameAndLastName(String name, String lastName)
    @GetMapping("/employee/get_all")
    public List<Period> showPeriodRequestFromEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
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

    //  ToDo: Use period DTO instead of id - new DateDTO
    //Displaying the periods of a certain team in a certain time interval
    @GetMapping("/team/time_plan")
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
