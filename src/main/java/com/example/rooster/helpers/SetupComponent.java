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
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class SetupComponent implements ApplicationListener<ApplicationReadyEvent> {

    private final EmployeeRepository employeeRepository;
    private final TeamController teamController;
    private final TeamRepository teamRepository;
    private final PeriodController periodController;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();
    private final List<String> names = Names.names;
    private final List<String> lastNames = Names.lastNames;
    private final List<String> teamNames = List.of("Sales", "Marketing", "Human Resources", "Production", "Customer Service");
    private final int maxTeamId = this.teamNames.size();
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

        IntStream.rangeClosed(0, maxTeamId - 1).forEachOrdered(this::generateRandomTeam);

        this.generateRandomEmployees();

        this.generateBoss();

        this.generateRandomPeriods();

        this.generateRandomManagers();

    }


    private void generateBoss() {
        Employee boss = new Employee();
        boss.setFirstName("Muster");
        boss.setLastName("Rooster");
        boss.setEmail("muster@rooster.bestapp");
        boss.setBreakTime(0.5);
        boss.setHoursPerWeek(40);
        boss.setBalanceHours(13);
        String password = "rooster123";
        String encodedPassword = passwordEncoder.encode(password);
        boss.setPassword(encodedPassword);
        boss.setRole(Role.OWNER);
        boss.setTeam(this.teamRepository.getById(1L));
        this.employeeRepository.save(boss);
    }

    private void generateRandomManagers() {
        IntStream.rangeClosed(0, this.maxTeamId - 1)
                .mapToObj(this.employeeRepository::findAllByTeamId)
                .map(teamMember -> {
                            if (teamMember.stream().findFirst().isPresent()) {
                                return teamMember.stream().findFirst().get();
                            }
                            return null;
                        }
                )
                .filter(Objects::nonNull)
                .forEach(manager -> {
                    manager.setRole(Role.MANAGER);
                    this.employeeRepository.save(manager);
                });
    }

    private void generateRandomPeriods() {
        this.employeeRepository.findAll().forEach(employee -> IntStream
                .rangeClosed(0, 30)
                .filter(day ->
                        (new Random()).nextInt(0, 30) <= 5)
                .forEachOrdered(day ->
                        this.generateRandomPeriodDTO(employee, day))
        );
    }

    private void generateRandomEmployees() {
        for (int i = 1; i <= this.maxTeamId; i++) {
            for (int n = 1; n <= random.nextInt(5, 10); n++) {
                generateRandomEmployee(i);
            }
        }

    }

    private void generateRandomPeriodDTO(Employee employee, int day) {
        PeriodDTO period = new PeriodDTO();
        period.setPurpose(getRandomPurpose());
        period.setDateFrom((String.format("%04d-%02d-%02dT%02d:%02d",
                2022,
                4,
                day,
                random.nextInt(8, 11),
                15 * random.nextInt(0, 4))));
        period.setDateTo((String.format("%04d-%02d-%02dT%02d:%02d",
                2022,
                4,
                day,
                random.nextInt(16, 18),
                15 * random.nextInt(0, 4))));
        period.setEmployee(employee.getId());

        this.periodController.submitPeriodRequest(period);
    }

    private String getRandomPurpose() {
        return switch (random.nextInt(10)) {
            case 7 -> Purpose.SICK_LEAVE.name();
            case 8 -> Purpose.CONFIRMED_VACATION.name();
            case 9 -> Purpose.VACATION_REQUEST.name();
            default -> Purpose.WORKING_HOURS.name();
        };
    }

    private Role getRandomRole() {
        return random.nextInt(4) == 0 ? Role.TRAINEE : Role.STAFF;
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
        employee.setBreakTime(0.25 * (random.nextInt(2, 5)));
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
        team.setMinBreakTime(0.25 * (random.nextInt(2, 5)));

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
        return String.format("%02d:%02d", random.nextInt(8, 10), 15 * random.nextInt(0, 4));
    }

    public String setRandomShiftEnd() {
        return String.format("%02d:%02d", random.nextInt(16, 18), 15 * random.nextInt(0, 4));
    }
}