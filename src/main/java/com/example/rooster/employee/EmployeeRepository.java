package com.example.rooster.employee;
import com.example.rooster.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(long id);

    List<Employee> findAll();

    List<Employee> findAllByTeam(Team team);

    List<Employee> findAllByRole(Role role);

    Employee findEmployeeByEmail(String email);

}
