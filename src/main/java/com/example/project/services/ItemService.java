package com.example.project.services;

import com.example.project.dto.ItemDto;
import com.example.project.entities.Item;
import com.example.project.entities.PackageType;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.payload.EditItemRequest;
import com.example.project.payload.ItemRequest;
import com.example.project.repositories.ItemRepository;
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
    private final UserService userService;

    @Autowired
    public ItemService(
            ItemRepository itemRepository,
            UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    /**
     * Добавление Item
     */
    public Optional<Item> addItem(ItemRequest itemRequest) {

        Item newItem = new Item();
        newItem.setName(itemRequest.getName());
        newItem.setDescription(itemRequest.getDescription());
        newItem.setWeight(itemRequest.getWeight());
        newItem.setPrice(itemRequest.getPrice());
        newItem.setActive(true);
        Item savedItem = saveItem(newItem);
        logger.info("Создан новый товар :" + savedItem.getName());
        return Optional.of(savedItem);
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


//    /**
//     * Получение Items
//     * Если ADMIN -> page Items, если USER -> set ItemsDto
//     */
//    public Object getItems(Pageable pageable, JwtUser jwtUser) {
//        List<String> roles = jwtUser.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//        if (roles.contains("ROLE_ADMIN")) {
//            return getPageItemFromAdmin(pageable);
//        }
//        return getItemsDtoFromUser();
//    }
//
//    /**
//     * Получение Item
//     * Если ADMIN -> Item, если USER -> ItemDto
//     */
//    public Object getItem(Long itemId, JwtUser jwtUser) {
//        List<String> roles = jwtUser.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//        if (roles.contains("ROLE_ADMIN")) {
//            return findById(itemId);
//        }
//        Item item = findById(itemId);
//        if (!item.isActive()) {
//            logger.error("Item  " + itemId + " не активен");
//            throw new ResourceNotFoundException("Price", "active", true);
//        }
//        return getItemDto(itemId);
//    }
//
//    /**
//     * Получение у Item списка всех Price
//     * Если ADMIN -> set prices, если USER -> set pricesDto
//     */
//    public Object getItemprices(Long itemId, JwtUser jwtUser) {
//        List<String> roles = jwtUser.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//
//        if (roles.contains("ROLE_ADMIN")) {
//            return getItempricesFromAdmin(itemId);
//        }
//        return getItempricesFromUser(itemId);
//    }
//
//    /**
//     * Покупка UsetItem (копии Item) за внутреннюю валюту
//     */
//    public Set<InventoryUnitDto> buyItem(Long itemId, String amountItems, String currencyTitle, JwtUser jwtUser) {
//
//        Item item = findById(itemId);
//
//        if (!validateAmountItems(amountItems)) {
//            logger.error("Неверный формат суммы Items :" + amountItems);
//            throw new InvalidAmountFormat("Сумма", "Item", amountItems);
//        }
//
//        String cost = priceService.getCostInCurrency(item.getPrices(), currencyTitle);
//        return userService.getSavedInventoryUnit(jwtUser, currencyTitle, cost, amountItems, item);
//    }
//

//
//    /**
//     * Изменение Item (без цен)
//     */
//    @Override
//    public Optional<Item> editItem(long id, EditItemRequest editItemRequest) {
//
//        Item item = findById(id);
//        item.setName(editItemRequest.getName());
//        item.setType(editItemRequest.getType());
//        item.setDescription(editItemRequest.getDescription());
//        item.setActive(editItemRequest.isActive());
//        saveItem(item);
//        logger.info("Item " + item.getName() + " был изменен");
//        return Optional.of(item);
//    }
//
//    /**
//     * Добавление новой цены с валидацией
//     */
//    @Override
//    public Optional<Item> addItemPrice(PriceRequest priceRequest, Long itemId) {
//
//        Item item = findById(itemId);
//        Price newPrice = priceService.getValidatedPrice(item.getPrices(), priceRequest);
//        item.getPrices().add(newPrice);
//        priceService.saveprices(item.getPrices());
//        Item savedItem = saveItem(item);
//        logger.info("Добавлена новая цена для " + savedItem.getName());
//        return Optional.of(savedItem);
//    }
//
//    /**
//     * Изменение и удаление (выключение) Price
//     */
//    @Override
//    public Optional<Price> editItemPrice(PriceRequest priceRequest, Long priceId) {
//        return priceService.editPrice(priceRequest, priceId);
//    }
//
//    /**
//     * Удаление (Выключение) Item
//     */
//    @Override
//    public void deleteItem(long itemId) {
//
//        Item item = findById(itemId);
//        priceService.deleteprices(item.getPrices());
//        item.setActive(false);
//        saveItem(item);
//        logger.info("Item " + itemId + " был выключен");
//    }
//
//    /**
//     * Получение страницы со всеми Item
//     */
//    private Page<Item> getPageItemFromAdmin(Pageable pageable) {
//        return findAllItem(pageable);
//    }
//
//    /**
//     * Получение всех Price у айтема (влючая выкленные)
//     */
//    private Set<Price> getItempricesFromAdmin(Long itemId) {
//        Item item = findById(itemId);
//        return item.getPrices();
//    }
//
//    /**
//     * Получение списка всех ItemDto
//     */
//    private Set<ItemDto> getItemsDtoFromUser() {
//        Set<Item> items = findAllByActive(true);
//        if (items.isEmpty()) {
//            logger.info("Нет активных Item");
//            throw new ResourceNotFoundException("Item", "active", true);
//        }
//
//        Set<ItemDto> itemsDto = items.stream().map(item -> getItemDto(item.getId())).collect(Collectors.toSet());
//        return itemsDto;
//    }
//
//    /**
//     * Получение списка всех PriceDto у конкретного Item
//     */
//    private Set<PriceDto> getItempricesFromUser(Long itemId) {
//        Item item = findById(itemId);
//        return priceService.getItempricesDto(item.getPrices());
//    }
//
//    /**
//     * Получение ItemDto
//     */
//    private ItemDto getItemDto(Long id) {
//        return ItemDto.fromUser(findById(id));
//    }
//
//    private Set<Item> findAllByActive(boolean active) {
//        Set<Item> activeItems = itemRepository.findAllByActive(active);
//        if (activeItems == null) {
//            logger.error("Нет активных Item");
//            throw new ResourceNotFoundException("Items", "active", true);
//        }
//        return activeItems;
//    }
//
//    private Page<Item> findAllItem(Pageable pageable) {
//        return itemRepository.findAll(pageable);
//    }
//
//    private Item findById(Long id) {
//        return itemRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
//    }
//
//    public Set<Item> saveItems(Set<Item> items) {
//        return items.stream().map(item -> saveItem(item)).collect(Collectors.toSet());
//    }
//

//
//    /**
//     * Проверка суммы
//     */
//    private boolean validateAmountItems(String amountItems)
//    {
//        try {
//            int value = Integer.parseInt(amountItems);
//            if (value <= 0) {
//                return false;
//            } else {
//                return true;
//            }
//
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
}
