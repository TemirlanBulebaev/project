package com.example.project.services;

import com.example.project.entities.GreenCoffee;
import com.example.project.entities.RoastedCoffee;
import com.example.project.exceptions.NotEnoughException;
import com.example.project.repositories.RoastedCoffeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

//    /**
//     * Добавить жареный кофе на склад
//     */
//    public Optional<RoastedCoffee> addRoastCoffee(Long id, String amountBatch) {//TODO Декомпозировать логику
//        RoastedCoffee newRoastedCoffee  = new RoastedCoffee();//Создать новую позицию жаренного
//        Long amount = Long.parseLong(amountBatch);//получаем количество батчей
//
//        GreenCoffee greenCoffee = greenCoffeeService.findById(id);//находим зеленый кофе
//
//        newRoastedCoffee.setName(greenCoffee.getName()); // получаем его имя
//        Integer waterLoss = greenCoffee.getWaterLoss(); // и потерю влаги
//        Long rez = (long)((amount*12000*(1-waterLoss*0.01))); // результат ужарки (кофе на выходе)
//        newRoastedCoffee.setWeight(rez);
//        greenCoffee.setWeight(greenCoffee.getWeight()-12000); //спишем зеленку
//        RoastedCoffee savedRoastedCoffee = saveRoastedCoffee(newRoastedCoffee);
//        logger.info("Произошла обжарка:" + savedRoastedCoffee.getName());
//        return Optional.of(savedRoastedCoffee);
//    }
    /**
     * Добавить жареный кофе на склад
     */
    public Optional<RoastedCoffee> addRoastCoffee(Long id, String amountBatch) {//TODO Декомпозировать логику вынести в методы
        GreenCoffee greenCoffee = greenCoffeeService.findById(id);//находим зеленый кофе
        if (greenCoffee.getWeight()-12000 < 0){ //12000 - загрузка партии
            throw new NotEnoughException("Зеленого кофе", greenCoffee.getName(), greenCoffee.getWeight());
        }


        greenCoffee.setWeight(greenCoffee.getWeight()-12000); //спишем зеленку
        Long amount = Long.parseLong(amountBatch);//получаем количество батчей
        Integer waterLoss = greenCoffee.getWaterLoss(); // и потерю влаги
        Long rezult = (long)((amount*12000*(1-waterLoss*0.01))); // результат ужарки (кофе на выходе)

        if (roastedCoffeeRepository.existsByName(greenCoffee.getName())) {
            RoastedCoffee existRoastedCoffee = roastedCoffeeRepository.findByName(greenCoffee.getName());
            existRoastedCoffee.setWeight(existRoastedCoffee.getWeight()+rezult);
            roastedCoffeeRepository.save(existRoastedCoffee);//TODO Перевести в перемернную для сохранения в ретурн
            return Optional.of(existRoastedCoffee);
        } else {

            RoastedCoffee newRoastedCoffee = new RoastedCoffee();//Создать новую позицию жаренного
            newRoastedCoffee.setName(greenCoffee.getName()); // получаем его имя
            newRoastedCoffee.setWeight(rezult);
            RoastedCoffee savedRoastedCoffee = saveRoastedCoffee(newRoastedCoffee);
            logger.info("Произошла обжарка:" + savedRoastedCoffee.getName());
            return Optional.of(savedRoastedCoffee);
        }
    }


    private RoastedCoffee saveRoastedCoffee(RoastedCoffee newRoastedCoffee) {
        return roastedCoffeeRepository.save(newRoastedCoffee);
    }

    public List<RoastedCoffee> getRoastedCoffee() {
        return roastedCoffeeRepository.findAll();
    }
}
