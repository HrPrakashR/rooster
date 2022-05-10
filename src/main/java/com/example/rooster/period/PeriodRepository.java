package com.example.rooster.period;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodRepository extends CrudRepository<Period, Long> {

    List<Period> findAll();

    Period findPeriodById(long id);

}
