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
    // Todo: task completed
    // changed getTeam parameter from id to name and GetMapping to PostMapping

    @PostMapping("/get")
    public Team getTeam(@RequestBody TeamDTO teamDTO) {
        return teamService.getTeamByName(teamDTO.getName());
    }

    // ToDo: Use DTOs instead of Team entity
    @GetMapping("/get_all")
    public List<TeamDTO> getTeams() {
        return teamService.getTeams();
    }

    // Team anlegen
    @PostMapping("/new")
    public Team newTeam(@RequestBody TeamDTO teamDTO) {
        return this.teamService.setTeam(teamDTO);
    }

    @GetMapping("/new")
    public TeamDTO newTeam(){
        return new TeamDTO();
    }

    //Edit a Team
    @PostMapping("/edit")
    public Team update(@RequestBody TeamDTO teamDTO) {
        return this.teamService.updateTeam(teamDTO);
    }

    // Delete a Team
    // ToDo: Delete with DTO and byName - Task Completed
    // Changed DeleteMapping to PostMapping and
    // Name is used as parameter instead of Id
    @PostMapping("/delete")
    public void delete(@RequestBody TeamDTO teamDTO) {
        this.teamService.deleteTeam(teamDTO.getName());
    }
}
