package com.example.project.services;

import com.example.project.entities.User;
import com.example.project.entities.UserDevice;
import com.example.project.entities.token.EmailVerificationToken;
import com.example.project.entities.token.RefreshToken;
import com.example.project.exceptions.AlreadyUserException;
import com.example.project.payload.LoginRequest;
import com.example.project.payload.RegistrationRequest;
import com.example.project.security.JwtTokenProvider;
import com.example.project.security.JwtUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.util.Optional;

@Service
public class AuthService {
    private static final Logger logger = LogManager.getLogger(AuthService.class);
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDeviceService userDeviceService;
    private final RefreshTokenService refreshTokenService;


    @Autowired
    public AuthService(
            UserService userService,
            JwtTokenProvider jwtTokenProvider,
            EmailVerificationTokenService emailVerificationTokenService,
            AuthenticationManager authenticationManager,
            UserDeviceService userDeviceService,
            RefreshTokenService refreshTokenService


            ) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.authenticationManager = authenticationManager;
        this.userDeviceService = userDeviceService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Регистрация пользователя
     */
//Optional всего лишь контейнер: он может содержать значение или некоторый тип Т или просто быть null
    public Optional<User> registrationUser (RegistrationRequest registrationRequest) {

        String email = registrationRequest.getEmail();
        String username = registrationRequest.getUsername();

        if (emailAlreadyExists(email)) {
            logger.error("Email " + email + " уже занят");
            throw new AlreadyUserException("Email", "Adress", email);
        }

        if (usernameAlreadyExists(registrationRequest.getUsername())) {
            logger.error("Username" + registrationRequest.getUsername() + " уже занято");
            throw new AlreadyUserException("User", "Username", username);
        }

        User newUser = userService.addNewUser(registrationRequest).get();
        User savedNewUser = userService.saveUser(newUser);
        logger.info("Зарегистрирован новый пользователь " + email);
        return Optional.ofNullable(savedNewUser);
    }

    /**
     * Существует ли такая почта
     */
    private boolean emailAlreadyExists(String userEmail)
    {
        return userService.existsByEmail(userEmail);
    }

    private boolean usernameAlreadyExists(String username) {
        return userService.existsByUsername(username);
    }

    /**
     * Подтверждение учетной записи
     */
    public Optional<User> confirmEmailRegistration(String token) {

        EmailVerificationToken verificationToken = emailVerificationTokenService.findByToken(token);

        User registeredUser = verificationToken.getUser();

        if  (registeredUser.getIsEmailVerified()) {
            logger.error("Пользователь " + registeredUser.getEmail() + " уже потверждён");
            return Optional.of(registeredUser);
        }

        emailVerificationTokenService.verifyExpiration(verificationToken);
        verificationToken.setConfirmedStatus();

        emailVerificationTokenService.save(verificationToken);
        registeredUser.verificationConfirmed();

        userService.saveUser(registeredUser);
        return Optional.of(registeredUser);
    }

    /**
     * Вход по почте, паролю и устройству
     */
    public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        return Optional.ofNullable(authenticationManager.authenticate(user));
    }

    /**
     * Создание нового токена
     */
    public String createToken (JwtUser jwtUser) {
        return jwtTokenProvider.createToken(jwtUser);
    }

    /**
     * Обновление токена для устройства
     */
    public Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication,
                                                                        LoginRequest loginRequestDto) {
        User jwtUser = (User) authentication.getPrincipal();

        userDeviceService.findByUserId(
                        jwtUser.getId()).map(UserDevice::getRefreshToken)
                .map(RefreshToken::getId)
                .ifPresent(refreshTokenService::deleteById);

        UserDevice userDevice = userDeviceService.createUserDevice(loginRequestDto.getDeviceInfo());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken();

        userDevice.setUser(jwtUser);
        userDevice.setRefreshToken(refreshToken);

        refreshToken.setUserDevice(userDevice);
        refreshToken = refreshTokenService.save(refreshToken);
        logger.info("Добавлено новое устройство " + userDevice.getDeviceId()
                + " для пользователя " + jwtUser.getUsername());
        return Optional.ofNullable(refreshToken);
    }
}
