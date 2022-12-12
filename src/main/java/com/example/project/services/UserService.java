package com.example.project.services;

import com.example.project.dto.UserDto;
import com.example.project.dto.UserProfileDto;
import com.example.project.entities.Role;
import com.example.project.entities.User;
import com.example.project.entities.UserDevice;
import com.example.project.entities.UserInventory;
import com.example.project.event.UserLogoutSuccess;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.exceptions.UserLogoutException;
import com.example.project.payload.LogOutRequest;
import com.example.project.payload.RegistrationRequest;
import com.example.project.repositories.UserRepository;
import com.example.project.security.JwtUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class); //Логер
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserDeviceService userDeviceService;
    private final RefreshTokenService refreshTokenService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserService(
            UserRepository userRepository,
            RoleService roleService,
            PasswordEncoder passwordEncoder, UserDeviceService userDeviceService, RefreshTokenService refreshTokenService, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userDeviceService = userDeviceService;
        this.refreshTokenService = refreshTokenService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Создание нового пользователя
     */
    public Optional<User> addNewUser(RegistrationRequest registrationRequest) {
        User newUser = new User();
        Boolean isAdmin = registrationRequest.getRegistrationAsAdmin();
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setUsername(registrationRequest.getUsername());
        newUser.addRoles(getRolesForNewUser(isAdmin));
        newUser.setActive(true);
        newUser.setIsEmailVerified(false);
        UserInventory userInventory = new UserInventory();
        newUser.setUserInventory(userInventory);
        return Optional.of(newUser);
    }


    /**
     * Является ли новый пользователь администратором
     */
    private Set<Role> getRolesForNewUser(Boolean isAdmin) {
        Set<Role> newRoles = new HashSet<>(roleService.findAll());
        if (!isAdmin) {
            newRoles.removeIf(Role::isAdminRole);
        }
        return newRoles;
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


    /**
     * Получение свего профиля
     */
    public Optional<UserProfileDto> getMyUserProfile(JwtUser jwtUser) {
        String username = jwtUser.getUsername();
        User user = findByUsername(username);
        return Optional.of(UserProfileDto.fromUser(user));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    /**
     * Получение списка всех пользователей
     */
    public List<UserDto> findAllUsersDto() {
        List<User> activeUsers = userRepository.findByActive(true);
        if (activeUsers == null) {
            logger.error("Нет активных пользователей");
            throw new ResourceNotFoundException("User", "active", true);
        }

        return activeUsers.stream() .map(activeUser -> UserDto.fromUser(activeUser))
                .collect(Collectors.toList());
    }

    /**
     * Logout
     * Происходит по конкретному девайсу
     */
    public void logout(JwtUser jwtUser, @Valid LogOutRequest logOutRequestDto) {

        String deviceId = logOutRequestDto.getDeviceInfo().getDeviceId();
        UserDevice userDevice = userDeviceService.findByUserId(jwtUser.getId())
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(
                        logOutRequestDto.getDeviceInfo().getDeviceId(), "Invalid device Id"));

        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        UserLogoutSuccess logoutSuccess = new UserLogoutSuccess(
                jwtUser.getEmail(), credentials.toString(), logOutRequestDto);
        applicationEventPublisher.publishEvent(logoutSuccess);
        logger.info("Пользователь покинул систему " + jwtUser.getUsername());

    }

    public Optional<UserDto> getUserProfile (String username) {
        User user = findByUsername(username);
        if (!user.isActive()) {
            logger.error("Пользователь " + user.getUsername() + " Не активен");
            throw new ResourceNotFoundException("Пользоватеоь", "username", username);
        }
        return Optional.of(UserDto.fromUser(user));
    }
}

 