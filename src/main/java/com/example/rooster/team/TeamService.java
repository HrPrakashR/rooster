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

        teamDTO.setMondayFrom(
                String.valueOf(DateWorker.getCalendarObject(team.getMondayFrom()).get(Calendar.MINUTE) + ':' +
                DateWorker.getCalendarObject(team.getMondayFrom()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setMondayTo(
                String.valueOf(DateWorker.getCalendarObject(team.getMondayTo()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getMondayTo()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setTuesdayFrom(
                String.valueOf(DateWorker.getCalendarObject(team.getTuesdayFrom()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getMondayFrom()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setTuesdayTo(
                String.valueOf(DateWorker.getCalendarObject(team.getTuesdayTo()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getTuesdayTo()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setWednesdayFrom(
                String.valueOf(DateWorker.getCalendarObject(team.getWednesdayFrom()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getWednesdayFrom()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setWednesdayTo(
                String.valueOf(DateWorker.getCalendarObject(team.getWednesdayTo()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getWednesdayTo()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setThursdayFrom(
                String.valueOf(DateWorker.getCalendarObject(team.getThursdayFrom()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getThursdayFrom()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setThursdayTo(
                String.valueOf(DateWorker.getCalendarObject(team.getThursdayTo()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getThursdayTo()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setFridayFrom(
                String.valueOf(DateWorker.getCalendarObject(team.getFridayFrom()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getFridayFrom()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setFridayTo(
                String.valueOf(DateWorker.getCalendarObject(team.getFridayTo()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getFridayTo()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setSaturdayFrom(
                String.valueOf(DateWorker.getCalendarObject(team.getSaturdayFrom()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getSaturdayFrom()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setSaturdayTo(
                String.valueOf(DateWorker.getCalendarObject(team.getSaturdayTo()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getSaturdayTo()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setSundayFrom(
                String.valueOf(DateWorker.getCalendarObject(team.getSundayFrom()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getSundayFrom()).get(Calendar.HOUR_OF_DAY))
        );

        teamDTO.setSundayTo(
                String.valueOf(DateWorker.getCalendarObject(team.getSundayTo()).get(Calendar.MINUTE) + ':' +
                        DateWorker.getCalendarObject(team.getSundayTo()).get(Calendar.HOUR_OF_DAY))
        );

        return teamDTO;
    }

    public Team convertToTeam(TeamDTO teamDTO) {
        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setName(teamDTO.getName());
        team.setRestHours(teamDTO.getRestHours());
        team.setRestDays(teamDTO.getRestDays());
        team.setMinBreakTime(teamDTO.getMinBreakTime());
        team.setMondayFrom(teamDTO.getMondayFrom());
        team.setMondayTo(teamDTO.getMondayTo());
        team.setTuesdayFrom(teamDTO.getTuesdayFrom());
        team.setTuesdayTo(teamDTO.getTuesdayTo());
        team.setWednesdayFrom(teamDTO.getWednesdayFrom());
        team.setWednesdayTo(teamDTO.getWednesdayTo());
        team.setThursdayFrom(teamDTO.getThursdayFrom());
        team.setThursdayTo(teamDTO.getThursdayTo());
        team.setFridayFrom(teamDTO.getFridayFrom());
        team.setFridayTo(teamDTO.getFridayTo());
        team.setSaturdayFrom(teamDTO.getSaturdayFrom());
        team.setSaturdayTo(teamDTO.getSaturdayTo());
        team.setSundayFrom(teamDTO.getSundayFrom());
        team.setSundayTo(teamDTO.getSundayTo());
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
