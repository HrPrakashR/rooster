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
    @PostMapping("/new")
    public Team newTeam(@RequestBody TeamDTO teamDTO) {
        return this.teamService.setTeam(this.teamService.convertToTeam(teamDTO));
    }

    //Edit a Team
    //Attention: Team name cannot be changed, as it is identifier!
    @PostMapping("/edit")
    public Team update(@RequestBody TeamDTO teamDTO) {
        return this.teamService.updateTeam(this.teamService.convertToTeam(teamDTO));
    }

    // Delete a Team
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        this.teamService.neutralizeEmployeeTeam(this.teamService.getTeam(id));
        this.teamService.deleteTeam(id);
    }
}
