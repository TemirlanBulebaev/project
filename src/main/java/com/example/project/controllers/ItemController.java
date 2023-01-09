package com.example.project.controllers;

import com.example.project.dto.ApiResponse;
import com.example.project.exceptions.InvalidTokenRequestException;
import com.example.project.payload.EditItemRequest;
import com.example.project.payload.ItemRequest;
import com.example.project.security.JwtUser;
import com.example.project.services.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/item")
@Api(value = "Item Rest API", description = "Товар, который можно приобрести за внутреннюю валюту")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Добавление товара
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Добавление Item")
    public ResponseEntity addItem(@Valid @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok().body(itemService.addItem(itemRequest));
    }

    /**
     * Добавить количество существующего товара
     */
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Добавление количества Item по id")
    public ResponseEntity addAmountItem(@PathVariable(value = "id") Long id,
                                        @RequestParam String amountItem,
                                        @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {
        return ResponseEntity.ok().body(itemService.addAmountItem(id, amountItem));
    }

    /**
     * Получение Items
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Получение Items. Формат ответа зависит от роли")
    public ResponseEntity getItems(@ApiIgnore Pageable pageable,
                                   @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(itemService.getItems(pageable, jwtUser));
    }
    /**
     * Покупка Item
     */
    @PostMapping("/{id}/buy")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Добавление Item в инвентарь (корзину)")
    public ResponseEntity buyItem(@PathVariable(value = "id") Long itemId,
                                  @RequestParam String amountItem,
                                  @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {


        return ResponseEntity.ok().body(itemService.buyItem(itemId, amountItem, jwtUser));
    }


    /**
     *  Удаление (Выключение) Item
     */
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Убрать отображение товара для пользователя")
    public ResponseEntity deleteItem(@PathVariable(value = "id") Long id,
                                     @AuthenticationPrincipal JwtUser jwtUser) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(new ApiResponse(true, "Товар " + id + " был удален"));
    }


    /**
     * Получение товара по id
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Получение Item по id. Формат ответа зависить от роли")
    public ResponseEntity getItem(@PathVariable(value = "id") Long id,
                                      @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(itemService.getItem(id, jwtUser));
    }

    /**
     * Изменение товара
     */
    @PutMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Изменение информации об Item ")
    public ResponseEntity editItem(@PathVariable(value = "id") Long id,
                                   @Valid @RequestBody EditItemRequest editItemRequest) {

        return ResponseEntity.ok().body(itemService.editItem(id, editItemRequest));
    }
}

