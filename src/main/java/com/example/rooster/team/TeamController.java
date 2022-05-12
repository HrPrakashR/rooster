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
    @PostMapping("/get")
    public Team getTeam(@RequestBody TeamDTO teamDTO) {
        return teamService.getTeamByName(teamDTO.getName());
    }

    // Display all Teams
    @GetMapping("/get_all")
    public List<TeamDTO> getTeams() {
        return teamService.getTeams();
    }

    // Create a new Team
    @PostMapping("/new")
    public Team newTeam(@RequestBody TeamDTO teamDTO) {
        return this.teamService.setTeam(teamDTO);
    }

    @GetMapping("/new")
    public TeamDTO newTeam() {
        return new TeamDTO();
    }

    //Edit a Team
    //Attention: Team name cannot be changed, as it is identifier!
    @PostMapping("/edit")
    public Team update(@RequestBody TeamDTO teamDTO) {
        return this.teamService.updateTeam(teamDTO);
    }

    // Delete a Team
    @PostMapping("/delete")
    public void delete(@RequestBody TeamDTO teamDTO) {
        this.teamService.deleteTeam(teamDTO.getName());
    }
}
