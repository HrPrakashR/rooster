package com.example.rooster.team;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeRepository;
import com.example.rooster.helpers.DateWorker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;

    public TeamService(TeamRepository teamRepository, EmployeeRepository employeeRepository) {
        this.teamRepository = teamRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<TeamDTO> getTeams() {
        List<Team> teams = this.teamRepository.findAll();
        List<TeamDTO> teamDTOs = new ArrayList<>();
        teams.forEach((team) -> teamDTOs.add(this.convertToTeamDTO(team)));
        return teamDTOs;
    }

    public TeamDTO convertToTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setRestHours(team.getRestHours());
        teamDTO.setRestDays(team.getRestDays());
        teamDTO.setMinBreakTime(team.getMinBreakTime());

        if (teamDTO.getMondayFrom() != null && teamDTO.getMondayTo() != null) {
            teamDTO.setMondayFrom(
                    String.valueOf(DateWorker.getCalendarObject(team.getMondayFrom()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getMondayFrom()).get(Calendar.HOUR_OF_DAY))
            );
            teamDTO.setMondayTo(
                    String.valueOf(DateWorker.getCalendarObject(team.getMondayTo()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getMondayTo()).get(Calendar.HOUR_OF_DAY))
            );
        } else {
            teamDTO.setMondayFrom("");
            teamDTO.setMondayTo("");
        }


        if (teamDTO.getTuesdayFrom() != null && teamDTO.getTuesdayTo() != null) {
            teamDTO.setTuesdayFrom(
                    String.valueOf(DateWorker.getCalendarObject(team.getTuesdayFrom()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getMondayFrom()).get(Calendar.HOUR_OF_DAY))
            );
            teamDTO.setTuesdayTo(
                    String.valueOf(DateWorker.getCalendarObject(team.getTuesdayTo()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getTuesdayTo()).get(Calendar.HOUR_OF_DAY))
            );
        } else {
            teamDTO.setTuesdayFrom("");
            teamDTO.setTuesdayTo("");
        }

        if (teamDTO.getWednesdayFrom() != null && teamDTO.getWednesdayTo() != null) {
            teamDTO.setWednesdayFrom(
                    String.valueOf(DateWorker.getCalendarObject(team.getWednesdayFrom()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getWednesdayFrom()).get(Calendar.HOUR_OF_DAY))
            );
            teamDTO.setWednesdayTo(
                    String.valueOf(DateWorker.getCalendarObject(team.getWednesdayTo()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getWednesdayTo()).get(Calendar.HOUR_OF_DAY))
            );
        } else {
            teamDTO.setWednesdayFrom("");
            teamDTO.setWednesdayTo("");
        }

        if (teamDTO.getThursdayFrom() != null && teamDTO.getThursdayTo() != null) {
            teamDTO.setThursdayFrom(
                    String.valueOf(DateWorker.getCalendarObject(team.getThursdayFrom()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getThursdayFrom()).get(Calendar.HOUR_OF_DAY))
            );
            teamDTO.setThursdayTo(
                    String.valueOf(DateWorker.getCalendarObject(team.getThursdayTo()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getThursdayTo()).get(Calendar.HOUR_OF_DAY))
            );
        } else {
            teamDTO.setThursdayFrom("");
            teamDTO.setThursdayTo("");
        }

        if (teamDTO.getFridayFrom() != null && teamDTO.getFridayTo() != null) {
            teamDTO.setFridayFrom(
                    String.valueOf(DateWorker.getCalendarObject(team.getFridayFrom()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getFridayFrom()).get(Calendar.HOUR_OF_DAY))
            );
            teamDTO.setFridayTo(
                    String.valueOf(DateWorker.getCalendarObject(team.getFridayTo()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getFridayTo()).get(Calendar.HOUR_OF_DAY))
            );
        } else {
            teamDTO.setFridayFrom("");
            teamDTO.setFridayTo("");
        }

        if (teamDTO.getSaturdayFrom() != null && teamDTO.getSaturdayTo() != null) {
            teamDTO.setSaturdayFrom(
                    String.valueOf(DateWorker.getCalendarObject(team.getSaturdayFrom()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getSaturdayFrom()).get(Calendar.HOUR_OF_DAY))
            );
            teamDTO.setSaturdayTo(
                    String.valueOf(DateWorker.getCalendarObject(team.getSaturdayTo()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getSaturdayTo()).get(Calendar.HOUR_OF_DAY))
            );
        } else {
            teamDTO.setSaturdayFrom("");
            teamDTO.setSaturdayTo("");
        }

        if (teamDTO.getSundayFrom() != null && teamDTO.getSundayTo() != null) {
            teamDTO.setSundayFrom(
                    String.valueOf(DateWorker.getCalendarObject(team.getSundayFrom()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getSundayFrom()).get(Calendar.HOUR_OF_DAY))
            );
            teamDTO.setSundayTo(
                    String.valueOf(DateWorker.getCalendarObject(team.getSundayTo()).get(Calendar.MINUTE) + ':' +
                            DateWorker.getCalendarObject(team.getSundayTo()).get(Calendar.HOUR_OF_DAY))
            );
        } else {
            teamDTO.setSundayFrom("");
            teamDTO.setSundayTo("");
        }

        return teamDTO;
    }

    public Team convertToTeam(TeamDTO teamDTO) {
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setName(teamDTO.getName());
        team.setRestHours(teamDTO.getRestHours());
        team.setRestDays(teamDTO.getRestDays());
        team.setMinBreakTime(teamDTO.getMinBreakTime());

        if (
                teamDTO.getMondayFrom() != null &&
                        teamDTO.getMondayTo() != null &&
                        !teamDTO.getMondayFrom().isEmpty() &&
                        !teamDTO.getMondayTo().isEmpty() &&
                        !teamDTO.getMondayFrom().equals(teamDTO.getMondayTo()) &&
                        !teamDTO.getMondayFrom().isBlank() &&
                        !teamDTO.getMondayTo().isBlank()

        ) {
            team.setMondayFrom(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getMondayFrom().substring(3, 4)),
                            Integer.parseInt(teamDTO.getMondayFrom().substring(0, 1)))
            );
            team.setMondayTo(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getMondayTo().substring(3, 4)),
                            Integer.parseInt(teamDTO.getMondayTo().substring(0, 1)))
            );
        }

        if (
                teamDTO.getTuesdayFrom() != null &&
                        teamDTO.getTuesdayTo() != null &&
                        !teamDTO.getTuesdayFrom().isEmpty() &&
                        !teamDTO.getTuesdayTo().isEmpty() &&
                        !teamDTO.getTuesdayFrom().equals(teamDTO.getTuesdayTo()) &&
                        !teamDTO.getTuesdayFrom().isBlank() &&
                        !teamDTO.getTuesdayTo().isBlank()
        ) {
            team.setTuesdayFrom(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getTuesdayFrom().substring(3, 4)),
                            Integer.parseInt(teamDTO.getTuesdayFrom().substring(0, 1)))
            );
            team.setTuesdayTo(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getTuesdayTo().substring(3, 4)),
                            Integer.parseInt(teamDTO.getTuesdayTo().substring(0, 1)))
            );
        }


        if (
                teamDTO.getWednesdayFrom() != null &&
                        teamDTO.getWednesdayTo() != null &&
                        !teamDTO.getWednesdayFrom().isEmpty() &&
                        !teamDTO.getWednesdayTo().isEmpty() &&
                        !teamDTO.getWednesdayFrom().equals(teamDTO.getWednesdayTo()) &&
                        !teamDTO.getWednesdayFrom().isBlank() &&
                        !teamDTO.getWednesdayTo().isBlank()
        ) {
            team.setWednesdayFrom(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getWednesdayFrom().substring(3, 4)),
                            Integer.parseInt(teamDTO.getWednesdayFrom().substring(0, 1)))
            );

            team.setWednesdayTo(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getWednesdayTo().substring(3, 4)),
                            Integer.parseInt(teamDTO.getWednesdayTo().substring(0, 1)))
            );
        }

        if (
                teamDTO.getThursdayFrom() != null &&
                        teamDTO.getThursdayTo() != null &&
                        !teamDTO.getThursdayTo().isEmpty() &&
                        !teamDTO.getThursdayFrom().isEmpty() &&
                        !teamDTO.getThursdayFrom().equals(teamDTO.getThursdayTo()) &&
                        !teamDTO.getThursdayFrom().isBlank() &&
                        !teamDTO.getThursdayTo().isBlank()
        ) {
            team.setThursdayFrom(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getThursdayFrom().substring(3, 4)),
                            Integer.parseInt(teamDTO.getThursdayFrom().substring(0, 1)))
            );

            team.setThursdayTo(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getThursdayTo().substring(3, 4)),
                            Integer.parseInt(teamDTO.getThursdayTo().substring(0, 1)))
            );
        }

        if (
                teamDTO.getFridayFrom() != null &&
                        teamDTO.getFridayTo() != null &&
                        !teamDTO.getFridayFrom().isEmpty() &&
                        !teamDTO.getFridayTo().isEmpty() &&
                        !teamDTO.getFridayFrom().equals(teamDTO.getFridayTo()) &&
                        !teamDTO.getFridayFrom().isBlank() &&
                        !teamDTO.getFridayTo().isBlank()
        ) {
            team.setFridayFrom(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getFridayFrom().substring(3, 4)),
                            Integer.parseInt(teamDTO.getFridayFrom().substring(0, 1)))
            );

            team.setFridayTo(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getFridayTo().substring(3, 4)),
                            Integer.parseInt(teamDTO.getFridayTo().substring(0, 1)))
            );
        }

        if (
                teamDTO.getSaturdayFrom() != null &&
                        teamDTO.getSaturdayTo() != null &&
                        !teamDTO.getSaturdayFrom().isEmpty() &&
                        !teamDTO.getSaturdayTo().isEmpty() &&
                        !teamDTO.getSaturdayFrom().equals(teamDTO.getSaturdayTo()) &&
                        !teamDTO.getSaturdayFrom().isBlank() &&
                        !teamDTO.getSaturdayTo().isBlank()
        ) {
            team.setSaturdayFrom(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getSaturdayFrom().substring(3, 4)),
                            Integer.parseInt(teamDTO.getSaturdayFrom().substring(0, 1)))
            );

            team.setSaturdayTo(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getSaturdayTo().substring(3, 4)),
                            Integer.parseInt(teamDTO.getSaturdayTo().substring(0, 1)))
            );
        }

        if (
                teamDTO.getSundayFrom() != null &&
                        teamDTO.getSundayTo() != null &&
                        !teamDTO.getSundayFrom().isEmpty() &&
                        !teamDTO.getSundayTo().isEmpty() &&
                        !teamDTO.getSundayFrom().isBlank() &&
                        !teamDTO.getSundayTo().isBlank() &&
                        !teamDTO.getSundayFrom().equals(teamDTO.getSundayTo())

        ) {
            team.setSundayFrom(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getSundayFrom().substring(3, 4)),
                            Integer.parseInt(teamDTO.getSundayFrom().substring(0, 1)))
            );

            team.setSundayTo(
                    DateWorker.getDateObject(0,
                            Integer.parseInt(teamDTO.getSundayTo().substring(3, 4)),
                            Integer.parseInt(teamDTO.getSundayTo().substring(0, 1)))
            );
        }

        return team;
    }

    public Team getTeam(long id) {
        return this.teamRepository.findTeamById(id);
    }

    public Optional<Team> getTeamById(long id) {
        return this.teamRepository.findById(id);
    }

    public Team setTeam(Team team) {
        return this.teamRepository.save(team);
    }

    public ResponseEntity<String> deleteTeam(long id) {
        this.teamRepository.deleteById((this.teamRepository.getById(id).getId()));
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    public void neutralizeEmployeeTeam(Team team) {
        List<Employee> listOfEmployees = this.employeeRepository.findAllByTeam(team);
        listOfEmployees.forEach(e -> e.setTeam(null));
    }

    public Team updateTeam(Team team) {
        if (team != null && !Objects.isNull(this.getTeamById(team.getId()))) {
            this.teamRepository.save(team);
        }
        throw new RuntimeException("Cannot find Team");
    }
}
