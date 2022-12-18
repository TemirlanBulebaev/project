package com.example.project.services;

import com.example.project.entities.GreenCoffee;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.payload.EditGreenCoffeeRequest;
import com.example.project.payload.GreenCoffeeRequest;
import com.example.project.repositories.GreenCoffeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GreenCoffeeService {

    private static final Logger logger = LogManager.getLogger(GreenCoffeeService.class);

    private final GreenCoffeeRepository greenCoffeeRepository;

    @Autowired
    public GreenCoffeeService(GreenCoffeeRepository greenCoffeeRepository) {
        this.greenCoffeeRepository = greenCoffeeRepository;
    }


    /**
     * Добавить зеленый кофе на склад
     */
    public Object addGreenCoffee(GreenCoffeeRequest greenCoffeeRequest) {
        GreenCoffee newGreenCoffee = new GreenCoffee();
        newGreenCoffee.setName(greenCoffeeRequest.getName());
        newGreenCoffee.setWeight(greenCoffeeRequest.getWeight());
        newGreenCoffee.setWaterLoss(greenCoffeeRequest.getWaterLoss());
        newGreenCoffee.setDensity(greenCoffeeRequest.getDensity());
        newGreenCoffee.setHumidity(greenCoffeeRequest.getHumidity());
        GreenCoffee savedGreenCoffee = saveGreenCoffee(newGreenCoffee);
        logger.info("На склад добавлен :" + savedGreenCoffee.getName());
        return Optional.of(savedGreenCoffee);
    }
    /**
     * Сохранение позиции в базе
     */
    private GreenCoffee saveGreenCoffee(GreenCoffee greenCoffee) {
        return greenCoffeeRepository.save(greenCoffee);
    }

    /**
     * Получить все позиции зеленого кофе
     */
    public List<GreenCoffee> getGreenCoffees() {
        return greenCoffeeRepository.findAll();
    }


    public void deleteGreenCoffee(Long id) {
        GreenCoffee greenCoffee = findById(id);
        logger.info("Позиция " + id + "была удалена");
        greenCoffeeRepository.deleteById(id);
    }

    GreenCoffee findById(Long id) {
        return greenCoffeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Позиция", "id", id));
    }

    public Object getGreenCoffee(Long id) {
        return findById(id);
    }

    public Optional editGreenCoffee(Long id, EditGreenCoffeeRequest editGreenCoffeeRequest) {
        GreenCoffee greenCoffee = findById(id);
        greenCoffee.setName(editGreenCoffeeRequest.getName());
        greenCoffee.setWeight(editGreenCoffeeRequest.getWeight());
        greenCoffee.setWaterLoss(editGreenCoffeeRequest.getWaterLoss());
        greenCoffee.setDensity(editGreenCoffeeRequest.getDensity());
        greenCoffee.setHumidity(editGreenCoffeeRequest.getHumidity());
        saveGreenCoffee(greenCoffee);
        logger.info("Позиция " + greenCoffee.getName() + " была изменена");
        return Optional.of(greenCoffee);
    }


}
