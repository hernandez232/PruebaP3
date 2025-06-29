package com.ldar01.demoemployees.security;

import com.ldar01.demoemployees.entities.Employee;
import com.ldar01.demoemployees.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    /*
    Esta clase se encarga de cargar los datos del usuario (empleado) a partr de su nombre de usuario o correo electrónico,
    y convierte estos datos en un objeto que Spring Security pueda utilizar para la autenticación y autorización.
    */

    private EmployeeRepository employeeRepository;

    /*
    loadUserByUsername se utiliza para cargar los detalles de un usuario (como su nombre de usuario, contraseña y roles)
    desde la base de datos, basándose en el nombre de usuario o correo electrónico. Es requerido por la interfaz UserDetailsService
    de Spring Security y es clave para la autenticación.
    */

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exist with the username: " + usernameOrEmail));

        Set<GrantedAuthority> grantedAuthorities = employee.getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new User(
                usernameOrEmail,
                employee.getPassword(),
                grantedAuthorities
        );
    }
}
