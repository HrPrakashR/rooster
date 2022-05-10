package com.example.rooster.employee;

import com.example.rooster.team.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findEmployeeById(long id);

    List<Employee> findAll();

    List<Employee> findAllByTeam(Team team);

    List<Employee> findAllByRole(Role role);

}
