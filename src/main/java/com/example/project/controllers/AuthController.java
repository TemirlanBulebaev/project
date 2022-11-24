package com.example.project.controllers;

import com.example.project.dto.ApiResponse;
import com.example.project.entities.token.RefreshToken;
import com.example.project.event.UserRegistrationComplete;
import com.example.project.exceptions.InvalidTokenRequestException;
import com.example.project.exceptions.UserLoginException;
import com.example.project.exceptions.UserRegistrationException;
import com.example.project.payload.JwtAuthenticationResponse;
import com.example.project.payload.LoginRequest;
import com.example.project.payload.RegistrationRequest;
import com.example.project.security.JwtTokenProvider;
import com.example.project.security.JwtUser;
import com.example.project.services.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    private final AuthService authService;
    private final ApplicationEventPublisher applicationEventPublisher; //Интерфейс, который инкапсулирует функциональность публикации событий.

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(
            AuthService authService,
            ApplicationEventPublisher applicationEventPublisher,
            JwtTokenProvider jwtTokenProvider
            ) {
        this.authService = authService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Регистрация пользователя
     * (Опубликовать событие для создания токена подверждения электронной почты)
     * registerAsAdmin": "true" - зарегистрировать администратором
     */
    @PostMapping("/registration")    public ResponseEntity registrationUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        //??? Вернем сохраненного пользователя из функции и применим все что находится в мапе???
        return authService.registrationUser(registrationRequest)
                .map(savedNewUser -> {
                    //Генерируем путь
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/auth/registrationConfirmation");
                    //Создаем событие завершения регистрации с последующим подтверждением
                    UserRegistrationComplete userRegistrationComplete = new UserRegistrationComplete(savedNewUser, urlBuilder);
                    //Уведомить всех подходящих прослушивателей, зарегистрированных в этом приложении, о событии
                    applicationEventPublisher.publishEvent(userRegistrationComplete);
                    logger.info("Пользователь зарегистрировался: " + savedNewUser.getUsername());
                    return ResponseEntity.ok(new ApiResponse(true, "Для завершения регистрации перейдите по ссылке в письме"));
                //??? Нужно ли бросать эту ошибку если мы в сервисе бросаем такую же?
                }).orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "Пользователь с такой почтой уже существует"));//Не получилось отправить уведомление о регистрации
    }

    /**
     * Подтверждение учетной записи
     */
    @GetMapping("/registrationConfirmation")
    //@ApiOperation(value = "Подтверждение учетной записи")
    public ResponseEntity confirmRegistration(@RequestParam("token") String token) {//

        return authService.confirmEmailRegistration(token)
                .map(user -> ResponseEntity.ok(new ApiResponse(true, "Учётная запись подтверждена")))
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", token, "Не удалось подтвердить регистрацию"));
    }

    /**
     * Логин по почте, паролю и устройству
     */
    @PostMapping("/login")
    //@ApiOperation(value = "Логин по почте, паролю и устройству")
    public ResponseEntity login (@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("аутентификации", loginRequest.getEmail()));

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();//Привести к типу
        logger.info("Вход в систему  " + jwtUser.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);// getContext() - задает контекст подсовывает токен

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = authService.createToken(jwtUser);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,  jwtTokenProvider.getExpiryDuration()));
                }).orElseThrow(() -> new UserLoginException("создания токена", loginRequest.getEmail()));

    }
}
