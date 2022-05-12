package com.example.rooster.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAll();

    Team findTeamById(Long teamId);

    Team findByName(String name);

    Team deleteById(long Id);

}
