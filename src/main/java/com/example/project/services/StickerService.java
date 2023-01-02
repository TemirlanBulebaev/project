package com.example.project.services;

import com.example.project.entities.Package;
import com.example.project.entities.Sticker;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.payload.EditPackageRequest;
import com.example.project.payload.EditStickerRequest;
import com.example.project.payload.PackageRequest;
import com.example.project.payload.StickerRequest;
import com.example.project.repositories.StickerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StickerService {

    private static final Logger logger = LogManager.getLogger(StickerService.class);

    private final StickerRepository stickerRepository;

    @Autowired
    public StickerService(StickerRepository stickerRepository) {
        this.stickerRepository = stickerRepository;
    }

    /**
     * Добавить наклейку на склад
     */
    public Object addSticker(StickerRequest stickerRequest) {//TODO Сделать логи и ошибку при некорректном добавлении стикера
        if (stickerRepository.existsByName(stickerRequest.getName())) {
            Sticker existSticker = stickerRepository.findByName(stickerRequest.getName());
            existSticker.setAmount(existSticker.getAmount()+stickerRequest.getAmount());
            Sticker editSticker = stickerRepository.save(existSticker);
            return Optional.of(editSticker);
        } else {
            Sticker newSticker = new Sticker();
            newSticker.setName(stickerRequest.getName());
            newSticker.setAmount(stickerRequest.getAmount());
            Sticker savedSticker = saveSticker(newSticker);
            logger.info("На склад добавлен :" + savedSticker.getName());
            return Optional.of(savedSticker);
        }
    }

    private Sticker saveSticker(Sticker newSticker) {
        return stickerRepository.save(newSticker);
    }

    public Object getSticker(Long id) {
        return findById(id);
    }

    private Sticker findById(Long id) {
        return stickerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Наклейка", "id", id));
    }

    public List<Sticker> getStickers() {
        return stickerRepository.findAll();
    }

    public Optional editSticker(Long id, EditStickerRequest editStickerRequest) {
        Sticker editSticker = findById(id);
        editSticker.setName(editStickerRequest.getName());
        editSticker.setAmount(editStickerRequest.getAmount());
        saveSticker(editSticker);
        logger.info("информация о наклейке " + editSticker.getName() + " была изменена");
        return Optional.of(editSticker);
    }

    public void deleteSticker(Long id) {
        Sticker delSticker = findById(id);
        logger.info("Информация о наклейке " + id + "была удалена");
        stickerRepository.deleteById(id);
    }
}
