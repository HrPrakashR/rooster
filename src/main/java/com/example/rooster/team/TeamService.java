package com.example.rooster.team;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDTO> getTeams() {
        List<Team> teams = this.teamRepository.findAll();
        List<TeamDTO> teamDTOs = new ArrayList<>();
        teams.forEach((team) -> teamDTOs.add(new TeamDTO(team)));
        return teamDTOs;
    }

    public Team getTeam(long id) {
        return this.teamRepository.findTeamById(id);
    }

    public Team getTeamByName(String name) {
        return this.teamRepository.findByName(name);
    }

    public Team setTeam(TeamDTO teamDTO) {
        if (Objects.isNull(this.teamRepository.findByName(teamDTO.getName()))) {
            return this.teamRepository.save(teamDTO.getTeam());
        } else {
            throw new RuntimeException("This Team already exists");
        }

    }

    public void deleteTeam(String name) {
        this.teamRepository.deleteById((this.teamRepository.findByName(name).getId()));
    }

    public Team updateTeam(TeamDTO teamDTO) {
        Team team = teamDTO.getTeam();
        team.setId(this.teamRepository.findByName(teamDTO.getName()).getId());
        return this.teamRepository.save(team);
    }
}
