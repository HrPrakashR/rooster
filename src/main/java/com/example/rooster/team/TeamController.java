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

    @GetMapping("/{id}/team")
    public Team getTeam(@PathVariable long id) {
        return teamService.getTeam(id);
    }

    @GetMapping("/teams")
    public List<Team> getTeams() {
        return teamService.getTeams();
    }


    // Team anlegen

    // Team bearbeiten sobald Frontend steht!

    @PostMapping("/team/edit")
    public Team update(@RequestBody TeamDTO teamDTO) {
        return this.teamService.updateTeam(teamDTO);
    }

    // Team Loeschen
    @DeleteMapping("/team/delete/{id}")
    public List<Team> deleteTeam(@PathVariable long id) {
        this.teamService.deleteTeamById(id);
        return getTeams();
    }

}
