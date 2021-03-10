package com.spacemonkeys.farmbox.controllers;

import com.spacemonkeys.farmbox.Models.Users;
import com.spacemonkeys.farmbox.dto.UserDto;
import com.spacemonkeys.farmbox.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List getAll(){
        return this.userService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save (@RequestBody @Valid UserDto dto){
        Users data = this.userService.save(dto.toUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updating (@PathVariable Long id, @RequestBody UserDto dto){
        Optional<Users> updater = userService.update(dto.toUser(), id);
        return ResponseEntity.ok(updater);
    }

    @DeleteMapping(path  = "/{id}")
    public void delete (@PathVariable Long id){
        userService.delete(id);
    }


}
