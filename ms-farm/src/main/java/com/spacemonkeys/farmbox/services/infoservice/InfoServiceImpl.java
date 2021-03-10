package com.spacemonkeys.farmbox.services.infoservice;

import com.spacemonkeys.farmbox.Models.Info;
import com.spacemonkeys.farmbox.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service

public class InfoServiceImpl implements InfoService {

    @Autowired
    private final InfoRepository infoRepository;

    public InfoServiceImpl(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }


    @Override
    public Info save(Info info) {
        return this.infoRepository.save(info);
    }

    @Override
    public void delete(Long id) {
        Optional<Info> data  = infoRepository.findById(id);
        Assert.isTrue(data.isPresent(), "Register not found");
        this.infoRepository.deleteById(id);
    }

    @Override
    public Info update(Info info, Long id) {
        Optional<Info> updating = this.infoRepository.findById(id);
        Assert.isTrue(updating.isPresent(),"Register not found");
        try{
            updating.map( up -> {
               up.setLight(info.getLight());
               up.setNutrients(info.getNutrients());
               up.setConductivity(info.getConductivity());
               up.setHumidity(info.getHumidity());
               up.setPh(info.getPh());
               up.setLight(info.getLight());
               Info updater = this.infoRepository.save(up);
               return updater;

            });
        }catch (Exception e){
            System.out.println(e);
        }
        return info;
    }

    @Override
    public List findAll() {
        return this.infoRepository.findAll();
    }

    @Override
    public Optional<Info> findById(Long id) {
        return this.infoRepository.findById(id);
    }
}
