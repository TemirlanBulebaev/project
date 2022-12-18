package com.example.project.services;

import com.example.project.entities.GreenCoffee;
import com.example.project.entities.RoastedCoffee;
import com.example.project.repositories.RoastedCoffeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoastedCoffeeService {

    private static final Logger logger = LogManager.getLogger(RoastedCoffeeService.class);

    private final RoastedCoffeeRepository roastedCoffeeRepository;
    private final GreenCoffeeService greenCoffeeService;

    @Autowired
    public RoastedCoffeeService(RoastedCoffeeRepository roastedCoffeeRepository, GreenCoffeeService greenCoffeeService) {
        this.roastedCoffeeRepository = roastedCoffeeRepository;
        this.greenCoffeeService = greenCoffeeService;
    }

    /**
     * Добавить жареный кофе на склад
     */
    public Optional addRoastCoffee(Long id, String amountBatch) {//TODO Прописать потерю валажности
        RoastedCoffee newRoastedCoffee  = new RoastedCoffee();
        Long amount = Long.parseLong(amountBatch);
        GreenCoffee greenCoffee = greenCoffeeService.findById(id);
        newRoastedCoffee.setName(greenCoffee.getName());
        Integer waterLoss = greenCoffee.getWaterLoss();
        Long rez = (long)((amount*12000*(1-waterLoss*0.01)));
        newRoastedCoffee.setWeight(rez);
        greenCoffee.setWeight(greenCoffee.getWeight()-12000);
        RoastedCoffee savedRoastedCoffee = saveRoastedCoffee(newRoastedCoffee);
        logger.info("Произошла обжарка:" + savedRoastedCoffee.getName());
        return Optional.of(savedRoastedCoffee);
    }
    private RoastedCoffee saveRoastedCoffee(RoastedCoffee newRoastedCoffee) {
        return roastedCoffeeRepository.save(newRoastedCoffee);
    }

}
