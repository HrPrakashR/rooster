package com.example.rooster.helpers;

import com.example.rooster.employee.*;

import com.example.rooster.period.PeriodController;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.PeriodRepository;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamController;
import com.example.rooster.team.TeamDTO;
import com.example.rooster.team.TeamRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class SetupComponent implements ApplicationListener<ApplicationReadyEvent> {

    private final EmployeeController employeeController;
    private final EmployeeRepository employeeRepository;
    private final TeamController teamController;
    private final TeamRepository teamRepository;
    private final PeriodController periodController;
    private final PeriodRepository periodRepository;
    private final PasswordEncoder passwordEncoder;


    public SetupComponent(EmployeeController employeeController, EmployeeRepository employeeRepository, TeamController teamController, TeamRepository teamRepository, PeriodController periodController, PeriodRepository periodRepository, PasswordEncoder passwordEncoder) {
        this.employeeController = employeeController;
        this.employeeRepository = employeeRepository;
        this.teamController = teamController;
        this.teamRepository = teamRepository;
        this.periodController = periodController;
        this.periodRepository = periodRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        for(int i=0; i<20; i++) {
            this.generateRandomTeam();
        }

            Employee owner = new Employee();
            Team team = this.teamRepository.getById(1L);
            owner.setId(0);
            owner.setFirstName("The");
            owner.setLastName("Owner");
            owner.setEmail("owner@owner.rooster");
            owner.setTeam(team);
            owner.setHoursPerWeek(40);
            owner.setBalanceHours(0);
            owner.setRole(Role.OWNER);
            String password = "owner123";
            String encodedPassword = passwordEncoder.encode(password);
            owner.setPassword(encodedPassword);
            this.employeeRepository.save(owner);


            PeriodDTO period1 = new PeriodDTO();
            period1.setPurpose("VACATION_REQUEST");
            period1.setDateFrom("2022-05-24T08:30");
            period1.setDateTo("2022-05-25T08:30");
            period1.setEmployee(owner.getId());
            this.periodController.submitPeriodRequest(period1);

    }

    private void generateRandomTeam(){
        TeamDTO team1 = new TeamDTO();
        Random random = new java.util.Random();
        team1.setName("Team_"+random.nextInt(0,10000));
        team1.setRestHours(random.nextDouble(10,14));
        team1.setRestDays(random.nextInt(1,4));
        team1.setMinBreakTime(random.nextDouble(0.5,2));

        if(1 != random.nextInt(7)+1){
            team1.setMondayFrom(String.format("%02d:%02d",random.nextInt(0,12),random.nextInt(0,59)));
            team1.setMondayTo(String.format("%02d:%02d", random.nextInt(13,23),random.nextInt(0,59)));
        }

        if(1 != random.nextInt(7)+1){
            team1.setTuesdayFrom(String.format("%02d:%02d",random.nextInt(0,12),random.nextInt(0,59)));
            team1.setTuesdayTo(String.format("%02d:%02d", random.nextInt(13,23),random.nextInt(0,59)));
        }

        if(1 != random.nextInt(7)+1){
            team1.setWednesdayFrom(String.format("%02d:%02d",random.nextInt(0,12),random.nextInt(0,59)));
            team1.setWednesdayTo(String.format("%02d:%02d", random.nextInt(13,23),random.nextInt(0,59)));
        }

        if(1 != random.nextInt(7)+1){
            team1.setThursdayFrom(String.format("%02d:%02d",random.nextInt(0,12),random.nextInt(0,59)));
            team1.setThursdayTo(String.format("%02d:%02d", random.nextInt(13,23),random.nextInt(0,59)));
        }

        if(1 != random.nextInt(7)+1){
            team1.setFridayFrom(String.format("%02d:%02d",random.nextInt(0,12),random.nextInt(0,59)));
            team1.setFridayTo(String.format("%02d:%02d", random.nextInt(13,23),random.nextInt(0,59)));
        }

        if(1 != random.nextInt(7)+1){
            team1.setSaturdayFrom(String.format("%02d:%02d",random.nextInt(0,12),random.nextInt(0,59)));
            team1.setSaturdayTo(String.format("%02d:%02d", random.nextInt(13,23),random.nextInt(0,59)));
        }

        if(1 != random.nextInt(7)+1){
            team1.setSundayFrom(String.format("%02d:%02d",random.nextInt(0,12),random.nextInt(0,59)));
            team1.setSundayTo(String.format("%02d:%02d", random.nextInt(13,23),random.nextInt(0,59)));
        }


        this.teamController.newTeam(team1);
    }
}