package com.example.rooster.period;

import com.example.rooster.employee.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodRepository extends CrudRepository<Period, Long> {

    List<Period> findAll();

    Period findPeriodById(long id);

    List<Period> findAllByEmployee(Employee employee);

}
