package com.example.rooster.period;

import com.example.rooster.employee.Employee;

import java.util.Date;

public class PeriodDTO {

    private Purpose purpose;

    private Date dateFrom;

    private Date dateTo;

    private Employee employee;

    public PeriodDTO(Purpose purpose, Date dateFrom, Date dateTo, Employee employee) {
        this.purpose = purpose;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.employee = employee;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
