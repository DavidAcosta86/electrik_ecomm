package com.electrik.electrik_ecomm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.electrik.electrik_ecomm.repositories.FactoryRepository;
import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.entities.Factory;

@Service
public class FactoryService {
    @Autowired
    private FactoryRepository factoryRepository;

    private void CreateFactory(String factoryName) throws MyException {
        validateFactoryName(factoryName);
        Factory factory = new Factory();
        factory.setFactoryName(factoryName);
        factoryRepository.save(factory);

    }

    private void ModifyFactory(String factoryName) throws MyException {
        validateFactoryName(factoryName);
        Factory factory = factoryRepository.findByFactoryName(factoryName)
                .orElseThrow(() -> new MyException("Factory with name " + factoryName + " does not exist"));
        factoryRepository.save(factory);
    }

    public void validateFactoryName(String factoryName) throws MyException {
        if (factoryName == null || factoryName.isEmpty()) {
            throw new MyException("Factory name cannot be null or empty");
        }
        if (factoryRepository.findByFactoryName(factoryName) != null) {
            throw new MyException("Factory with name " + factoryName + " already exists!!!");
        }

    }

}
