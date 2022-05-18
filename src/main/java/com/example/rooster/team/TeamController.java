package com.example.rooster.team;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/teams/")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Display Team with Team Name as input parameter
    @GetMapping("/get/{id}")
    public TeamDTO getTeam(@PathVariable long id) {
        return teamService.convertToTeamDTO(teamService.getTeamById(id).orElseThrow(() -> new RuntimeException("can not find this team")));
    }

    // Display all Teams
    @GetMapping("/get_all")
    public List<TeamDTO> getTeams() {
        return teamService.getTeams();
    }

    // Create a new Team
    //TODO: Check if the team name already exists. Teams with the same new are not allowed.
    @PostMapping("/new")
    public TeamDTO newTeam(@RequestBody TeamDTO teamDTO) {
        Team newTeam = teamService.convertToTeam(teamDTO);
        Team savedTeam = teamService.setTeam(newTeam);
        return teamService.convertToTeamDTO(savedTeam);
    }

    //Edit a Team
    //TODO: Check if the team name already exists. Teams with the same new are not allowed.
    @PostMapping("/edit")
    public TeamDTO update(@RequestBody TeamDTO teamDTO) {
        Team teamToEdit = teamService.convertToTeam(teamDTO);
        teamService.setTeam(teamToEdit);
        return teamDTO;
    }

    // Delete a Team. First sets the team value of the employees from this team to null.
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        this.teamService.neutralizeEmployeeTeam(this.teamService.getTeam(id));
        this.teamService.deleteTeam(id);
    }
}
