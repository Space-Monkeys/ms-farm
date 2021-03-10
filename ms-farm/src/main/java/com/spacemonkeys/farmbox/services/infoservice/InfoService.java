package com.spacemonkeys.farmbox.services.infoservice;

import com.spacemonkeys.farmbox.Models.Info;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InfoService {
    Info save(Info info);
    void delete(Long id);
    Info update(Info info, Long id);
    List findAll();
    Optional<Info> findById(Long id);
}
