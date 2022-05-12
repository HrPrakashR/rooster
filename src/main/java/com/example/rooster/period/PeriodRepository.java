package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import com.example.rooster.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {

    List<Period> findAll();

    Period findPeriodById(long id);

    List<Period> findAllByEmployee(Employee employee);

    List<Period> findAllByEmployeeTeamAndDateFromBetween(Team team, Date beginDate, Date endDate);
    // TODO MEHMET: Old, not working: java.lang.IllegalArgumentException
    // List<Period> findAllByEmployeeTeamAndDateFromBetweenOrEmployeeTeamAndDateToBetween(Team team, Date beginDate, Date endDate);
}
