package com.example.project.services;

import com.example.project.repositories.UserDeliveryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDeliveryService {
    private static final Logger logger = LogManager.getLogger(UserDeliveryService.class); //Логер

    private final UserDeliveryRepository userDeliveryRepository;

    @Autowired
    public UserDeliveryService(UserDeliveryRepository userDeliveryRepository) {
        this.userDeliveryRepository = userDeliveryRepository;
    }
}
