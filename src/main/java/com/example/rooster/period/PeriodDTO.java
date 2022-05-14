package com.example.rooster.period;

import com.example.rooster.employee.Employee;

import java.util.Date;

public class PeriodDTO {


    private long id;
    private int purpose;

    private Date dateFrom;

    private Date dateTo;

    private long employee;

    public PeriodDTO() {
    }

    public PeriodDTO(long id, int purpose, Date dateFrom, Date dateTo, long employee) {
        this.id = id;
        this.purpose = purpose;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.employee = employee;
    }

    public PeriodDTO(Period period) {
        this.id = period.getId();
        this.purpose = period.getPurpose().ordinal();
        this.dateFrom = period.getDateFrom();
        this.dateTo = period.getDateTo();
        this.employee = period.getEmployee().getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPurpose() {
        return purpose;
    }

    public void setPurpose(int purpose) {
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
