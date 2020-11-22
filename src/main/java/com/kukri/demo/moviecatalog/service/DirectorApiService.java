package com.kukri.demo.moviecatalog.service;

import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Director;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DirectorApiService {
    List<Director> getDirectors();
    Director createDirector(Director director);
    Director getDirectorById(Long directorId) throws ResourceNotFoundException;
    Director updateDirector(Director director,Long directorID)throws ResourceNotFoundException;
    Map<String ,Boolean> deleteDirector(Long directorId) throws ResourceNotFoundException;

}
