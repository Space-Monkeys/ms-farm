package com.spacemonkeys.farmbox.repository;

import com.spacemonkeys.farmbox.Models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant,Long> {
}
