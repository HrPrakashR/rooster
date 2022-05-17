package com.example.rooster.period;

import java.util.Date;

public class PeriodDTO {


    private long id;

    private String purpose;

    private Date dateFrom;

    private Date dateTo;

    private long employee;

    public PeriodDTO() {
    }

    public PeriodDTO(long id, String purpose, Date dateFrom, Date dateTo, long employee) {
        this.id = id;
        this.purpose = purpose;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.employee = employee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public long getEmployee() {
        return employee;
    }

    public void setEmployee(long employee) {
        this.employee = employee;
    }
}
