package com.kukri.demo.moviecatalog.controller;


import com.kukri.demo.moviecatalog.dao.DirectorRepository;
import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * director controller  is a rest controller which
 * exposes the restAPI for ADD/Delete/Update and lists directors
 *
 * @author Shruthi Gowda
 */

@RestController
@RequestMapping("/api/v1")
public class DirectorController {
    @Autowired
    DirectorRepository directorRepository;

    /*
     * Get  directors list.
     *
     * @return the list of directors
     */
    @GetMapping("/listdirectors")
    public List<Director> getDirectors() {
        return directorRepository.findAll();

    }

    /*
     * Gets directors by id.
     *
     * @param directorsID the directorId
     * @return the director  by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/director/{id}")
    public ResponseEntity<Object> getDirectorById(@PathVariable(value = "id") Long directorId)
            throws ResourceNotFoundException {
       Director director=
                directorRepository
                        .findById(directorId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " +  directorId));
        return ResponseEntity.ok().body(director);
    }
    /*
     * Create director director.
     * @param director the director
     * @return the director
     */
    @PostMapping("/addDirector")
    public Director createdirector(@Valid @RequestBody Director director) {
        return directorRepository.save(director);
    }
    /**
     * Update director response entity.
     *
     * @param directorId the director id
     * @param directordetails the  details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/updateDirector/{id}")
    public ResponseEntity<Director> updateDirector(
            @PathVariable(value = "id") Long directorId, @Valid @RequestBody Director directordetails)
            throws ResourceNotFoundException {

        Director director =
                directorRepository
                        .findById(directorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Director not found on :: " + directorId));
        director.setCountry(directordetails.getCountry());
        director.setFirstName(directordetails.getFirstName());
        director.setLastName(directordetails.getLastName());
        director.setFlimIndustry(directordetails.getFlimIndustry());
        final Director updateDirector = directorRepository.save(director);
        return ResponseEntity.ok(updateDirector);
    }

    /**
     * Delete director
     *
     * @param  directorId
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/deletedirector/{id}")
    public Map<String, Boolean> deleteDirector(@PathVariable(value = "id") Long directorId) throws Exception {
        Director director =
                directorRepository
                        .findById(directorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Director not found on :: " + directorId));

        directorRepository.delete(director);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}

