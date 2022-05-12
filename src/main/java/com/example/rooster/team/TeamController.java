package com.example.rooster.team;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Display Team with Team Name as input parameter
    // Todo: task completed
    // changed getTeam parameter from id to name and GetMapping to PostMapping

    @PostMapping("/team/get")
    public Team getTeam(@RequestBody TeamDTO teamDTO) {
        return teamService.getTeamByName(teamDTO.getName());
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

    //Edit a Team
    @PostMapping("/team/edit")
    public Team update(@RequestBody TeamDTO teamDTO) {
        return this.teamService.updateTeam(teamDTO);
    }

    // Delete a Team
    // ToDo: Delete with DTO and byName - Task Completed
    // Changed DeleteMapping to PostMapping and
    // Name is used as parameter instead of Id
    @PostMapping("/team/delete")
    public void delete(@RequestBody TeamDTO teamDTO) {
        this.teamService.deleteTeam(teamDTO.getName());
    }
}
