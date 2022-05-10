package com.example.rooster.time_frame;

import com.example.rooster.employee.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
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
