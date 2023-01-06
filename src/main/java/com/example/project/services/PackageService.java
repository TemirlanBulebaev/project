package com.example.project.services;

import com.example.project.entities.Package;
import com.example.project.entities.PackageType;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.payload.EditPackageRequest;
import com.example.project.payload.PackageRequest;
import com.example.project.repositories.PackageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    private static final Logger logger = LogManager.getLogger(PackageService.class);
    private final PackageRepository packageRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    /**
     * Добавить упаковку на склад
     */
    public Object addPackage(PackageRequest packageRequest) { //TODO добавить логи и ошибку о несуществовании
        if (packageRepository.existsByName(packageRequest.getName())){
            Package existPackage = packageRepository.findByName(packageRequest.getName());
            existPackage.setAmount(existPackage.getAmount() + packageRequest.getAmount());
            Package editPackage = packageRepository.save(existPackage);
            return Optional.of(editPackage);
        } else {
            Package newPackage = new Package();
            newPackage.setName(packageRequest.getName());
            newPackage.setAmount(packageRequest.getAmount());
            Package savedPackage = savePackage(newPackage);
            logger.info("На склад добавлен :" + savedPackage.getName());
            return Optional.of(savedPackage);
        }
    }
    /**
     * Сохранение упаковки в базе
     */
    private Package savePackage(Package newPackage) {
        return packageRepository.save(newPackage);
    }

    public Object getPackage(Long id) {
        return findById(id);
    }

    private Package findById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Упаковка", "id", id));
    }

    public List<Package> getPackages() {
        return packageRepository.findAll();
    }

    public Optional editPackage(Long id, EditPackageRequest editPackageRequest) {
        Package editPackage = findById(id);
        editPackage.setName(editPackageRequest.getName());
        editPackage.setAmount(editPackageRequest.getAmount());
        savePackage(editPackage);
        logger.info("Информация об упаковке " + editPackage.getName() + " была изменена");
        return Optional.of(editPackage);

    }

    public void deletePackage(Long id) {
        logger.info("Информация об упаковке " + id + "была удалена");
        packageRepository.deleteById(id);
    }

}
