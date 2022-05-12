package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PeriodService {

    private final PeriodRepository periodRepository;

    public PeriodService(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    public List<Period> getPeriods() {
        return this.periodRepository.findAll();
    }

    public List<PeriodDTO> getPeriodsAsDTO() {
        List<Period> periods = this.getPeriods();
        List<PeriodDTO> periodDTOs = new ArrayList<>();
        periods.forEach(period -> periodDTOs.add(this.convertToPeriodDTO(period)));
        return periodDTOs;
    }

    public List<Period> getPeriodsByEmployee(Employee employee) {
        return periodRepository.findAllByEmployee(employee);
    }

    public Period getPeriod(Long id) {
        return this.periodRepository.findPeriodById(id);
    }


    // TODO in DTO einbinden
    public Period convertToPeriod(PeriodDTO periodDTO) {
        Period period = new Period();
        period.setPurpose(periodDTO.getPurpose());
        period.setDateFrom(periodDTO.getDateFrom());
        period.setDateTo(periodDTO.getDateTo());
        period.setEmployee(periodDTO.getEmployee());

        return period;
    }

    public PeriodDTO convertToPeriodDTO(Period period) {
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.setPurpose(period.getPurpose());
        periodDTO.setDateFrom(period.getDateFrom());
        periodDTO.setDateTo(period.getDateTo());
        periodDTO.setEmployee(period.getEmployee());

        return  periodDTO;
    }

    public void addPeriod(Period period) {
        periodRepository.save(period);
    }

    public void deletePeriod(Period period) {
        periodRepository.delete(period);
    }

    public List<Period> getPeriodsPerTeamAndTimeInterval(Team team, Date start, Date end) {
        return periodRepository.findAllByEmployeeTeamAndDateFromBetween(team, start, end);
    }

    public Period getPeriodFromPeriodDTO(PeriodDTO periodDTO) {
        return periodRepository.findByPurposeAndAndDateFromAndDateToAndEmployee(periodDTO.getPurpose(), periodDTO.getDateFrom(), periodDTO.getDateTo(), periodDTO.getEmployee());
    }

}
