package com.example.rooster.security.config;

import com.example.rooster.employee.Employee;
import com.example.rooster.employee.EmployeeConverter;
import com.example.rooster.employee.EmployeeService;
import com.example.rooster.employee.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
public class SecurityService implements UserDetailsService {

    public static final String OWNER_ROLE = "OWNER_ROLE";
    public static final String MANAGER_ROLE = "MANAGER_ROLE";

    private final EmployeeService employeeService;

    public SecurityService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = this.employeeService.findEmployeeByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(format("Email '%s' is unknown", email)));

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (employee.getRole() == Role.OWNER) {
            authorities.add(new SimpleGrantedAuthority(OWNER_ROLE));
            authorities.add(new SimpleGrantedAuthority(MANAGER_ROLE));
        }
        if (employee.getRole() == Role.MANAGER || employee.getRole() == Role.SUBSTITUTE_MANAGER) {
            authorities.add(new SimpleGrantedAuthority(MANAGER_ROLE));
        }

        return new org.springframework.security.core.userdetails.User(employee.getEmail(), employee.getPassword(), authorities);
    }
}

