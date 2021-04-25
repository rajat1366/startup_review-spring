package com.StartupReview.service;

import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.repository.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StartupService {
    @Autowired
    private StartupRepository startupRepository;

    public Startup saveStartup(Startup startup){
         return startupRepository.save(startup);
    }
    public Boolean findByName(String name){
        return startupRepository.existsByName(name);
    }

    public Page<Startup> getstartups(Pageable pageable){
        return startupRepository.findAllByOrderByDateTimeDesc(pageable);
    }
    public Page<Startup >getstartupsFromSearchData(String searchData,Pageable pageable){
        return  startupRepository.findByNameContainingOrDescriptionContaining(searchData,searchData, pageable);
    }

    public Optional<Startup> getstartupsById(long id) {
        return startupRepository.findById(id);
    }

    public void deleteById(long id) {
        startupRepository.deleteById(id);
    }
}
