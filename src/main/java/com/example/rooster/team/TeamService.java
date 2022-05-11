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

    public void deleteTeamById(long id) {
        this.teamRepository.deleteById(id);
    }

    public Team updateTeam(TeamDTO teamDTO) {
        Team team = this.teamRepository.findByName(teamDTO.getName());
        team.setRestHours(teamDTO.getRestHours());
        team.setRestDays(teamDTO.getRestDays());
        team.setMinBreakTime(teamDTO.getMinBreakTime());
        team.setMondayFrom(teamDTO.getMondayFrom());
        team.setMondayTo(teamDTO.getMondayTo());
        team.setTuesdayFrom(teamDTO.getTuesdayFrom());
        team.setTuesdayTo(teamDTO.getTuesdayTo());
        team.setWednesdayFrom(teamDTO.getWednesdayFrom());
        team.setWednesdayTo(teamDTO.getWednesdayTo());
        team.setThursdayFrom(teamDTO.getThursdayFrom());
        team.setThursdayTo(teamDTO.getThursdayTo());
        team.setFridayFrom(teamDTO.getFridayFrom());
        team.setFridayTo(teamDTO.getFridayTo());
        team.setSaturdayFrom(teamDTO.getSaturdayFrom());
        team.setSaturdayTo(teamDTO.getSaturdayTo());
        team.setSundayFrom(teamDTO.getSundayFrom());
        team.setSundayTo(teamDTO.getSundayTo());

        return this.setTeam(team);
    }
}
