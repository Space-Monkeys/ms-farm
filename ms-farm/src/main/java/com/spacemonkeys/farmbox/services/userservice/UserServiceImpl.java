package com.spacemonkeys.farmbox.services.userservice;

import com.spacemonkeys.farmbox.Models.Users;
import com.spacemonkeys.farmbox.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public Users save(Users user) {

        Assert.isTrue(user.getName() != null, "The field name cannot be null");
        Assert.isTrue(user.getPassword() != null, "The field password cannot be null");

        return usersRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        Optional <Users> data  = usersRepository.findById(id);
        Assert.isTrue(data.isPresent(),"Register not found");
        usersRepository.deleteById(id);
    }

    @Override
    public Optional<Users> update(Users user, Long id) {

        Optional<Users> updating  = usersRepository.findById(id);
        Assert.isTrue(updating.isPresent(), "Register not found");

        try{
            updating.map( up -> {
                up.setPassword(user.getPassword());
                up.setName(user.getName());
                Users updater = usersRepository.save(up);
                return updater;
            });

        } catch (Exception e){
            System.out.println(e);
        }

        return updating;
    }

    @Override
    public List findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        Optional<Users> data = usersRepository.findById(id);
        return data;
    }
}
