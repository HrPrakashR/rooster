package com.example.rooster.team;

import java.util.Date;

public class TeamDTO {

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

    public TeamDTO() {
    }

    public TeamDTO(String name, double restHours, int restDays, double minBreakTime, Date mondayFrom, Date mondayTo, Date tuesdayFrom, Date tuesdayTo, Date wednesdayFrom, Date wednesdayTo, Date thursdayFrom, Date thursdayTo, Date fridayFrom, Date fridayTo, Date saturdayFrom, Date saturdayTo, Date sundayFrom, Date sundayTo) {
        this.name = name;
        this.restHours = restHours;
        this.restDays = restDays;
        this.minBreakTime = minBreakTime;
        this.mondayFrom = mondayFrom;
        this.mondayTo = mondayTo;
        this.tuesdayFrom = tuesdayFrom;
        this.tuesdayTo = tuesdayTo;
        this.wednesdayFrom = wednesdayFrom;
        this.wednesdayTo = wednesdayTo;
        this.thursdayFrom = thursdayFrom;
        this.thursdayTo = thursdayTo;
        this.fridayFrom = fridayFrom;
        this.fridayTo = fridayTo;
        this.saturdayFrom = saturdayFrom;
        this.saturdayTo = saturdayTo;
        this.sundayFrom = sundayFrom;
        this.sundayTo = sundayTo;
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

    public Team getTeam() {
        Team team = new Team();
        team.setName(this.getName());
        team.setRestHours(this.getRestHours());
        team.setRestDays(this.getRestDays());
        team.setMinBreakTime(this.getMinBreakTime());
        team.setMondayFrom(this.getMondayFrom());
        team.setMondayTo(this.getMondayTo());
        team.setTuesdayFrom(this.getTuesdayFrom());
        team.setTuesdayTo(this.getTuesdayTo());
        team.setWednesdayFrom(this.getWednesdayFrom());
        team.setWednesdayTo(this.getWednesdayTo());
        team.setThursdayFrom(this.getThursdayFrom());
        team.setThursdayTo(this.getThursdayTo());
        team.setFridayFrom(this.getFridayFrom());
        team.setFridayTo(this.getFridayTo());
        team.setSaturdayFrom(this.getSaturdayFrom());
        team.setSaturdayTo(this.getSaturdayTo());
        team.setSundayFrom(this.getSundayFrom());
        team.setSundayTo(this.getSundayTo());
        return team;
    }
}
