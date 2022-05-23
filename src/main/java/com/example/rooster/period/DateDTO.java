package com.example.rooster.period;

import java.util.Date;

/**
 * Class for holding Date DTOs, which are used for the information exchange (dates)
 * between backend and frontend.
 */
public class DateDTO {
    private Date dateFrom;
    private Date dateTo;

    public DateDTO() {
    }

    public DateDTO(Date dateFrom, Date dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
