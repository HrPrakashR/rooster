package com.example.rooster.team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private double restHours;

    private int restDays;

    private double minBreakTime;

    private Date mondayFrom;
    private Date mondayTo;

    private Date tuesdayFrom;
    private Date tuesdayTo;

    private Date wednesdayFrom;
    private Date wednesdayTo;

    private Date thursdayFrom;
    private Date thursdayTo;
    private Date fridayFrom;
    private Date fridayTo;
    private Date saturdayFrom;
    private Date saturdayTo;
    private Date sundayFrom;
    private Date sundayTo;

    public Team() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRestHours() {
        return restHours;
    }

    public void setRestHours(double restHours) {
        this.restHours = restHours;
    }

    public int getRestDays() {
        return restDays;
    }

    public void setRestDays(int restDays) {
        this.restDays = restDays;
    }

    public double getMinBreakTime() {
        return minBreakTime;
    }

    public void setMinBreakTime(double minBreakTime) {
        this.minBreakTime = minBreakTime;
    }

    public Date getMondayFrom() {
        return mondayFrom;
    }

    public void setMondayFrom(Date mondayFrom) {
        this.mondayFrom = mondayFrom;
    }

    public Date getMondayTo() {
        return mondayTo;
    }

    public void setMondayTo(Date mondayTo) {
        this.mondayTo = mondayTo;
    }

    public Date getTuesdayFrom() {
        return tuesdayFrom;
    }

    public void setTuesdayFrom(Date tuesdayFrom) {
        this.tuesdayFrom = tuesdayFrom;
    }

    public Date getTuesdayTo() {
        return tuesdayTo;
    }

    public void setTuesdayTo(Date tuesdayTo) {
        this.tuesdayTo = tuesdayTo;
    }

    public Date getWednesdayFrom() {
        return wednesdayFrom;
    }

    public void setWednesdayFrom(Date wednesdayFrom) {
        this.wednesdayFrom = wednesdayFrom;
    }

    public Date getWednesdayTo() {
        return wednesdayTo;
    }

    public void setWednesdayTo(Date wednesdayTo) {
        this.wednesdayTo = wednesdayTo;
    }

    public Date getThursdayFrom() {
        return thursdayFrom;
    }

    public void setThursdayFrom(Date thursdayFrom) {
        this.thursdayFrom = thursdayFrom;
    }

    public Date getThursdayTo() {
        return thursdayTo;
    }

    public void setThursdayTo(Date thursdayTo) {
        this.thursdayTo = thursdayTo;
    }

    public Date getFridayFrom() {
        return fridayFrom;
    }

    public void setFridayFrom(Date fridayFrom) {
        this.fridayFrom = fridayFrom;
    }

    public Date getFridayTo() {
        return fridayTo;
    }

    public void setFridayTo(Date fridayTo) {
        this.fridayTo = fridayTo;
    }

    public Date getSaturdayFrom() {
        return saturdayFrom;
    }

    public void setSaturdayFrom(Date saturdayFrom) {
        this.saturdayFrom = saturdayFrom;
    }

    public Date getSaturdayTo() {
        return saturdayTo;
    }

    public void setSaturdayTo(Date saturdayTo) {
        this.saturdayTo = saturdayTo;
    }

    public Date getSundayFrom() {
        return sundayFrom;
    }

    public void setSundayFrom(Date sundayFrom) {
        this.sundayFrom = sundayFrom;
    }

    public Date getSundayTo() {
        return sundayTo;
    }

    public void setSundayTo(Date sundayTo) {
        this.sundayTo = sundayTo;
    }


/*  actually we do not need that method. But it is possibly usable in the future.

    public List<List<Date>> getWeekdays() {
        List<List<Date>> weekdays = new ArrayList<>();
        List<Date> weekdaysFrom = new ArrayList<>();
        List<Date> weekdaysTo = new ArrayList<>();

        weekdaysFrom.add(getMondayFrom());
        weekdaysFrom.add(getTuesdayFrom());
        weekdaysFrom.add(getWednesdayFrom());
        weekdaysFrom.add(getThursdayFrom());
        weekdaysFrom.add(getFridayFrom());
        weekdaysFrom.add(getSaturdayFrom());
        weekdaysFrom.add(getSundayFrom());

        weekdaysTo.add(getMondayTo());
        weekdaysTo.add(getTuesdayTo());
        weekdaysTo.add(getWednesdayTo());
        weekdaysTo.add(getThursdayTo());
        weekdaysTo.add(getFridayTo());
        weekdaysTo.add(getSaturdayTo());
        weekdaysTo.add(getSundayTo());

        for (int i = 0; i < 7; i++) {
            List<Date> day = new ArrayList<>();
            day.add(weekdaysFrom.get(i));
            day.add(weekdaysTo.get(i));
            weekdays.add(day);
        }

        return weekdays;
    }*/

    public void setId(Long id) {
        this.id = id;
    }
}