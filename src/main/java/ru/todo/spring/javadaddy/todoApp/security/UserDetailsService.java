package ru.todo.spring.javadaddy.todoApp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.todo.spring.javadaddy.todoApp.model.User;
import ru.todo.spring.javadaddy.todoApp.repository.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepo;

    public UserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new ru.todo.spring.javadaddy.todoApp.security.UserDetails(user);
    }
}