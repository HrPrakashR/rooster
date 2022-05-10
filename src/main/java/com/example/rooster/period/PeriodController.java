package com.example.rooster.period;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class PeriodController {

    // neuen Eintrag erstellen
    private final PeriodRepository periodRepository;

    public PeriodController(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    @GetMapping("/leaveRequest")
    public List<PeriodDTO> createLeaveRequest(){
        List<PeriodDTO> leaveRequest = new LinkedList<>();

        for (Period period : periodRepository.findAllById()) {
            leaveRequest.add(new PeriodDTO(period.getPurpose(),period.getDateFrom(),period.getDateTo(),period.getEmployee()));
        }
        return leaveRequest;
    }

    // Eintrag loeschen

    // Eintrag bearbeiten

    // Gib Daten eines bestimmten Purposes eines bestimmten Mitarbeiters zurueck

    // Alle Daten eines Mitarbeiters zurueckgeben

    // Alle Zeiten eines Teams in einem bestimmten Monat weitergeben = Dienstplan !!!! Wichtig!

}
