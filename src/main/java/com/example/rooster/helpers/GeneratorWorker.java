package com.example.rooster.helpers;

import com.example.rooster.employee.Employee;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.Purpose;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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

    public static Double CompulsoryWorkingHourDifference(double compulsory, double workingHours) {
        return compulsory - workingHours;
    }

    public static double getTotalWorkingHours(List<PeriodDTO> workingTimes, Employee employee) {
        double total = workingTimes.stream().filter(periodDTO ->
                periodDTO.getEmployee() == employee.getId() &&
                        periodDTO.getPurpose().equals(Purpose.WORKING_HOURS.name())
                )
                .mapToDouble(periodDTO ->
                // Stunden zaehlen
                        Duration.between(
                                DateWorker.convertDateStringToDate(periodDTO.getDateFrom()).toInstant(),
                                DateWorker.convertDateStringToDate(periodDTO.getDateTo()).toInstant()
                                ).toHours()
        ).sum();

        total += workingTimes.stream().filter(periodDTO ->
                        periodDTO.getEmployee() == employee.getId() &&
                                Stream.of(Purpose.CONFIRMED_VACATION.name(), Purpose.SICK_LEAVE.name())
                                        .anyMatch(purpose -> periodDTO.getPurpose().equals(purpose))
                )
                .mapToDouble(periodDTO ->
                       employee.getHoursPerWeek() / 5
                ).sum();

        return total;
    }

    public static double getCompulsory(List<PeriodDTO> workingTimes, Employee employee) {
        return DateWorker.getWorkingTime(GeneratorWorker.filterByEmployee(workingTimes, employee.getId()).stream().toList(),
                GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek()));
    }
}