package com.spacemonkeys.farmbox.repository;

import com.spacemonkeys.farmbox.Models.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
}
