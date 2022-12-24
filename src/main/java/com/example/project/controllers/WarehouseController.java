package com.example.project.controllers;

import com.example.project.dto.ApiResponse;
import com.example.project.payload.*;
import com.example.project.security.JwtUser;
import com.example.project.services.GreenCoffeeService;
import com.example.project.services.PackageService;
import com.example.project.services.RoastedCoffeeService;
import com.example.project.services.StickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController // склад менеджер
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/warehouse")
public class WarehouseController {

    private final GreenCoffeeService greenCoffeeService;
    private final PackageService packageService;
    private final RoastedCoffeeService roastedCoffeeService;

    private final StickerService stickerService;

    @Autowired
    public WarehouseController(GreenCoffeeService greenCoffeeService, PackageService packageService, RoastedCoffeeService roastedCoffeeService, StickerService stickerService) {
        this.greenCoffeeService = greenCoffeeService;
        this.packageService = packageService;
        this.roastedCoffeeService = roastedCoffeeService;
        this.stickerService = stickerService;
    }

    /**
     * Добавить позицию зеленого кофе
     */
    @PostMapping("/addGreen")
    public ResponseEntity addGreenCoffee(@Valid @RequestBody GreenCoffeeRequest greenCoffeeRequest) {
        return ResponseEntity.ok().body(greenCoffeeService.addGreenCoffee(greenCoffeeRequest));
    }

    /**
     * Получение позции по id
     */
    @GetMapping("/green/{id}")
    public ResponseEntity getGreenCoffee(@PathVariable(value = "id") Long id,
                                  @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(greenCoffeeService.getGreenCoffee(id));
    }

    /**
     * Получение всех позиций зеленого кофе
     */
    @GetMapping("/green/all") //Готов
    public ResponseEntity getGreenCoffees(@AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(greenCoffeeService.getGreenCoffees());
    }

    /**
     * Изменение позиции
     */
    @PutMapping("/green/{id}/edit")
    public ResponseEntity editGreenCoffee(@PathVariable(value = "id") Long id,
                                          @Valid @RequestBody EditGreenCoffeeRequest editGreenCoffeeRequest,
                                          @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(greenCoffeeService.editGreenCoffee(id, editGreenCoffeeRequest));
    }

    /**
     *  Удаление позиции зеленого кофе
     */
    @DeleteMapping("/green/{id}/delete")
    public ResponseEntity deleteItem(@PathVariable(value = "id") Long id,
                                     @AuthenticationPrincipal JwtUser jwtUser) {
        greenCoffeeService.deleteGreenCoffee(id);
        return ResponseEntity.ok(new ApiResponse(true, "Позиция " + id + " была удалена"));
    }

    /**
     * Обжарка позиции зеленого кофе
     */
    @PostMapping("/green/{id}/roast")
    public ResponseEntity roastGreenCoffee(@PathVariable(value = "id") Long id,
                                           @RequestParam String amountBatch,
                                           @ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {
        return ResponseEntity.ok().body(roastedCoffeeService.addRoastCoffee(id, amountBatch));
    }




    /**
     * Добавить упаковку на склад
     */
    @PostMapping("/addPackage")
    public ResponseEntity addPackage(@RequestBody PackageRequest packageRequest) {
        return ResponseEntity.ok().body(packageService.addPackage(packageRequest));
    }
    /**
     * Получение упаковки по id
     */
    @GetMapping("/package/{id}")
    public ResponseEntity getPackage(@PathVariable(value = "id") Long id,
                                     @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(packageService.getPackage(id));
    }
    /**
     * Получение информации о всех упаковках
     */
    @GetMapping("/package/all")
    public ResponseEntity getPackages(@AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(packageService.getPackages());
    }

    /**
     * Изменение информации об упаковках
     */
    @PutMapping("/package/{id}/edit")
    public ResponseEntity editPackage(@PathVariable(value = "id") Long id,
                                      @RequestBody EditPackageRequest editPackageRequest) {

        return ResponseEntity.ok().body(packageService.editPackage(id, editPackageRequest));
    }

    /**
     *  Удаление информации об упаковке
     */
    @DeleteMapping("/package/{id}/delete")
    public ResponseEntity deletePackage(@PathVariable(value = "id") Long id,
                                        @AuthenticationPrincipal JwtUser jwtUser) {
        packageService.deletePackage(id);
        return ResponseEntity.ok(new ApiResponse(true, "Информация об упаковке " + id + " была удалена"));
    }

    /**
     * Добавить накейку на склад
     */
    @PostMapping("/addSticker")
    public ResponseEntity addSticker(@RequestBody StickerRequest stickerRequest) {
        return ResponseEntity.ok().body(stickerService.addSticker(stickerRequest));
    }
    /**
     * Получение наклейки по id
     */
    @GetMapping("/sticker/{id}")
    public ResponseEntity getSticker(@PathVariable(value = "id") Long id,
                                     @AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(stickerService.getSticker(id));
    }
    /**
     * Получение информации о всех наклейках
     */
    @GetMapping("/sticker/all")
    public ResponseEntity getStickers(@AuthenticationPrincipal JwtUser jwtUser) {

        return ResponseEntity.ok().body(stickerService.getStickers());
    }
    /**
     * Изменение информации о наклейках
     */
    @PutMapping("/sticker/{id}/edit")
    public ResponseEntity editSticker(@PathVariable(value = "id") Long id,
                                      @RequestBody EditStickerRequest editStickerRequest) {

        return ResponseEntity.ok().body(stickerService.editSticker(id, editStickerRequest));
    }

    /**
     *  Удаление информации о наклейке
     */
    @DeleteMapping("/sticker/{id}/delete")
    public ResponseEntity deleteSticker(@PathVariable(value = "id") Long id,
                                        @AuthenticationPrincipal JwtUser jwtUser) {
        stickerService.deleteSticker(id);
        return ResponseEntity.ok(new ApiResponse(true, "Информация о наклейке " + id + " была удалена"));
    }

}
