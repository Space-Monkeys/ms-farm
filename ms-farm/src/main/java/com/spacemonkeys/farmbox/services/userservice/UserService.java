package com.spacemonkeys.farmbox.services.userservice;

import com.spacemonkeys.farmbox.Models.Users;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Users save(Users user);
    void delete(Long id);
    Optional<Users> update(Users user, Long id);
    List findAll();
    Optional<Users> findById(Long id);

}
