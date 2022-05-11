package com.example.rooster.team;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    List<Team> findAll();

    Team findTeamById(Long teamId);

    Team findByName(String name);

}
