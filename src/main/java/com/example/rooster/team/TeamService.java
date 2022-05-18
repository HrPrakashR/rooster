package com.example.rooster.team;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeRepository;
import com.example.rooster.helpers.DateWorker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if (team.getMondayFrom() != null && team.getMondayTo() != null) {
            teamDTO.setMondayFrom(DateWorker.convertDateToTimeString(team.getMondayFrom()));
            teamDTO.setMondayTo(DateWorker.convertDateToTimeString(team.getMondayTo()));
        }

        if (team.getTuesdayFrom() != null && team.getTuesdayTo() != null) {
            teamDTO.setTuesdayFrom(DateWorker.convertDateToTimeString(team.getTuesdayFrom()));
            teamDTO.setTuesdayTo(DateWorker.convertDateToTimeString(team.getTuesdayTo()));
        }

        if (team.getWednesdayFrom() != null && team.getWednesdayTo() != null) {
            teamDTO.setWednesdayFrom(DateWorker.convertDateToTimeString(team.getWednesdayFrom()));
            teamDTO.setWednesdayTo(DateWorker.convertDateToTimeString(team.getWednesdayTo()));
        }

        if (team.getThursdayFrom() != null && team.getThursdayTo() != null) {
            teamDTO.setThursdayFrom(DateWorker.convertDateToTimeString(team.getThursdayFrom()));
            teamDTO.setThursdayTo(DateWorker.convertDateToTimeString(team.getThursdayTo()));
        }

        if (team.getFridayFrom() != null && team.getFridayTo() != null) {
            teamDTO.setFridayFrom(DateWorker.convertDateToTimeString(team.getFridayFrom()));
            teamDTO.setFridayTo(DateWorker.convertDateToTimeString(team.getFridayTo()));
        }

        if (team.getSaturdayFrom() != null && team.getSaturdayTo() != null) {
            teamDTO.setSaturdayFrom(DateWorker.convertDateToTimeString(team.getSaturdayFrom()));
            teamDTO.setSaturdayTo(DateWorker.convertDateToTimeString(team.getSaturdayTo()));
        }

        if (team.getSundayFrom() != null && team.getSundayTo() != null) {
            teamDTO.setSundayFrom(DateWorker.convertDateToTimeString(team.getSundayFrom()));
            teamDTO.setSundayTo(DateWorker.convertDateToTimeString(team.getSundayTo()));
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

    public void deleteTeam(long id) {
        this.teamRepository.deleteById((this.teamRepository.getById(id).getId()));
        new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    public void neutralizeEmployeeTeam(Team team) {
        List<Employee> listOfEmployees = this.employeeRepository.findAllByTeam(team);
        listOfEmployees.forEach(e -> e.setTeam(null));
    }

}
