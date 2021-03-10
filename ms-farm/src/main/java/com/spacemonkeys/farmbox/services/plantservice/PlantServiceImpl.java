package com.spacemonkeys.farmbox.services.plantservice;

import com.spacemonkeys.farmbox.Models.Plant;
import com.spacemonkeys.farmbox.Models.Users;
import com.spacemonkeys.farmbox.repository.PlantRepository;
import com.spacemonkeys.farmbox.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service

public class PlantServiceImpl implements PlantService {

    @Autowired
    private final PlantRepository repository;
    public PlantServiceImpl(PlantRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private UsersRepository usersRepository;


    @Override
    public Plant save(Plant plant) {
        Assert.isTrue(plant.getUser() != null , "The user field cannot be null");
        Optional<Users> check =  usersRepository.findById(plant.getUser());
        Assert.isTrue(check.isPresent(),"Register not found");
        return repository.save(plant);
    }

    @Override
    public void delete(Long id) {
        Optional<Plant> data  = repository.findById(id);
        Assert.isTrue(data.isPresent(),"Register not found");
        repository.deleteById(id);
    }

    @Override
    public Plant update(Long id, Plant plant) {

        Optional<Plant> search = repository.findById(id);
        Assert.isTrue(search.isPresent(),"Register not found");

        Plant updater  =  repository.findById(id)
        .map( up -> {
            up.setType(plant.getType());
            up.setUser(plant.getUser());
            up.setCicle(plant.getCicle());
            up.setAge(plant.getAge());
            up.setInfo(plant.getInfo());
            Plant data  = repository.save(up);
            return data;
        }).get();

        return updater;
    }

    @Override
    public List findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Plant> findById(Long id) {
        return repository.findById(id);
    }
}
