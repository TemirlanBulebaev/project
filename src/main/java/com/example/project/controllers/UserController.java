package com.example.project.controllers;

import com.example.project.dto.ApiResponse;
import com.example.project.payload.DeliveryRequest;
import com.example.project.payload.LogOutRequest;
import com.example.project.security.JwtUser;
import com.example.project.services.UserInventoryService;
import com.example.project.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(value = "User Rest API", description = "Доступно только авторизованным пользователям")
public class UserController {

    private final UserService userService;
    private final UserInventoryService userInventoryService;

    @Autowired
    public UserController(UserService userService, UserInventoryService userInventoryService) {
        this.userService = userService;
        this.userInventoryService = userInventoryService;
    }

    /**
     * Получение свего профиля
     * В профиле отражена информация о самом пользователе,
     * @param jwtUser
     * @return
     */
    @GetMapping("/profile") // TODO: расширить информацию
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Получение свего профиля")
    public ResponseEntity getMyUserProfile (@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(userService.getMyUserProfile(jwtUser));
    }

    /**
     * Получение информации о пользователе
     * Доступно всем авторизованным пользователяим
     * @param username
     * @param jwtUser
     * @return
     */
    @GetMapping("/{username}")//TODO Расширить возможности
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Получение информации о пользователе")
    public ResponseEntity getUserProfile(@PathVariable(value = "username") String username,
                                         @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(userService.getUserProfile(username));
    }


    /**
     * Получение списка всех пользователей
     * @param jwtUser
     * @return
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Получение списка всех пользователей")
    public ResponseEntity getAllUsers (@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(userService.findAllUsersDto());
    }

    /**
     * Logout
     * Происходит по конкретному девайсу
     * @param jwtUser
     * @param logOutRequest
     * @return
     */
    @PostMapping("/logout")//TODO перепроверить логику работы
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Logout")
    public ResponseEntity logout(@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser,
                                 @ApiParam(value = "The LogOutRequest payload")
                                 @Valid @RequestBody LogOutRequest logOutRequest) {

        userService.logout(jwtUser, logOutRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Log out successful") );
    }

    /**
     * Получение своего инвенторя
     */
    @GetMapping("/inventory")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Получение своего инвенторя")
    public ResponseEntity getUserInventory (@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {
        return ResponseEntity.ok().body(userService.getUserInventory(jwtUser));
    }

    /**
     * Добавить количество товара в инвентаре
     */
    @PutMapping("/inventory/{unitId}/edit")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Изменить количество товара в инвентаре по id")
    public ResponseEntity getUserInventory (@PathVariable(value = "unitId") Long unitId,
                                            @RequestParam String amountItems,
                                            @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(userInventoryService.editUnit(unitId, amountItems));
    }

    /**
     * Оформить доставку
     */
    @PostMapping("/inventory/delivery")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Оформить доставку")
    public ResponseEntity arrangeDelivery(@RequestBody DeliveryRequest deliveryRequest,
                                          @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser
                                 ) {
        if (deliveryRequest.getIsPayment()) {
            userService.arrangeInventoryDelivery(deliveryRequest, jwtUser);
            return ResponseEntity.ok(new ApiResponse(true, "Доставка оформленна"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "заказ не оплачен"));
        }
    }

    /**
     * Посмотреть историю доставок
     */
    @GetMapping("/inventory/delivery/history/all")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Получить историю доставок")
    public ResponseEntity getAllDelivery(@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(userService.findAllDelivery(jwtUser));
    }


    /**
     * Посмотреть историю доставки по id
     */
    @GetMapping("/inventory/delivery/history/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Получить информацию о доставке по id")
    public ResponseEntity getAllDelivery(@PathVariable(value = "id") Long deliveryId,
                                         @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(userService.findDeliveryById(deliveryId));
    }
}

