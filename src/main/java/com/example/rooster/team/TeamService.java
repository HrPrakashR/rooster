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

    public Team setTeam(TeamDTO teamDTO) {
        return this.teamRepository.save(teamDTO.getTeam());
    }

    public void deleteTeamById(long id) {
        this.teamRepository.deleteById(id);
    }

    public Team updateTeam(TeamDTO teamDTO) {
        Team team = teamDTO.getTeam();
        team.setId(this.teamRepository.findByName(teamDTO.getName()).getId());
        return this.teamRepository.save(team);
    }
}
