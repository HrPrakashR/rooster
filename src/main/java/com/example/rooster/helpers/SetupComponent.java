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
import java.util.Random;

/**
 * Component to create initial data for the application.
 * Created data is stored in the database
 */
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
    private final long employeeId = 0;

    public SetupComponent(EmployeeRepository employeeRepository, TeamController teamController, TeamRepository teamRepository, PeriodController periodController, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.teamController = teamController;
        this.teamRepository = teamRepository;
        this.periodController = periodController;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        /**
         * Creating teams with random attributes
         */
        for (int i = 0; i < maxTeamId; i++) {

            TeamDTO team = new TeamDTO();
            team.setName(teamNames.get(i));
            team.setRestHours(10 + i % 4);
            team.setRestDays(2);
            team.setMinBreakTime(0.25 * (i % 3) + 0.5);

            team.setMondayFrom(i % 2 == 0 ? setNullShiftTime() : setRandomShiftBegin(i));
            team.setMondayTo(i % 2 == 0 ? setNullShiftTime() : setRandomShiftEnd(i));

            team.setTuesdayFrom(setRandomShiftBegin(i));
            team.setTuesdayTo(setRandomShiftEnd(i));

            team.setWednesdayFrom(setRandomShiftBegin(i));
            team.setWednesdayTo(setRandomShiftEnd(i));

            team.setThursdayFrom(setRandomShiftBegin(i));
            team.setThursdayTo(setRandomShiftEnd(i));

            team.setFridayFrom(setRandomShiftBegin(i));
            team.setFridayTo(setRandomShiftEnd(i));

            team.setSaturdayFrom(i % 2 == 1 ? setNullShiftTime() : setRandomShiftBegin(i));
            team.setSaturdayTo(i % 2 == 1 ? setNullShiftTime() : setRandomShiftEnd(i));

            team.setSundayFrom(setNullShiftTime());
            team.setSundayTo(setNullShiftTime());

            this.teamController.newTeam(team);
        }

        /**
         * Creating employees with random attributes
         */
        for (int i = 0; i < this.names.size(); i++) {
            Employee employee = new Employee();
            Team team = this.teamRepository.getById((1 + i / 5L));
            String firstName = names.get(i);
            employee.setFirstName(firstName);
            employee.setLastName(lastNames.get(i));
            employee.setEmail(firstName + "@rooster.bestapp");
            employee.setTeam(team);
            employee.setBreakTime(0.5 + 0.25 * (i % 3));
            employee.setHoursPerWeek(15 + 5 * (i % 6));
            employee.setBalanceHours(i % 2 == 0 ? 5 * (i % 5) : -5 * (i / 5));
            employee.setRole(getNextRole(i));
            String password = "rooster123";
            String encodedPassword = passwordEncoder.encode(password);
            employee.setPassword(encodedPassword);
            this.employeeRepository.save(employee);
        }

        /**
         * Creating periods with random attributes
         */
        for (long i = 6; i <= 30; i++) {
            for (int j = 0; j <= 30; j++) {
                generatePeriodDTO(employeeRepository.getById(i), j);
            }
        }
    }

    private void generatePeriodDTO(Employee employee, int day) {
        PeriodDTO period = new PeriodDTO();
        period.setPurpose(getNextPurpose((int) employee.getId(), day));
        period.setDateFrom(String.format("%04d-%02d-%02dT%02d:%02d",
                2022,
                4,
                day,
                8 + ((employee.getId() * day) % 3),
                15 * ((employee.getId() * day) % 4)));
        period.setDateTo(String.format("%04d-%02d-%02dT%02d:%02d",
                2022,
                4,
                day,
                16 + ((employee.getId() * day) % 3),
                15 * ((employee.getId() * day + 2) % 4)));
        period.setEmployee(employee.getId());
        if (((employee.getId() * 4 + day * 7L)) % 9 < 1) {
            this.periodController.submitPeriodRequest(period);
        }
    }

    /**
     * Assigning the next purpose for period creation.
     * ~16% Vacation, ~32% Working hours, ~16% Free time request, ~32% Vacation request
     */
    private String getNextPurpose(int id, int day) {
        return switch ((2 * id + 7 * day) % 6) {
            case 0 -> Purpose.CONFIRMED_VACATION.name();
            case 2, 3 -> Purpose.WORKING_HOURS.name();
            case 4 -> Purpose.FREE_TIME_REQUEST.name();
            default -> Purpose.VACATION_REQUEST.name();
        };
    }

    /**
     * Assigning the next role for employee creation.
     * First employee is the owner.
     * First employee of the teams are managers.
     * Last employee of each team is a trainee.
     * The other employees are staff.
     */
    private Role getNextRole(int i) {
        if (i == 0) {
            return Role.OWNER;
        } else if (i == 1 || i % 5 == 0) {
            return Role.MANAGER;
        } else if (i % 5 == 4) {
            return Role.TRAINEE;
        } else {
            return Role.STAFF;
        }
    }

    /**
     * Assigning a random beginning time for the shift
     * @param i receives team id as random generator
     * @return time in hours and minutes as string
     */
    public String setRandomShiftBegin(int i) {
        return String.format("%02d:%02d", 9 - i % 2, 15 * (i % 4));
    }

    /**
     * Assigning a random ending time for the shift
     * @param i receives team id as random generator
     * @return time in hours and minutes as string
     */
    public String setRandomShiftEnd(int i) {
        return String.format("%02d:%02d", 18 - i % 2, 15 * (i % 4));
    }

    /**
     * Assigning the shift beginning and ending as 0 o'clock for free days
     * @return null o'clock as string
     */
    public String setNullShiftTime() {
        return String.format("%02d:%02d", 0, 0);
    }
}

