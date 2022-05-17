package com.example.rooster.team;

import java.util.Date;

public class TeamDTO {

    private long id;

    private String name;

    private double restHours;

    private int restDays;

    private double minBreakTime;

    private String mondayFrom;
    private String mondayTo;

    private String tuesdayFrom;
    private String tuesdayTo;

    private String wednesdayFrom;
    private String wednesdayTo;

    private String thursdayFrom;
    private String thursdayTo;

    private String fridayFrom;
    private String fridayTo;

    private String saturdayFrom;
    private String saturdayTo;

    private String sundayFrom;
    private String sundayTo;

    public TeamDTO() {

    }

    public TeamDTO(long id, String name, double restHours, int restDays, double minBreakTime, String mondayFrom, String mondayTo, String tuesdayFrom, String tuesdayTo, String wednesdayFrom, String wednesdayTo, String thursdayFrom, String thursdayTo, String fridayFrom, String fridayTo, String saturdayFrom, String saturdayTo, String sundayFrom, String sundayTo) {
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMondayFrom() {
        return mondayFrom;
    }

    public void setMondayFrom(String mondayFrom) {
        this.mondayFrom = mondayFrom;
    }

    public String getMondayTo() {
        return mondayTo;
    }

    public void setMondayTo(String mondayTo) {
        this.mondayTo = mondayTo;
    }

    public String getTuesdayFrom() {
        return tuesdayFrom;
    }

    public void setTuesdayFrom(String tuesdayFrom) {
        this.tuesdayFrom = tuesdayFrom;
    }

    public String getTuesdayTo() {
        return tuesdayTo;
    }

    public void setTuesdayTo(String tuesdayTo) {
        this.tuesdayTo = tuesdayTo;
    }

    public String getWednesdayFrom() {
        return wednesdayFrom;
    }

    public void setWednesdayFrom(String wednesdayFrom) {
        this.wednesdayFrom = wednesdayFrom;
    }

    public String getWednesdayTo() {
        return wednesdayTo;
    }

    public void setWednesdayTo(String wednesdayTo) {
        this.wednesdayTo = wednesdayTo;
    }

    public String getThursdayFrom() {
        return thursdayFrom;
    }

    public void setThursdayFrom(String thursdayFrom) {
        this.thursdayFrom = thursdayFrom;
    }

    public String getThursdayTo() {
        return thursdayTo;
    }

    public void setThursdayTo(String thursdayTo) {
        this.thursdayTo = thursdayTo;
    }

    public String getFridayFrom() {
        return fridayFrom;
    }

    public void setFridayFrom(String fridayFrom) {
        this.fridayFrom = fridayFrom;
    }

    public String getFridayTo() {
        return fridayTo;
    }

    public void setFridayTo(String fridayTo) {
        this.fridayTo = fridayTo;
    }

    public String getSaturdayFrom() {
        return saturdayFrom;
    }

    public void setSaturdayFrom(String saturdayFrom) {
        this.saturdayFrom = saturdayFrom;
    }

    public String getSaturdayTo() {
        return saturdayTo;
    }

    public void setSaturdayTo(String saturdayTo) {
        this.saturdayTo = saturdayTo;
    }

    public String getSundayFrom() {
        return sundayFrom;
    }

    public void setSundayFrom(String sundayFrom) {
        this.sundayFrom = sundayFrom;
    }

    public String getSundayTo() {
        return sundayTo;
    }

    public void setSundayTo(String sundayTo) {
        this.sundayTo = sundayTo;
    }
}
