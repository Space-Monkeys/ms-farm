package com.spacemonkeys.farmbox.controllers;

import com.spacemonkeys.farmbox.Models.Info;
import com.spacemonkeys.farmbox.dto.InfoDTO;
import com.spacemonkeys.farmbox.services.infoservice.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/farmbox"})
public class InfoController {

    @Autowired
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping
    public List findAll(){
         return this.infoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Info create(@RequestBody InfoDTO dto){
        return this.infoService.save(dto.toInfo());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update (@PathVariable Long id, @RequestBody @Valid InfoDTO dto){
        Info updater = infoService.update(dto.toInfo(),id);
        return ResponseEntity.status(HttpStatus.OK).body(updater);
    }

    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        infoService.delete(id);
    }

}
