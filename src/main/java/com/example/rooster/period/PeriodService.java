package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.team.Team;
import org.springframework.stereotype.Service;

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

    public List<Period> getPeriodsByEmployee(Employee employee) {
        return periodRepository.findAllByEmployee(employee);
    }

    public Period getPeriod(Long id) {
        return this.periodRepository.findPeriodById(id);
    }

    public Period convertToPeriod(PeriodDTO periodDTO) {
        Period period = new Period();
        period.setPurpose(periodDTO.getPurpose());
        period.setDateFrom(periodDTO.getDateFrom());
        period.setDateTo(periodDTO.getDateTo());
        period.setEmployee(periodDTO.getEmployee());

        return period;
    }

    public void addPeriod(Period period) {
        periodRepository.save(period);
    }

    public void deletePeriod(Period period) {
        periodRepository.delete(period);
    }

    public List<Period> getPeriodsPerTeamAndTimeInterval(Team team, Date start, Date end) {
        return periodRepository.findAllByEmployeeTeamAndDateFromBetweenOrEmployeeTeamAndDateToBetween(team, start, end);
    }

}
