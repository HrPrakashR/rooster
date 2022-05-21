
package com.example.rooster.helpers;

import com.example.rooster.period.PeriodDTO;

import java.util.Calendar;
import java.util.List;

public class GeneratorWorker {

    public static Double getDailyWorkingHours(double weeklyWorkingTime) {
        return weeklyWorkingTime / 5;
    }

    public static List<PeriodDTO> filterByEmployee(List<PeriodDTO> periodDTOList, long employeeId) {
        return periodDTOList.stream().filter(periodDTO -> periodDTO.getEmployee() == employeeId).toList();
    }

    public static PeriodDTO createDTO(Calendar day, int hourFrom, int minuteFrom, int hourTo, int minuteTo, long employeeId, String purposeString) {
        // add working times - please do not change!
        Calendar from = day;
        from.set(Calendar.HOUR_OF_DAY, hourFrom);
        from.set(Calendar.MINUTE, minuteFrom);

        Calendar to = day;
        from.set(Calendar.HOUR_OF_DAY, hourTo);
        from.set(Calendar.MINUTE, minuteTo);

        PeriodDTO newPeriodDTO = new PeriodDTO();
        newPeriodDTO.setDateFrom(from.toInstant().toString());
        newPeriodDTO.setDateTo(to.toInstant().toString());
        newPeriodDTO.setEmployee(employeeId);
        newPeriodDTO.setPurpose(purposeString);

        return newPeriodDTO;
    }
}