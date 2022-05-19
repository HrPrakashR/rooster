package com.example.rooster.helpers;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeRepository;
import com.example.rooster.employee.Role;
import com.example.rooster.period.PeriodController;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.Purpose;
import com.example.rooster.team.Team;
import com.example.rooster.team.TeamController;
import com.example.rooster.team.TeamDTO;
import com.example.rooster.team.TeamRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Component
public class SetupComponent implements ApplicationListener<ApplicationReadyEvent> {

    private final EmployeeRepository employeeRepository;
    private final TeamController teamController;
    private final TeamRepository teamRepository;
    private final PeriodController periodController;
    private final PasswordEncoder passwordEncoder;

    private long employeeId = 0;

    public SetupComponent(EmployeeRepository employeeRepository, TeamController teamController, TeamRepository teamRepository, PeriodController periodController, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.teamController = teamController;
        this.teamRepository = teamRepository;
        this.periodController = periodController;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        int maxTeamId = 9;

        IntStream.rangeClosed(0, maxTeamId).forEachOrdered(i -> this.generateRandomTeam());

        for (int i = 1; i <= maxTeamId; i++) {
            for (int n = 1; n <= (new Random()).nextInt(5, 12); n++) {
                generateRandomEmployee(i);
            }
        }

        this.employeeRepository.findAll().forEach(employee -> {
                    for (int day = 0; day <= 30; day++) {
                        this.generateRandomPeriodDTO(employee, day);
                    }
                }
        );
    }

    private void generateRandomPeriodDTO(Employee employee, int day) {
        Random random = new Random();
        PeriodDTO period = new PeriodDTO();
        period.setPurpose(getRandomPurpose());
        period.setDateFrom((String.format("%04d-%02d-%02dT%02d:%02d",
                2022,
                4,
                day,
                random.nextInt(0, 12),
                random.nextInt(0, 59))));
        period.setDateTo((String.format("%04d-%02d-%02dT%02d:%02d",
                2022,
                4,
                day,
                random.nextInt(12, 23),
                random.nextInt(0, 59))));
        period.setEmployee(employee.getId());

        if ((new Random()).nextInt(30) >= 20) {
            this.periodController.submitPeriodRequest(period);
        }
    }

    private String getRandomPurpose() {
        int x = (new Random()).nextInt(Purpose.class.getEnumConstants().length);
        return Purpose.class.getEnumConstants()[x].name();
    }

    private Role getRandomRole() {
        int x = (new Random()).nextInt(Role.class.getEnumConstants().length);
        return Role.class.getEnumConstants()[x];
    }

    private void generateRandomEmployee(long teamId) {
        Employee employee = new Employee();
        employee.setId(this.employeeId++);
        Team team = this.teamRepository.getById(teamId);
        String firstName = nameGenerator();
        employee.setFirstName(firstName);
        employee.setLastName(nameGenerator());
        employee.setEmail(firstName + "@rooster.bestapp");
        employee.setTeam(team);
        employee.setBreakTime((new Random()).nextDouble(0.5, 2));
        employee.setHoursPerWeek((new Random()).nextInt(10, 40));
        employee.setBalanceHours((new Random()).nextInt(-20, 20));
        employee.setRole(getRandomRole());
        String password = "rooster123";
        String encodedPassword = passwordEncoder.encode(password);
        employee.setPassword(encodedPassword);
        this.employeeRepository.save(employee);
    }

    private String nameGenerator() {
        StringBuilder new_Name = new StringBuilder();
        String odd_Consonants = "bcdfghjklmnprstvwxz";
        String even_Vowels = "aeiou";
        int lengthSelector = ThreadLocalRandom.current().nextInt(5, 11 + 1);
        for (int max_Name = 0; max_Name <= lengthSelector; max_Name++) {
            if (max_Name % 2 == 0) {
                int randomChar = (int) (Math.random() * even_Vowels.length());
                new_Name.append(even_Vowels.charAt(randomChar));
            } else {
                int randomChar = (int) (Math.random() * odd_Consonants.length());
                new_Name.append(odd_Consonants.charAt(randomChar));
            }
        }
        return new_Name.substring(0, 1).toUpperCase() + new_Name.substring(1);
    }

    private void generateRandomTeam() {
        TeamDTO team = new TeamDTO();
        Random random = new java.util.Random();
        team.setName("Team_" + random.nextInt(0, 10000));
        team.setRestHours(random.nextDouble(10, 14));
        team.setRestDays(random.nextInt(1, 4));
        team.setMinBreakTime(random.nextDouble(0.5, 2));

        if (1 != random.nextInt(7) + 1) {
            team.setMondayFrom(String.format("%02d:%02d", random.nextInt(0, 12), random.nextInt(0, 59)));
            team.setMondayTo(String.format("%02d:%02d", random.nextInt(13, 23), random.nextInt(0, 59)));
        }

        if (1 != random.nextInt(7) + 1) {
            team.setTuesdayFrom(String.format("%02d:%02d", random.nextInt(0, 12), random.nextInt(0, 59)));
            team.setTuesdayTo(String.format("%02d:%02d", random.nextInt(13, 23), random.nextInt(0, 59)));
        }

        if (1 != random.nextInt(7) + 1) {
            team.setWednesdayFrom(String.format("%02d:%02d", random.nextInt(0, 12), random.nextInt(0, 59)));
            team.setWednesdayTo(String.format("%02d:%02d", random.nextInt(13, 23), random.nextInt(0, 59)));
        }

        if (1 != random.nextInt(7) + 1) {
            team.setThursdayFrom(String.format("%02d:%02d", random.nextInt(0, 12), random.nextInt(0, 59)));
            team.setThursdayTo(String.format("%02d:%02d", random.nextInt(13, 23), random.nextInt(0, 59)));
        }

        if (1 != random.nextInt(7) + 1) {
            team.setFridayFrom(String.format("%02d:%02d", random.nextInt(0, 12), random.nextInt(0, 59)));
            team.setFridayTo(String.format("%02d:%02d", random.nextInt(13, 23), random.nextInt(0, 59)));
        }

        if (1 != random.nextInt(7) + 1) {
            team.setSaturdayFrom(String.format("%02d:%02d", random.nextInt(0, 12), random.nextInt(0, 59)));
            team.setSaturdayTo(String.format("%02d:%02d", random.nextInt(13, 23), random.nextInt(0, 59)));
        }

        if (1 != random.nextInt(7) + 1) {
            team.setSundayFrom(String.format("%02d:%02d", random.nextInt(0, 12), random.nextInt(0, 59)));
            team.setSundayTo(String.format("%02d:%02d", random.nextInt(13, 23), random.nextInt(0, 59)));
        }


        this.teamController.newTeam(team);
    }
}