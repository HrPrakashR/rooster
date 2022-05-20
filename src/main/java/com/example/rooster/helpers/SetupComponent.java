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

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class SetupComponent implements ApplicationListener<ApplicationReadyEvent> {

    private final EmployeeRepository employeeRepository;
    private final TeamController teamController;
    private final TeamRepository teamRepository;
    private final PeriodController periodController;
    private final PasswordEncoder passwordEncoder;

    private long employeeId = 0;

    private Random random = new Random();

    private List<String> names = Names.names;
    private List<String> lastNames = Names.lastNames;
    private List<String> teamNames = List.of("Sales", "Marketing", "Human Resources", "Production", "Customer Service");

    public SetupComponent(EmployeeRepository employeeRepository, TeamController teamController, TeamRepository teamRepository, PeriodController periodController, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.teamController = teamController;
        this.teamRepository = teamRepository;
        this.periodController = periodController;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        int maxTeamId = teamNames.size();

        IntStream.rangeClosed(0, maxTeamId - 1).forEachOrdered(i -> this.generateRandomTeam(i));

        for (int i = 1; i <= maxTeamId; i++) {
            for (int n = 1; n <= random.nextInt(5, 10); n++) {
                generateRandomEmployee(i);
            }
        }

        this.employeeRepository.findAll().forEach(employee -> {
                    for (int day = 0; day <= 30; day++) {
                        this.generateRandomPeriodDTO(employee, day);
                    }
                }
        );

        for (int i = 1; i < 6; i++) {
            List<Employee> teamMembers = this.employeeRepository.findAllByTeamId(i);
            Employee manager = teamMembers.stream().findFirst().get();
            manager.setRole(Role.MANAGER);
            this.employeeRepository.save(manager);
        }

        Employee boss = this.employeeRepository.findAll().stream().findFirst().get();
        boss.setRole(Role.OWNER);
        this.employeeRepository.save(boss);

    }

    private void generateRandomPeriodDTO(Employee employee, int day) {
        PeriodDTO period = new PeriodDTO();
        period.setPurpose(getRandomPurpose());
        period.setDateFrom((String.format("%04d-%02d-%02dT%02d:%02d",
                2022,
                4,
                day,
                random.nextInt(8, 11),
                30 * random.nextInt(0, 2))));
        period.setDateTo((String.format("%04d-%02d-%02dT%02d:%02d",
                2022,
                4,
                day,
                random.nextInt(16, 18),
                30 * random.nextInt(0, 2))));
        period.setEmployee(employee.getId());

        this.periodController.submitPeriodRequest(period);
    }

    private String getRandomPurpose() {
        int x = random.nextInt(10);

        switch (x) {
            case 0, 1, 2, 3, 4, 5, 6:
                return Purpose.WORKING_HOURS.name();
            case 7:
                return Purpose.SICK_LEAVE.name();
            case 8:
                return Purpose.CONFIRMED_VACATION.name();
            case 9:
                return Purpose.VACATION_REQUEST.name();
            default:
                return Purpose.SCHEDULED_WORKING_HOURS.name();
        }
    }

    private Role getRandomRole() {
        int x = random.nextInt(4);

        switch (x) {
            case 0:
                return Role.TRAINEE;
            default:
                return Role.STAFF;
        }
    }

    private void generateRandomEmployee(long teamId) {
        Employee employee = new Employee();
        employee.setId(this.employeeId++);
        Team team = this.teamRepository.getById(teamId);
        String firstName = names.get(random.nextInt(names.size()));
        employee.setFirstName(firstName);
        employee.setLastName(lastNames.get(random.nextInt(lastNames.size())));
        employee.setEmail(firstName + "@rooster.bestapp");
        employee.setTeam(team);
        employee.setBreakTime(0.5 * (random.nextInt(1, 5)));
        employee.setHoursPerWeek(5 * (random.nextInt(2, 9)));
        employee.setBalanceHours(random.nextInt(-20, 21));
        employee.setRole(getRandomRole());
        String password = "rooster123";
        String encodedPassword = passwordEncoder.encode(password);
        employee.setPassword(encodedPassword);
        this.employeeRepository.save(employee);
    }

    private void generateRandomTeam(int i) {
        TeamDTO team = new TeamDTO();
        team.setName(teamNames.get(i));
        team.setRestHours(random.nextInt(10, 14));
        team.setRestDays(random.nextInt(1, 4));
        team.setMinBreakTime(0.5 * (random.nextInt(1, 5)));

        team.setMondayFrom(setRandomShiftBegin());
        team.setMondayTo(setRandomShiftEnd());

        team.setTuesdayFrom(setRandomShiftBegin());
        team.setTuesdayTo(setRandomShiftEnd());

        team.setWednesdayFrom(setRandomShiftBegin());
        team.setWednesdayTo(setRandomShiftEnd());

        team.setThursdayFrom(setRandomShiftBegin());
        team.setThursdayTo(setRandomShiftEnd());

        team.setFridayFrom(setRandomShiftBegin());
        team.setFridayTo(setRandomShiftEnd());

        team.setSaturdayFrom(setRandomShiftBegin());
        team.setSaturdayTo(setRandomShiftEnd());

        team.setSundayFrom("00:00");
        team.setSundayTo("00:00");


        this.teamController.newTeam(team);
    }

    public String setRandomShiftBegin() {
        return String.format("%02d:%02d", random.nextInt(8, 10), 30 * random.nextInt(0, 2));
    }

    public String setRandomShiftEnd() {
        return String.format("%02d:%02d", random.nextInt(16, 18), 30 * random.nextInt(0, 2));
    }
}