package com.ldar01.demoemployees.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Employee is the entity class for the employees table in the database.
 * It uses JPA annotations to map the class to the table.
 */
@Entity
@Table(name = "employees")
//@Getter
//@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // Se utiliza EAGER para que los roles se carguen junto con el empleado al consultar la entidad Employee
    // PERSIST: permite guardar relaciones nuevas, MERGE: permite actualizar relaciones existentes
    @JoinTable(name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
