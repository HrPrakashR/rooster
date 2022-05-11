package com.example.rooster.team;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

//   @PostMapping("/edit/{id}")
//    public Team update(@RequestBody @PathVariable Long id) {
//        Team team = teamService.getTeam(id);
//
//        return teamService.updateTeam(team);
//   }


    // Team Loeschen
    @DeleteMapping("/team/delete/{id}")
    public List<Team> deleteTeam(@PathVariable long id) {
        this.teamService.deleteTeamById(id);
        return getTeams();
    }

}
