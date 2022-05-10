package com.example.rooster.team;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Team anzeigen

    // Team anlegen

    // Team bearbeiten

    // Team Loeschen
}
