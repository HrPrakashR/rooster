package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.helpers.DateWorker;
import com.example.rooster.team.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Service class for the logical operations regarding periods
 */
@Service
public class PeriodService {

    private final PeriodRepository periodRepository;

    private final EmployeeService employeeService;

    public PeriodService(PeriodRepository periodRepository, EmployeeService employeeService) {
        this.periodRepository = periodRepository;
        this.employeeService = employeeService;
    }

    /**
     * Method to get list of all periods through the repository
     * @return list of all available periods
     */
    public List<Period> getPeriods() {
        return this.periodRepository.findAll();
    }

    /**
     * Method to convert a list of all periods as period DTOs
     * @return the list of all periods as DTO
     */
    public List<PeriodDTO> getPeriodsAsDTO() {
        List<Period> periods = this.getPeriods();
        List<PeriodDTO> periodDTOs = new ArrayList<>();
        periods.forEach(period -> periodDTOs.add(this.convertToPeriodDTO(period)));
        return periodDTOs;
    }

    /**
     * Method to get all periods of a certain employee
     * @param employee takes an employee object as parameter
     * @return the periods of the given employee as a list
     */
    public List<Period> getPeriodsByEmployee(Employee employee) {
        return periodRepository.findAllByEmployee(employee);
    }

    /**
     * Method to retrieve a certain period
     * @param id period id, long
     * @return the period with the given id
     */
    public Period getPeriod(Long id) {
        return this.periodRepository.findPeriodById(id);
    }

    /**
     * Method to get purpose with a given ordinal, int
     * @param id ordinal of the selected purpose
     * @return the purpose with the given ordinal
     */
    private Purpose findPurpose(int id) {
        return Arrays.stream(Purpose.values()).filter(purpose -> purpose.ordinal() == id).findFirst().orElse(null);
    }

    /**
     * Method to convert a period DTO to a period
     * @param periodDTO receives a DTO as parameter
     * @return the converted period object
     */
    public Period convertToPeriod(PeriodDTO periodDTO) {
        Period period = new Period();
        period.setId(periodDTO.getId());
        period.setPurpose(Purpose.valueOf(periodDTO.getPurpose()));
        period.setDateFrom(DateWorker.convertDateStringToDate(periodDTO.getDateFrom()));
        period.setDateTo(DateWorker.convertDateStringToDate(periodDTO.getDateTo()));
        period.setEmployee(this.employeeService.getEmployee(periodDTO.getEmployee()));
        return period;
    }

    /**
     * Method to convert a period to a period DTO
     * @param period receives a period as parameter
     * @return the converted period DTO object
     */
    public PeriodDTO convertToPeriodDTO(Period period) {
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.setId(period.getId());
        periodDTO.setPurpose(period.getPurpose().name());
        periodDTO.setDateFrom(DateWorker.convertDateToDateString(period.getDateFrom()));
        periodDTO.setDateTo(DateWorker.convertDateToDateString(period.getDateTo()));
        periodDTO.setEmployee(period.getEmployee().getId());
        return periodDTO;
    }

    /**
     * Method to save a given period to the repository
     * @param period receives a period as parameter
     * @return the period after saving it
     */
    public Period addPeriod(Period period) {
        return periodRepository.save(period);
    }

    /**
     * Method to delete a certain period
     * @param period receives a period object as parameter
     */
    public void deletePeriod(Period period) {
        periodRepository.delete(period);
    }

    /**
     * Method to select periods of a team within a given time interval
     * @param team team of the given employee
     * @param start first day of the time interval
     * @param end last day of the time interval
     * @return a list of Periods for the given parameters
     */
    public List<Period> getPeriodsPerTeamAndTimeInterval(Team team, Date start, Date end) {
        return periodRepository.findAllByEmployeeTeamAndDateFromBetween(team, start, end);
    }

    /**
     * Method to select periods of an employee within a given time interval
     * @param employeeById selected employee
     * @param from first day of the time interval
     * @param to last day of the time interval
     * @return a list of Period DTOs for the given parameters
     */
    public List<PeriodDTO> getPeriodsByEmployeeAndBetween(Employee employeeById, Date from, Date to) {
        List<PeriodDTO> periodDTOList = new ArrayList<>();
        List<Period> periods = periodRepository.findAllByEmployeeAndDateFromBetween(employeeById, from, to);
        periods.forEach(period -> periodDTOList.add(this.convertToPeriodDTO(period)));
        return periodDTOList;
    }

    /**
     * Method to delete all periods for a certain team/year/month
     * @param team selected team
     * @param month selected month as int
     * @param year selected year as int
     */
    public void deleteAllPeriodsByTeamAndByMonthAndByYear(Team team, int month, int year) {
        List<Period> teamPeriods = this.periodRepository.findAllByEmployeeTeam(team);
        Date firstDay = DateWorker.getFirstOrLastDayOfMonth(false, year, month).getTime();
        Date lastDay = DateWorker.getFirstOrLastDayOfMonth(true, year, month).getTime();

        for (Period teamPeriod : teamPeriods) {
            if (teamPeriod.getDateFrom().after(firstDay) && teamPeriod.getDateTo().before(lastDay)) {
                this.periodRepository.delete(teamPeriod);
            }
        }
    }
}
