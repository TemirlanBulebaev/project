package com.example.project.controllers;

import com.example.project.dto.ApiResponse;
import com.example.project.payload.*;
import com.example.project.security.JwtUser;
import com.example.project.services.GreenCoffeeService;
import com.example.project.services.PackageService;
import com.example.project.services.RoastedCoffeeService;
import com.example.project.services.StickerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/warehouse")
@Api(value = "Warehouse Rest API", description = "Склад-менеджер")
public class WarehouseController {

    private final GreenCoffeeService greenCoffeeService;
    private final PackageService packageService;
    private final RoastedCoffeeService roastedCoffeeService;

    private final StickerService stickerService;

    @Autowired
    public WarehouseController(GreenCoffeeService greenCoffeeService,
                               PackageService packageService,
                               RoastedCoffeeService roastedCoffeeService,
                               StickerService stickerService) {
        this.greenCoffeeService = greenCoffeeService;
        this.packageService = packageService;
        this.roastedCoffeeService = roastedCoffeeService;
        this.stickerService = stickerService;
    }

    /**
     * Добавить позицию зеленого кофе
     */
    @PostMapping("/addGreen")
    @ApiOperation(value = "Добавление на склад позиции зеленого кофе")
    public ResponseEntity addGreenCoffee(@Valid @RequestBody GreenCoffeeRequest greenCoffeeRequest) {
        return ResponseEntity.ok().body(greenCoffeeService.addGreenCoffee(greenCoffeeRequest));
    }

    /**
     * Получение позции по id
     */
    @GetMapping("/green/{id}")
    @ApiOperation(value = "Получение информации о зеленом кофе по id")
    public ResponseEntity getGreenCoffee(@PathVariable(value = "id") Long id,
                                         @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(greenCoffeeService.getGreenCoffee(id));
    }

    /**
     * Получение всех позиций зеленого кофе
     */
    @GetMapping("/green/all")
    @ApiOperation(value = "Получить список всех позиций зеленого кофе")
    public ResponseEntity getGreenCoffees(@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(greenCoffeeService.getGreenCoffees());
    }

    /**
     * Изменение позиции
     */
    @PutMapping("/green/{id}/edit")
    @ApiOperation(value = "Изменить ифнформацию о позиции зеленого кофе по id ")
    public ResponseEntity editGreenCoffee(@PathVariable(value = "id") Long id,
                                          @Valid @RequestBody EditGreenCoffeeRequest editGreenCoffeeRequest,
                                          @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(greenCoffeeService.editGreenCoffee(id, editGreenCoffeeRequest));
    }

    /**
     *  Удаление позиции зеленого кофе
     */
    @DeleteMapping("/green/{id}/delete")
    @ApiOperation(value = "Удаление позиции зеленого кофе по id")
    public ResponseEntity deleteItem(@PathVariable(value = "id") Long id,
                                     @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {
        greenCoffeeService.deleteGreenCoffee(id);
        return ResponseEntity.ok(new ApiResponse(true, "Позиция c  " + id + " была удалена"));
    }

    /**
     * Обжарка позиции зеленого кофе
     */
    @PostMapping("/green/{id}/roast")
    @ApiOperation(value = "Перевести зеленый кофе в обжареный") // TODO: проверить грамматику
    public ResponseEntity roastGreenCoffee(@PathVariable(value = "id") Long id,
                                           @RequestParam String amountBatch,
                                           @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {
        return ResponseEntity.ok().body(roastedCoffeeService.addRoastCoffee(id, amountBatch));
    }

    /**
     * Посмотреть остатки жареного кофе на складе
     */
    @GetMapping("/roasted/all")
    @ApiOperation(value = "Получить информацию об остатках зеленого кофе на складе")
    public ResponseEntity getRoastedCoffee(@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) { // TODO: убрать jwtUser
        return ResponseEntity.ok().body(roastedCoffeeService.getRoastedCoffee());
    }


    /**
     * Добавить упаковку на склад
     */
    @PostMapping("/addPackage")
    @ApiOperation(value = "Добавить упаковку на склад")
    public ResponseEntity addPackage(@RequestBody PackageRequest packageRequest) {
        return ResponseEntity.ok().body(packageService.addPackage(packageRequest));
    }

    /**
     * Получение информации об упаковке по id
     */
    @GetMapping("/package/{id}")
    @ApiOperation(value = "Получить информацию об упаковке по id")
    public ResponseEntity getPackage(@PathVariable(value = "id") Long id,
                                     @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(packageService.getPackage(id));
    }
    /**
     * Получение информации о всех упаковках
     */
    @GetMapping("/package/all")
    @ApiOperation(value = "Получить информацию о всех упаковках")
    public ResponseEntity getPackages(@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(packageService.getPackages());
    }

    /**
     * Изменение информации об упаковке по id
     */
    @PutMapping("/package/{id}/edit")
    @ApiOperation(value = "изменить информацию об упаковке по id")
    public ResponseEntity editPackage(@PathVariable(value = "id") Long id,
                                      @RequestBody EditPackageRequest editPackageRequest) {

        return ResponseEntity.ok().body(packageService.editPackage(id, editPackageRequest));
    }

    /**
     *  Удаление информации об упаковке
     */
    @DeleteMapping("/package/{id}/delete")
    @ApiOperation(value = "удалить информацию об упаковке по id")
    public ResponseEntity deletePackage(@PathVariable(value = "id") Long id,
                                        @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {
        packageService.deletePackage(id);
        return ResponseEntity.ok(new ApiResponse(true, "Информация об упаковке " + id + " была удалена"));
    }

    /**
     * Добавить накейку на склад
     */
    @PostMapping("/addSticker")
    @ApiOperation(value = "Добавить наклейку на склад")
    public ResponseEntity addSticker(@RequestBody StickerRequest stickerRequest) {
        return ResponseEntity.ok().body(stickerService.addSticker(stickerRequest));
    }
    /**
     * Получение наклейки по id
     */
    @GetMapping("/sticker/{id}")
    @ApiOperation(value = "получить информацию о наклейке по id")
    public ResponseEntity getSticker(@PathVariable(value = "id") Long id,
                                     @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(stickerService.getSticker(id));
    }
    /**
     * Получение информации о всех наклейках
     */
    @GetMapping("/sticker/all")
    @ApiOperation(value = "получить информацию о всех стикерах")
    public ResponseEntity getStickers(@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(stickerService.getStickers());
    }
    /**
     * Изменение информации о наклейках
     */
    @PutMapping("/sticker/{id}/edit")
    @ApiOperation(value = "изменить информацию о стикере по id")
    public ResponseEntity editSticker(@PathVariable(value = "id") Long id,
                                      @RequestBody EditStickerRequest editStickerRequest) {

        return ResponseEntity.ok().body(stickerService.editSticker(id, editStickerRequest));
    }

    /**
     *  Удаление информации о наклейке
     */
    @DeleteMapping("/sticker/{id}/delete")
    @ApiOperation(value = "Удалить информацию о стикере по id")
    public ResponseEntity deleteSticker(@PathVariable(value = "id") Long id,
                                        @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {
        stickerService.deleteSticker(id);
        return ResponseEntity.ok(new ApiResponse(true, "Информация о наклейке " + id + " была удалена"));
    }

}
