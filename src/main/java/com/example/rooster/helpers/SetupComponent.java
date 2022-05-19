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
        if (this.teamRepository.count() <= 5) {
            TeamDTO team1 =new TeamDTO();
            team1.setName("Sales");
            team1.setRestHours(12);
            team1. setRestDays(2);
            team1.setMinBreakTime(1);
            team1.setMondayFrom("08:30");
            team1.setMondayTo("17:00");
            team1.setTuesdayFrom("08:30");
            team1.setTuesdayTo("17:00");
            team1.setWednesdayFrom("08:30");
            team1.setWednesdayTo("17:00");
            team1.setThursdayFrom("08:30");
            team1.setThursdayTo("17:00");
            team1.setFridayFrom("08:30");
            team1.setFridayTo("17:00");
            team1.setSaturdayFrom("08:30");
            team1.setSaturdayTo("17:00");
            team1.setSundayFrom("08:30");
            team1.setSundayTo("17:00");
            this.teamController.newTeam(team1);
            
            TeamDTO team2 =new TeamDTO();
            team2.setName("Marketing");
            team2.setRestHours(12);
            team2. setRestDays(2);
            team2.setMinBreakTime(1);
            team2.setMondayFrom("08:30");
            team2.setMondayTo("17:00");
            team2.setTuesdayFrom("08:30");
            team2.setTuesdayTo("17:00");
            team2.setWednesdayFrom("08:30");
            team2.setWednesdayTo("17:00");
            team2.setThursdayFrom("08:30");
            team2.setThursdayTo("17:00");
            team2.setFridayFrom("08:30");
            team2.setFridayTo("17:00");
            team2.setSaturdayFrom("08:30");
            team2.setSaturdayTo("17:00");
            team2.setSundayFrom("08:30");
            team2.setSundayTo("17:00");
            this.teamController.newTeam(team2);
            
            TeamDTO team3 =new TeamDTO();
            team3.setName("Human Resources");
            team3.setRestHours(12);
            team3. setRestDays(2);
            team3.setMinBreakTime(1);
            team3.setMondayFrom("08:30");
            team3.setMondayTo("17:00");
            team3.setTuesdayFrom("08:30");
            team3.setTuesdayTo("17:00");
            team3.setWednesdayFrom("08:30");
            team3.setWednesdayTo("17:00");
            team3.setThursdayFrom("08:30");
            team3.setThursdayTo("17:00");
            team3.setFridayFrom("08:30");
            team3.setFridayTo("17:00");
            team3.setSaturdayFrom("08:30");
            team3.setSaturdayTo("17:00");
            team3.setSundayFrom("08:30");
            team3.setSundayTo("17:00");
            this.teamController.newTeam(team3);

            TeamDTO team4 =new TeamDTO();
            team4.setName("Operations");
            team4.setRestHours(12);
            team4. setRestDays(2);
            team4.setMinBreakTime(1);
            team4.setMondayFrom("08:30");
            team4.setMondayTo("17:00");
            team4.setTuesdayFrom("08:30");
            team4.setTuesdayTo("17:00");
            team4.setWednesdayFrom("08:30");
            team4.setWednesdayTo("17:00");
            team4.setThursdayFrom("08:30");
            team4.setThursdayTo("17:00");
            team4.setFridayFrom("08:30");
            team4.setFridayTo("17:00");
            team4.setSaturdayFrom("08:30");
            team4.setSaturdayTo("17:00");
            team4.setSundayFrom("08:30");
            team4.setSundayTo("17:00");
            this.teamController.newTeam(team4);

            TeamDTO team5 =new TeamDTO();
            team5.setName("Production");
            team5.setRestHours(12);
            team5. setRestDays(2);
            team5.setMinBreakTime(1);
            team5.setMondayFrom("08:30");
            team5.setMondayTo("17:00");
            team5.setTuesdayFrom("08:30");
            team5.setTuesdayTo("17:00");
            team5.setWednesdayFrom("08:30");
            team5.setWednesdayTo("17:00");
            team5.setThursdayFrom("08:30");
            team5.setThursdayTo("17:00");
            team5.setFridayFrom("08:30");
            team5.setFridayTo("17:00");
            team5.setSaturdayFrom("08:30");
            team5.setSaturdayTo("17:00");
            team5.setSundayFrom("08:30");
            team5.setSundayTo("17:00");
            this.teamController.newTeam(team5);
        }

        if (this.employeeRepository.count() <= 8) { // only create users if the database is empty
            Employee owner = new Employee();
            Team team = this.teamRepository.getById(1L);
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

            EmployeeDTO manager = new EmployeeDTO();
            manager.setFirstName("The");
            manager.setLastName("Manager");
            manager.setEmail("manager@manager.rooster");
            manager.setTeam(1);
            manager.setHoursPerWeek(40);
            manager.setBalanceHours(0);
            manager.setRole("MANAGER");
            this.employeeController.addNewEmployee(manager);

            EmployeeDTO staff1 = new EmployeeDTO();
            staff1.setFirstName("Elias");
            staff1.setLastName("Akbari");
            staff1.setEmail("elias.akbari@staff.rooster");
            staff1.setTeam(1);
            staff1.setHoursPerWeek(40);
            staff1.setBalanceHours(0);
            staff1.setRole("STAFF");
            this.employeeController.addNewEmployee(staff1);

            EmployeeDTO staff2 = new EmployeeDTO();
            staff2.setFirstName("Prakash");
            staff2.setLastName("Rathinasamy");
            staff2.setEmail("prakash.rathinasamy@staff.rooster");
            staff2.setTeam(1);
            staff2.setHoursPerWeek(40);
            staff2.setBalanceHours(0);
            staff2.setRole("STAFF");
            this.employeeController.addNewEmployee(staff2);

            EmployeeDTO staff3 = new EmployeeDTO();
            staff3.setFirstName("Mehmet");
            staff3.setLastName("Katran");
            staff3.setEmail("mehmet.katran@staff.rooster");
            staff3.setTeam(2);
            staff3.setHoursPerWeek(40);
            staff3.setBalanceHours(0);
            staff3.setRole("STAFF");
            this.employeeController.addNewEmployee(staff3);

            EmployeeDTO staff4 = new EmployeeDTO();
            staff4.setFirstName("Pia");
            staff4.setLastName("Stitzing");
            staff4.setEmail("pia.stitzing@staff.rooster");
            staff4.setTeam(2);
            staff4.setHoursPerWeek(40);
            staff4.setBalanceHours(0);
            staff4.setRole("STAFF");
            this.employeeController.addNewEmployee(staff4);
            
            EmployeeDTO staff5 = new EmployeeDTO();
            staff5.setFirstName("Philipp");
            staff5.setLastName("Bomers");
            staff5.setEmail("philipp.bomers@staff.rooster");
            staff5.setTeam(3);
            staff5.setHoursPerWeek(40);
            staff5.setBalanceHours(0);
            staff5.setRole("STAFF");
            this.employeeController.addNewEmployee(staff5);

            EmployeeDTO staff6 = new EmployeeDTO();
            staff6.setFirstName("Georg");
            staff6.setLastName("Brune");
            staff6.setEmail("georg.brune@staff.rooster");
            staff6.setTeam(3);
            staff6.setHoursPerWeek(40);
            staff6.setBalanceHours(0);
            staff6.setRole("STAFF");
            this.employeeController.addNewEmployee(staff6);
        }

        if (this.periodRepository.count() <= 12) {
            PeriodDTO period1 = new PeriodDTO();
            period1.setPurpose("VACATION_REQUEST");
            period1.setDateFrom("2022-05-24T08:30");
            period1.setDateTo("2022-05-25T08:30");
            period1.setEmployee(6);
            this.periodController.submitPeriodRequest(period1);

            PeriodDTO period2 = new PeriodDTO();
            period2.setPurpose("SICK_LEAVE");
            period2.setDateFrom("2022-05-25T08:30");
            period2.setDateTo("2022-05-27T08:30");
            period2.setEmployee(6);
            this.periodController.submitPeriodRequest(period2);

            PeriodDTO period3 = new PeriodDTO();
            period3.setPurpose("CONFIRMED_VACATION");
            period3.setDateFrom("2022-05-24T08:30");
            period3.setDateTo("2022-05-25T08:30");
            period3.setEmployee(7);
            this.periodController.submitPeriodRequest(period3);

            PeriodDTO period4 = new PeriodDTO();
            period4.setPurpose("VACATION_REQUEST");
            period4.setDateFrom("2022-05-30T08:30");
            period4.setDateTo("2022-06-06T08:30");
            period4.setEmployee(7);
            this.periodController.submitPeriodRequest(period4);

            PeriodDTO period5 = new PeriodDTO();
            period5.setPurpose("SICK_LEAVE");
            period5.setDateFrom("2022-05-27T08:30");
            period5.setDateTo("2022-05-28T08:30");
            period5.setEmployee(8);
            this.periodController.submitPeriodRequest(period5);

            PeriodDTO period6 = new PeriodDTO();
            period6.setPurpose("WORKING_HOURS");
            period6.setDateFrom("2022-05-30T08:30");
            period6.setDateTo("2022-05-31T08:30");
            period6.setEmployee(8);
            this.periodController.submitPeriodRequest(period6);

            PeriodDTO period7 = new PeriodDTO();
            period7.setPurpose("VACATION_REQUEST");
            period7.setDateFrom("2022-06-20T08:30");
            period7.setDateTo("2022-06-27T08:30");
            period7.setEmployee(9);
            this.periodController.submitPeriodRequest(period7);

            PeriodDTO period8 = new PeriodDTO();
            period8.setPurpose("SICK_LEAVE");
            period8.setDateFrom("2022-05-24T08:30");
            period8.setDateTo("2022-05-25T08:30");
            period8.setEmployee(10);
            this.periodController.submitPeriodRequest(period8);

            PeriodDTO period9 = new PeriodDTO();
            period9.setPurpose("SICK_LEAVE");
            period9.setDateFrom("2022-06-27T08:30");
            period9.setDateTo("2022-06-28T08:30");
            period9.setEmployee(7);
            this.periodController.submitPeriodRequest(period9);

            PeriodDTO period10 = new PeriodDTO();
            period10.setPurpose("VACATION_REQUEST");
            period10.setDateFrom("2022-06-15T08:30");
            period10.setDateTo("2022-06-19T08:30");
            period10.setEmployee(11);
            this.periodController.submitPeriodRequest(period10);

            PeriodDTO period11 = new PeriodDTO();
            period11.setPurpose("WORKING_HOURS");
            period11.setDateFrom("2022-06-20T08:30");
            period11.setDateTo("2022-06-21T08:30");
            period11.setEmployee(11);
            this.periodController.submitPeriodRequest(period11);

            PeriodDTO period12 = new PeriodDTO();
            period12.setPurpose("FREE_TIME_REQUEST");
            period12.setDateFrom("2022-06-27T08:30");
            period12.setDateTo("2022-06-30T08:30");
            period12.setEmployee(5);
            this.periodController.submitPeriodRequest(period12);
        }
    }
}