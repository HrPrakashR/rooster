package com.example.rooster.period;

public class PeriodDTO {


    private long id;

    private String purpose;

    private String dateFrom;

    private String dateTo;

    private long employee;

    public PeriodDTO() {
    }

    public PeriodDTO(long id, String purpose, String dateFrom, String dateTo, long employee) {
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

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public long getEmployee() {
        return employee;
    }

    public void setEmployee(long employee) {
        this.employee = employee;
    }
}
