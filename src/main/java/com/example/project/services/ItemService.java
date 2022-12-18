package com.example.project.services;

import com.example.project.dto.InventoryUnitDto;
import com.example.project.dto.ItemDto;
import com.example.project.entities.*;
import com.example.project.entities.Package;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.payload.EditItemRequest;
import com.example.project.payload.ItemRequest;
import com.example.project.repositories.ItemRepository;
import com.example.project.repositories.PackageRepository;
import com.example.project.repositories.RoastedCoffeeRepository;
import com.example.project.repositories.StickerRepository;
import com.example.project.security.JwtUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private static final Logger logger = LogManager.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    private final PackageRepository packageRepository;

    private final StickerRepository stickerRepository;

    private final RoastedCoffeeRepository roastedCoffeeRepository;
    private final UserService userService;

    @Autowired
    public ItemService(
            ItemRepository itemRepository, PackageRepository packageRepository, StickerRepository stickerRepository, RoastedCoffeeRepository roastedCoffeeRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.packageRepository = packageRepository;
        this.stickerRepository = stickerRepository;
        this.roastedCoffeeRepository = roastedCoffeeRepository;
        this.userService = userService;
    }

    /**
     * Добавление Item
     */
    public Optional<Item> addItem(ItemRequest itemRequest) {

        Item newItem = new Item();
        Integer weight = itemRequest.getWeight();
        newItem.setName(itemRequest.getName());
        newItem.setCoffeeName(itemRequest.getCoffeeName());
        newItem.setDescription(itemRequest.getDescription());
        newItem.setWeight(weight);
        newItem.setPackageType(weight);
        newItem.setStickerType(weight);
        newItem.setPrice(itemRequest.getPrice());
        newItem.setActive(true);
        Item savedItem = saveItem(newItem);
        reduceConsumables(savedItem);//расходники
        logger.info("Создан новый товар :" + savedItem.getName());
        return Optional.of(savedItem);
    }

    /**
     * Списание расходников
     */
    private void reduceConsumables(Item savedItem){//расходники
        PackageType packageType = savedItem.getPackageType();
        StickerType stickerType =savedItem.getStickerType();
        String coffee = savedItem.getCoffeeName();
        Package warehousePackage = packageRepository.findByName(packageType);
        warehousePackage.setAmount(warehousePackage.getAmount()-1);
        packageRepository.save(warehousePackage);
        Sticker warehouseSticker = stickerRepository.findByName(stickerType);
        warehouseSticker.setAmount(warehouseSticker.getAmount()-1);
        stickerRepository.save(warehouseSticker);
        RoastedCoffee roastedCoffee = roastedCoffeeRepository.findByName(coffee);
        roastedCoffee.setWeight(roastedCoffee.getWeight() - savedItem.getWeight());
        roastedCoffeeRepository.save(roastedCoffee);
    }

        private Item saveItem(Item Item) {
        return itemRepository.save(Item);
    }


    /**
     * Получение Items
     * Если ADMIN -> page Items, если USER -> set ItemsDto
     */
    public Object getItems(Pageable pageable, JwtUser jwtUser) {
        List<String> roles = jwtUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (roles.contains("ROLE_ADMIN")) {
            return getPageItemFromAdmin(pageable);
        }
        return getItemsDtoFromUser();
    }

    /**
     * Получение страницы со всеми Item для ADMIN
     */
    private Page<Item> getPageItemFromAdmin(Pageable pageable) {
        return findAllItem(pageable);
    }
    private Page<Item> findAllItem(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }


    /**
     * Получение списка всех ItemDto
     */
    private Set<ItemDto> getItemsDtoFromUser() {
        Set<Item> Item = findAllByActive(true);
        if (Item.isEmpty()) {
            logger.info("Нет активных Item");
            throw new ResourceNotFoundException("Item", "active", true);
        }

        Set<ItemDto> itemsDto = Item.stream().map( item -> getItemDto(item.getId())).collect(Collectors.toSet());
        return itemsDto;
    }


    /**
     * Получение ItemDto
     */
    private ItemDto getItemDto(Long id) {
        return ItemDto.fromUser(findById(id));
    }

    private Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Товар", "id", id));
    }

    private Set<Item> findAllByActive(boolean active) {
        Set<Item> activeItem = itemRepository.findAllByActive(active);
        if (activeItem == null) {
            logger.error("Нет активных Item");
            throw new ResourceNotFoundException("Items", "active", true);
        }
        return activeItem;
    }

    /**
     * Удаление (Выключение) Item
     */
    public void deleteItem(long itemId) {
        Item Item = findById(itemId);
        Item.setActive(false);
        saveItem(Item);
        logger.info("Товар " + itemId + " был выключен");
    }

    /**
     * Получение Item
     * Если ADMIN -> Item, если USER -> ItemDto
     */
    public Object getItem(Long itemId, JwtUser jwtUser) {
        List<String> roles = jwtUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (roles.contains("ROLE_ADMIN")) {
            return findById(itemId);
        }
        Item Item = findById(itemId);
        if (!Item.getActive()) {
            logger.error("Товар " + itemId + " не активен");
            throw new ResourceNotFoundException("Price", "active", true);
        }
        return getItemDto(itemId);
    }

    /**
     * Изменение Item
     */
    public Optional<Item> editItem(long id, EditItemRequest editItemRequest) {

        Item Item = findById(id);
        Item.setName(editItemRequest.getName());
        Item.setDescription(editItemRequest.getDescription());
        Item.setWeight(editItemRequest.getWeight());
        Item.setPrice(editItemRequest.getPrice());
        Item.setActive(editItemRequest.getActive());
        saveItem(Item);
        logger.info("Item " + Item.getName() + " был изменен");
        return Optional.of(Item);
    }

    /**
     * Покупка UserItem (копии Item)
     */
    public Set<InventoryUnitDto> buyItem(Long itemId, String amountItems, JwtUser jwtUser) {

        Item item = findById(itemId);
        return userService.getSavedInventoryUnit(jwtUser, amountItems, item);
    }
}
