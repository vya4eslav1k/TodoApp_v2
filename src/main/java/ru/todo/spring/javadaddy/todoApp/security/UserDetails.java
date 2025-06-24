package ru.todo.spring.javadaddy.todoApp.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import ru.todo.spring.javadaddy.todoApp.model.User;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private User user;

    public User getUser() {
        return user;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
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
