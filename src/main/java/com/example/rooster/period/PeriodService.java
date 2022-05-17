package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.team.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PeriodService {

    private final PeriodRepository periodRepository;

    private final EmployeeService employeeService;

    public PeriodService(PeriodRepository periodRepository, EmployeeService employeeService) {
        this.periodRepository = periodRepository;
        this.employeeService = employeeService;
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

    private Purpose findPurpose(int id) {
        return Arrays.stream(Purpose.values()).filter(purpose -> purpose.ordinal() == id).findFirst().orElse(null);
    }

    // TODO in DTO einbinden
    public Period convertToPeriod(PeriodDTO periodDTO) {
        Period period = new Period();
        period.setPurpose(Purpose.valueOf(periodDTO.getPurpose()));
        period.setDateFrom(periodDTO.getDateFrom());
        period.setDateTo(periodDTO.getDateTo());
        period.setEmployee(this.employeeService.getEmployee(periodDTO.getEmployee()));
        return period;
    }

    public PeriodDTO convertToPeriodDTO(Period period) {
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.setId(period.getId());
        periodDTO.setPurpose(period.getPurpose().name());
        periodDTO.setDateFrom(period.getDateFrom());
        periodDTO.setDateTo(period.getDateTo());
        periodDTO.setEmployee(period.getEmployee().getId());
        return periodDTO;
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
}
