package com.example.rooster.team;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Team anzeigen

    // ToDo: use name instead of id
    @GetMapping("/team/{id}")
    public Team getTeam(@PathVariable long id) {
        return teamService.getTeam(id);
    }

    // ToDo: Use DTOs instead of Team entity
    @GetMapping("/teams")
    public List<Team> getTeams() {
        return teamService.getTeams();
    }


    // Team anlegen
    @PostMapping("/team/new")
    public Team newTeam(@RequestBody TeamDTO teamDTO) {
        return this.teamService.setTeam(teamDTO);
    }


    @PostMapping("/team/edit")
    public Team update(@RequestBody TeamDTO teamDTO) {
        return this.teamService.updateTeam(teamDTO);
    }


    // ToDo: Delete with DTO and byName
    @DeleteMapping("/team/delete/{id}")
    public List<Team> deleteTeam(@PathVariable long id) {
        this.teamService.deleteTeamById(id);
        return getTeams();
    }

}
