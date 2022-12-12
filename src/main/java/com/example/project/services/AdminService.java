package com.example.project.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Функции администратора
 */
@Service
public class AdminService {

    private static final Logger logger = LogManager.getLogger(AdminService.class);

    private final UserService userService;

    @Autowired
    public AdminService(UserService userService) {
        this.userService = userService;
    }

}

