package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PeriodController {

    // neuen Eintrag erstellen
    private final PeriodService periodService;

    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }

    @GetMapping("/leaveRequest")
    public List<PeriodDTO> createLeaveRequest() {

        // ToDo: Zu klaeren: Sollen wir fuer alle Rest-Uebergaben an Angular DTOs verwenden?
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

    @GetMapping("/periodRequest")
    public PeriodDTO createPeriodRequest() {
        long miliseconds = System.currentTimeMillis();
        Date today = new Date(miliseconds);
        Date tomorrow = new Date(miliseconds + 86_400_000); //miliseconds in a day
        //Today and tomorrow are the default days for the request
        PeriodDTO periodDTO = new PeriodDTO(Purpose.WORKING_HOURS, today, tomorrow, new Employee());
        return periodDTO;
    }

    //TODO: We need the model attribute Session user to be able to save the request
    @PostMapping("/periodRequest")
    public List<Period> submitPeriodRequest(PeriodDTO periodDTO) {
        Period period = periodService.convertToPeriod(periodDTO);
        periodService.addPeriod(period);
        return periodService.findPeriodsByEmployee(period.getEmployee());
    }

    // Eintrag anzeigen

    // Eintrag loeschen

    // Eintrag bearbeiten

    // Gib Daten eines bestimmten Purposes eines bestimmten Mitarbeiters zurueck

    // Alle Daten eines Mitarbeiters zurueckgeben

    // Alle Zeiten eines Teams in einem bestimmten Monat weitergeben = Dienstplan !!!! Wichtig!

}
