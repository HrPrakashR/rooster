package com.example.rooster.team;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getTeams() {
        return this.teamRepository.findAll();
    }

    public Team getTeam(long id) {
        return this.teamRepository.findTeamById(id);
    }

    public Team setTeam(Team team) {
        return this.teamRepository.save(team);
    }

    public Team updateTeam(Team newTeam) {
        return this.teamRepository.save(newTeam);
    }

    public void deleteTeamById(long id) {
        this.teamRepository.deleteById(id);
    }
}
