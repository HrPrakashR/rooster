package com.example.rooster.period;

import com.example.rooster.employee.Employee;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "period")
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    private Purpose purpose;

    private Date dateFrom;

    private Date dateTo;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Period() {
    }

    public Period(long id, Purpose purpose, Date dateFrom, Date dateTo, Employee employee) {
        this.id = id;
        this.purpose = purpose;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.employee = employee;
    }

    private Purpose findPurpose(int id){
        for (Purpose purpose:Purpose.values()) {
            if (purpose.ordinal() ==id){
                return purpose;
            }
        }
        return null;
    }
    public Period(PeriodDTO periodDTO) {
        this.id = periodDTO.getId();
        this.purpose = findPurpose(periodDTO.getPurpose());
        this.dateFrom = periodDTO.getDateFrom();
        this.dateTo = periodDTO.getDateTo();
        this.employee = ;
    }

    public long getId() {
        return id;
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
