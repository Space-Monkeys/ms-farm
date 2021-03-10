package com.spacemonkeys.farmbox.repository;

import com.spacemonkeys.farmbox.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
