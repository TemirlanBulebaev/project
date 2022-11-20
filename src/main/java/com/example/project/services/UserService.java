package com.example.project.services;

import com.example.project.entities.Role;
import com.example.project.entities.User;
import com.example.project.payload.RegistrationRequest;
import com.example.project.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class); //Логер
    private final UserRepository userRepository;
    private final RoleService roleService;
    //private final PasswordEncoder passwordEncoder;
    
    //
    //private final UserDeviceService userDeviceService;
    //private final RefreshTokenService refreshTokenService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            RoleService roleService
    ) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }


    public Optional<User> addNewUser(RegistrationRequest registrationRequest) {
        User newUser = new User(); //Создаем нового пользователя
        Boolean isAdmin = registrationRequest.getRegistrationAsAdmin();// Проверяем админ ли  это
        newUser.setEmail(registrationRequest.getEmail()); // устанавливаем почту
        newUser.setUsername(registrationRequest.getUsername()); // устнавливаем имя
        newUser.addRoles(getRoles(isAdmin));
        newUser.setActive(true);
        newUser.setIsEmailVerified(false);
        return Optional.of(newUser);
    }

    /**
     * Проверка может ли быть новый пользователь админом
     */
    private Set<Role> getRoles(Boolean isAdmin) {
        Set<Role> roles = new HashSet<>(roleService.findAll());
        if (!isAdmin) {
            roles.removeIf(Role::isAdminRole);
        }
        return roles;
    }

    /**
     * Сохранение пользователя
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Существует ли такая почта
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    /**
     * Существует ли такое имя пользователя
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}

 