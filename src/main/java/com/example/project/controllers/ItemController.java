package com.example.project.controllers;

import com.example.project.dto.ApiResponse;
import com.example.project.exceptions.InvalidTokenRequestException;
import com.example.project.payload.EditItemRequest;
import com.example.project.payload.ItemRequest;
import com.example.project.security.JwtUser;
import com.example.project.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/item")
//@Api(value = "Item Rest API", description = "Товар, который можно приобрести за внутреннюю валюту")
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
    @PreAuthorize("hasRole('USER')")
    //@ApiOperation(value = "Добавление Item")
    public ResponseEntity addItem(@Valid @RequestBody ItemRequest itemRequest,
                                  @AuthenticationPrincipal JwtUser jwtUser
    ) {
        return itemService.addItem(itemRequest, jwtUser)
                .map(item -> ResponseEntity.ok(new ApiResponse(true, "Товар успешно, сохранен!")))
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", "1231", "Не удалось подтвердить регистрацию")); //TODO реалихзовать ошибку
    }

    /**
     * Получение Items
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    //@ApiOperation(value = "Получение Items. Формат ответа зависит от роли")
    public ResponseEntity getItems(Pageable pageable, // пагинация
                                   @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(itemService.getItems(pageable, jwtUser));
    }

    /**
     *  Удаление (Выключение) Item
     */
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('USER')")
    //@ApiOperation(value = "Получение Items. Формат ответа зависить от роли")
    public ResponseEntity deleteItem(@PathVariable(value = "id") Long id,
                                     @AuthenticationPrincipal JwtUser jwtUser) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(new ApiResponse(true, "Item " + id + " был удален"));
    }

    /**
     * Получение товара по id
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    //@ApiOperation(value = "Получение Item. Формат ответа зависить от роли")
    public ResponseEntity getItem(@PathVariable(value = "id") Long id,
                                      @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(itemService.getItem(id, jwtUser));
    }

    /**
     * Изменение товара
     */
    @PutMapping("/{id}/edit")
    @PreAuthorize("hasRole('USER')")
    //@ApiOperation(value = "Изменение Item (без цен)")
    public ResponseEntity editItem(@PathVariable(value = "id") Long id,
                                   @Valid @RequestBody EditItemRequest editItemRequest,
                                   @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(itemService.editItem(id, editItemRequest));
    }


}

