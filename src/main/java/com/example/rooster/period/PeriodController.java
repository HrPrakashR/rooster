package com.example.rooster.period;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // Eintrag anzeigen

    // Eintrag loeschen

    // Eintrag bearbeiten

    // Gib Daten eines bestimmten Purposes eines bestimmten Mitarbeiters zurueck

    // Alle Daten eines Mitarbeiters zurueckgeben

    // Alle Zeiten eines Teams in einem bestimmten Monat weitergeben = Dienstplan !!!! Wichtig!

}
