package com.example.project.security;

import com.example.project.entities.User;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        return user.map(JwtUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + email));

    }

    public UserDetails loadUserById(Long id)  {

        Optional<User> user = userRepository.findById(id);
        return user.map(JwtUser::new)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка авторизации. Пользователя", "параметром id", id));

    }

}
