package com.electrik.electrik_ecomm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.electrik.electrik_ecomm.repositories.FactoryRepository;

import jakarta.transaction.Transactional;

import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.entities.Factory;

@Service
public class FactoryService {

    @Autowired
    private FactoryRepository factoryRepository;

    @Transactional
    public void CreateFactory(String factoryName) throws MyException {
        validateFactoryName(factoryName);
        Factory factory = new Factory();
        factory.setFactoryName(factoryName);
        factoryRepository.save(factory);

    }

    @Transactional
    public void ModifyFactory(UUID id, String factoryName) throws MyException {
        validateFactoryName(factoryName);
        Factory factory = factoryRepository.findById(id).get();
        if (factory == null) {
            throw new MyException("factoty does not exist");
        }

        factory.setFactoryName(factoryName);
        factoryRepository.save(factory);
    }

    @Transactional
    public List<Factory> ListAllFactories() throws MyException {

        if (factoryRepository.count() == 0) {
            throw new MyException("No factories found in the system");
        }
        List<Factory> factories = new ArrayList<>();
        factories = factoryRepository.findAll();
        return factories;

    }

    @Transactional
    public void DeleteFactory(UUID id) throws MyException {
        Optional<Factory> factory = factoryRepository.findById(id);
        if (factory.isPresent()) {
            factoryRepository.delete(factory.get());
        } else {
            throw new MyException("The specified ID was not found!");
        }
    }

    @Transactional
    private void validateFactoryName(String factoryName) throws MyException {
        if (factoryName == null || factoryName.isEmpty()) {
            throw new MyException("Factory name cannot be null or empty");
        }
        if (factoryRepository.findByFactoryName(factoryName).isPresent()) { // Cambiar a .isPresent()
            throw new MyException("Factory with name " + factoryName + " already exists!!!");
        }
    }

    @Transactional
    public Factory getOne(UUID id) {
        return factoryRepository.findById(id).orElse(null);
    }

}
