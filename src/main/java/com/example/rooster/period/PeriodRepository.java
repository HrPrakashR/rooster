package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * JPA Repository for Period Entity
 */
@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {

    List<Period> findAll();

    Period findPeriodById(long id);

    List<Period> findAllByEmployee(Employee employee);

    Period findByPurposeAndDateFromAndDateToAndEmployee(Purpose purpose, Date dateFrom, Date dateTo, Employee employee);

    List<Period> findAllByEmployeeTeam(Team team);

    /**
     * Query to select periods of a team within a given time interval
     * @param employee_team team of the given employee
     * @param dateFrom first day of the time interval
     * @param dateFrom2 last day of the time interval
     * @return a list of Periods for the given parameters
     */
    List<Period> findAllByEmployeeTeamAndDateFromBetween(Team employee_team, Date dateFrom, Date dateFrom2);

    /**
     * Query to select periods of an employee within a given time interval
     * @param employee selected employee
     * @param start first day of the time interval
     * @param end last day of the time interval
     * @return a list of Periods for the given parameters
     */
    List<Period> findAllByEmployeeAndDateFromBetween(Employee employee, Date start, Date end);
}
