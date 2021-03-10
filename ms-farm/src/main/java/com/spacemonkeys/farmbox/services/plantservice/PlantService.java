package com.spacemonkeys.farmbox.services.plantservice;

import com.spacemonkeys.farmbox.Models.Plant;

import java.util.List;
import java.util.Optional;

public interface PlantService {
 Plant save(Plant plant);
 void  delete(Long id);
 Plant update(Long id, Plant plant);
 List  findAll();
 Optional<Plant> findById(Long id);
}
