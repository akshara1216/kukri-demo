package com.kukri.demo.moviecatalog.controller;


import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Director;
import com.kukri.demo.moviecatalog.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


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
     DirectorService directorService;

    /*
     * Get  directors list.
     *
     * @return the list of directors
     */
    @GetMapping("/directors")
    public List<Director> getDirectors() {
        return directorService.getDirectors();

    }

    /*
     * Gets directors by id.
     *
     * @param directorsID the directorId
     * @return the director  by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/directors/{id}")
    public ResponseEntity<Object> getDirectorById(@PathVariable(value = "id") Long directorId)
            throws ResourceNotFoundException {
        Director director = directorService.getDirectorById(directorId);
        return ResponseEntity.ok().body(director);
    }
    /*
     * Create director director.
     * @param director the director
     * @return the director
     */
    @PostMapping("/directors")
    public Director createDirector(@Valid @RequestBody Director director) {
      return directorService.createDirector(director);
    }
    /**
     * Update director response entity.
     *
     * @param directorId the director id
     * @param directorDetails the  details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/directors/{id}")
    public ResponseEntity<Director> updateDirector(
            @PathVariable(value = "id") Long directorId, @Valid @RequestBody Director directorDetails)
            throws ResourceNotFoundException {
        Director updateDirector=  directorService.updateDirector(directorDetails, directorId);
        return ResponseEntity.ok(updateDirector);
    }

    /**
     * Delete director
     *
     * @param  directorId
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/directors/{id}")
    public Map<String, Boolean> deleteDirector(@PathVariable(value = "id") Long directorId) throws Exception {
        return directorService.deleteDirector(directorId);
    }
}

