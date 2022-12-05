package com.example.project.controllers;

import com.example.project.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
//@Api(value = "Admin Rest API", description = "Функции администратора")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Добавление $ пользователю
     * @param username
     * @param amount
     * @return
     */
//    @PostMapping("/addmoney")
//    @PreAuthorize("hasRole('ADMIN')")
//    @ApiOperation(value = "Добавление $ пользователю")
//    public ResponseEntity addMoneyToUser (@RequestParam String username,
//                                          @RequestParam String amount) {
//
//        adminService.addMoneyToUser(username, amount);
//        return ResponseEntity.ok(new ApiResponse(true, "Сумма " + amount + " $ добавлена пользователю " + username));
//    }

}
