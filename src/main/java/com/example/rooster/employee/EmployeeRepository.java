package com.example.rooster.employee;

import com.example.rooster.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(long id);

    List<Employee> findAll();

    List<Employee> findAllByTeam(Team team);

    List<Employee> findAllByRole(Role role);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Employee employee SET employee.firstName= :firstName WHERE employee.id= :employeeId")
    void updateFirstName(@Param("employeeId") long employeeId, @Param("firstName") String firstName);
}
