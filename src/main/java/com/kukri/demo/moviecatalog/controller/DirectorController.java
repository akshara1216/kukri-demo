package com.kukri.demo.moviecatalog.controller;


import com.kukri.demo.moviecatalog.dao.DirectorRepository;
import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Director;
import com.kukri.demo.moviecatalog.service.DirectorApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * director controller  is a rest controller which
 * exposes the restAPI for ADD/Delete/Update and lists directors
 *
 * @author Shruthi Gowda
 */

@RestController
@RequestMapping("/v1")
public class DirectorController {

     @Autowired
    DirectorApiService directorApiService;

    /*
     * Get  directors list.
     *
     * @return the list of directors
     */
    @GetMapping("/directors")
    public List<Director> getDirectors() {
        return directorApiService.getDirectors();

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
        Director director = directorApiService.getDirectorById(directorId);
        return ResponseEntity.ok().body(director);
    }
    /*
     * Create director director.
     * @param director the director
     * @return the director
     */
    @PostMapping("/director")
    public Director createdirector(@Valid @RequestBody Director director) {
      return directorApiService.createDirector(director);
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

        Director updateDirector=  directorApiService.updateDirector(directordetails,directorId);
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

        return directorApiService.deleteDirector(directorId);
    }


}

