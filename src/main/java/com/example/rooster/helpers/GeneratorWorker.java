package com.example.rooster.helpers;

import com.example.rooster.period.PeriodDTO;

import java.util.Date;
import java.util.List;

public class GeneratorWorker {

    public static Double getDailyWorkingHours(double weeklyWorkingTime) {
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
}