package com.example.project.services;

import com.example.project.dto.UserInventoryDto;
import com.example.project.entities.InventoryUnit;
import com.example.project.entities.Item;
import com.example.project.entities.User;
import com.example.project.entities.UserInventory;
import com.example.project.repositories.InventoryUnitRepository;
import com.example.project.repositories.UserInventoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserInventoryService {

    private static final Logger logger = LogManager.getLogger(UserInventoryService.class);

    private final InventoryUnitRepository inventoryUnitRepository;
    private final UserInventoryRepository userInventoryRepository;

    @Autowired
    public UserInventoryService(InventoryUnitRepository inventoryUnitRepository, UserInventoryRepository userInventoryRepository) {
        this.inventoryUnitRepository = inventoryUnitRepository;
        this.userInventoryRepository = userInventoryRepository;
    }


    /**
     * Добавление пользователю Item
     */
    public Set<InventoryUnit> addItem(UserInventory userInventory, Item item1, String amountItems) {

        Set<InventoryUnit> inventoryUnits = userInventory.getInventoryUnit();
        InventoryUnit inventoryUnit = inventoryUnits.stream().filter(unit -> item1.equals(unit.getItem())).findAny().orElse(null);

        // Проверка есть ли у пользователя ячейка с таким UserItem
        if (inventoryUnit == null) {
            // Создание новой ячейки
            InventoryUnit newInventoryUnit = new InventoryUnit();
            newInventoryUnit.setAmountItems(amountItems);
            newInventoryUnit.setItem(item1);

            InventoryUnit savedInventoryUnit = inventoryUnitRepository.save(newInventoryUnit);
            inventoryUnits.add(savedInventoryUnit);
            logger.info("Добавлена новая ячейка инвенторя "+ savedInventoryUnit.getId());

        } else {
            // Изменение количество UserItem в ячейке
            int oldAmountItems =  Integer.parseInt(inventoryUnit.getAmountItems());
            int add = Integer.parseInt(amountItems);
            int newAmountItems =  oldAmountItems + add;

            inventoryUnit.setAmountItems(Integer.toString(newAmountItems));
            inventoryUnits.add(inventoryUnit);
            logger.info("В ячейке " + inventoryUnit.getId() + " изменено количество Item :" + newAmountItems);
        }
        userInventory.setInventoryUnit(inventoryUnits);
        userInventoryRepository.save(userInventory);
        return inventoryUnits;
    }

    /**
     * Получение инвенторя своего инвенторя в UserInventoryDto c
     * Ячейками в InventoryUnitDto с UserItemDto
     */
    public UserInventoryDto getUserInventory(User user) {
        return UserInventoryDto.fromUser(user.getUserInventory());
    }
}
