package com.example.rooster.team;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    // Team anlegen

    // Team bearbeiten

    // Team Loeschen
}
