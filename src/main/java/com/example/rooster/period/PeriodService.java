package com.example.rooster.period;

import org.springframework.stereotype.Service;

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

    public Period getPeriod(Long id) {
        return this.periodRepository.findPeriodById(id);
    }
}
