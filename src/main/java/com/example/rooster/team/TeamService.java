package com.example.rooster.team;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        teamDTO.setMondayFrom(team.getMondayFrom());
        teamDTO.setMondayTo(team.getMondayTo());
        teamDTO.setTuesdayFrom(team.getTuesdayFrom());
        teamDTO.setTuesdayTo(team.getTuesdayTo());
        teamDTO.setWednesdayFrom(team.getWednesdayFrom());
        teamDTO.setWednesdayTo(team.getWednesdayTo());
        teamDTO.setThursdayFrom(team.getThursdayFrom());
        teamDTO.setThursdayTo(team.getThursdayTo());
        teamDTO.setFridayFrom(team.getFridayFrom());
        teamDTO.setFridayTo(team.getFridayTo());
        teamDTO.setSaturdayFrom(team.getSaturdayFrom());
        teamDTO.setSaturdayTo(team.getSaturdayTo());
        teamDTO.setSundayFrom(team.getSundayFrom());
        teamDTO.setSundayTo(team.getSundayTo());
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

    public Team getTeamById(Long id) {
        return this.teamRepository.findById(id).orElseThrow(() -> new RuntimeException("We cannot find this Team"));
    }

    public void setTeam(Team team) {
        if (team != null && Objects.isNull(this.getTeamById(team.getId()))) {
            this.teamRepository.save(team);
        }
        throw new RuntimeException("This Team already exists");
    }

    public ResponseEntity<String> deleteTeam(long id) {
        this.teamRepository.deleteById((this.teamRepository.getById(id).getId()));
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    public void neutralizeEmployeeTeam(Team team){
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
