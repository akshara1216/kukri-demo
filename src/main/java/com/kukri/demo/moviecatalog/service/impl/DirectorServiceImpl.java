package com.kukri.demo.moviecatalog.service.impl;

import com.kukri.demo.moviecatalog.dao.DirectorRepository;
import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Director;
import com.kukri.demo.moviecatalog.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    DirectorRepository directorRepository;

    @Override
    public List<Director> getDirectors() {
        return directorRepository.findAll();
    }

    @Override
    public Director createDirector(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public Director getDirectorById(Long directorId) throws ResourceNotFoundException{
        Director director=
                directorRepository
                        .findById(directorId)
                .orElseThrow(()->new ResourceNotFoundException("No director found "+directorId));
        return director;

    }

    @Override
    public Director updateDirector(Director directordetails,Long directorId) throws ResourceNotFoundException {
        Director director =
                directorRepository
                        .findById(directorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Director not found on :: " + directorId));
        director.setCountry(directordetails.getCountry());
        director.setFirstName(directordetails.getFirstName());
        director.setLastName(directordetails.getLastName());
        director.setFilmIndustry(directordetails.getFilmIndustry());
        final Director updateDirector = directorRepository.save(director);
        return updateDirector;
    }

    @Override
    public Map<String, Boolean> deleteDirector(Long directorId) throws ResourceNotFoundException {
        Director director =
                directorRepository
                        .findById(directorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Director not found on :: " + directorId));

        directorRepository.delete(director);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",true);
        return response;
    }
}
