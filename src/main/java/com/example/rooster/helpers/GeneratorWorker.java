package com.example.rooster.helpers;

import com.example.rooster.employee.Employee;
import com.example.rooster.period.PeriodDTO;
import org.assertj.core.api.InstanceOfAssertFactories;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;

public class GeneratorWorker {

    public static Double getDailyWorkingHours(Double weeklyWorkingTime) {
        return weeklyWorkingTime / 5;
    }

    public static List<PeriodDTO> filterByEmployee(List<PeriodDTO> periodDTOList, long employeeId) {
        return periodDTOList.stream().filter(periodDTO -> periodDTO.getEmployee() == employeeId).toList();
    }

    public static PeriodDTO createPeriodDTO(int day, int month, int year, int hourFrom, int minuteFrom, int hourTo, int minuteTo, long employeeId, String purposeString) {
        Date from = DateWorker.getDateObject(0, minuteFrom, hourFrom, day, month, year);
        Date to = DateWorker.getDateObject(0, minuteTo, hourTo, day, month, year);

        PeriodDTO newPeriodDTO = new PeriodDTO();
        newPeriodDTO.setDateFrom(DateWorker.convertDateToDateString(from));
        newPeriodDTO.setDateTo(DateWorker.convertDateToDateString(to));
        newPeriodDTO.setEmployee(employeeId);
        newPeriodDTO.setPurpose(purposeString);

        return newPeriodDTO;
    }

    public static Double WorkingHourAndCompulsoryDifference(double workingHours, double compulsory) {
        return compulsory - workingHours;
    }

    public static double getWorkingHours(List<PeriodDTO> workingTimes, long employeeId) {
        return workingTimes.stream().filter(periodDTO -> periodDTO.getEmployee() == employeeId).mapToDouble(periodDTO ->
                // Stunden zaehlen
                        Duration.between(
                                DateWorker.convertDateStringToDate(periodDTO.getDateFrom()).toInstant(),
                                DateWorker.convertDateStringToDate(periodDTO.getDateTo()).toInstant()
                                ).toHours()
        ).sum();
    }

    public static double getCompulsory(List<PeriodDTO> workingTimes, Employee employee) {
        return DateWorker.getWorkingTime(GeneratorWorker.filterByEmployee(workingTimes, employee.getId()).stream().toList(),
                GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek()));
    }
}