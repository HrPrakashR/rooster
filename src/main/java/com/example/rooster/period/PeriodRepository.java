package com.example.rooster.period;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends CrudRepository<Period, Long> {
}
