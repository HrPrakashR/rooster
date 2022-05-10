package com.example.rooster.team;

import javax.persistence.*;

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

    public Team() {}



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}