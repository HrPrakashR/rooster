package com.example.rooster.employee;

import com.example.rooster.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(long id);

    List<Employee> findAll();

    List<Employee> findAllByTeam(Team team);

    List<Employee> findAllByRole(Role role);

     Optional<Employee> findEmployeeByEmail(String email);

    List<Employee> findAllByTeamId(long teamId);
}
