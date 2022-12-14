package com.example.project.repositories;

import com.example.project.entities.Sticker;
import com.example.project.entities.StickerType;
import com.example.project.payload.StickerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

    Sticker findByName(StickerType stickerType);
}
