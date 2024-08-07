package ru.cramonk.spring.boot_security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Имя пользователя не должно быть пустым")
    @Size(min = 2, max = 20, message = "Имя пользователя должно быть от 2 до 20 символов длиной")
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов длиной")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Фамилия пользователя не должна быть пустым")
    @Size(min = 2, max = 20, message = "Фамилия должна быть от 2 до 20 символов длиной")
    @Column(name = "second_name")
    private String lastName;


    @ManyToMany
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Transient
    private List<String> roleNames;
    public List<String> getListOfRoleNames() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
