
package com.kukri.demo.moviecatalog.controller;

import com.kukri.demo.moviecatalog.dao.DirectorRepository;
import com.kukri.demo.moviecatalog.dao.MoviesRepository;
import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * movie controller  is a rest controller which
 * exposes the restAPI for ADD/Delete/Update and lists movies
 *
 * @author Shruthi Gowda
 */

@RestController
@RequestMapping("/api/v1")
public class MoviesController {
    @Autowired
    MoviesRepository moviesRepository;

    @Autowired
    DirectorRepository directorRepository;

    /*
     * Get  movies list.
     *
     * @return the list of movies
     */
    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return moviesRepository.findAll();

    }

    /*
     * Gets movies by id.
     *
     * @param moviesID the movie id
     * @return the movie  by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<Object> getMovieById(@PathVariable(value = "id") Long moviesID)
            throws ResourceNotFoundException {
        Movie movie =
                moviesRepository
                        .findById(moviesID)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + moviesID));
        return ResponseEntity.ok().body(movie);
    }

    /*
     * Create Movie Movie.
     * @param movie the Movie
     * @return the Movie
     */
    @PostMapping("/movies")
    public Movie createMovie(@Valid @RequestBody Movie movie) {
        return moviesRepository.save(movie);
    }

    /**
     * Update Movie response entity.
     *
     * @param movieID      the Movie id
     * @param movieDetails the Movie details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable(value = "id") Long movieID, @Valid @RequestBody Movie movieDetails)
            throws ResourceNotFoundException {

        Movie movie =
                moviesRepository
                        .findById(movieID)
                        .orElseThrow(() -> new ResourceNotFoundException("Movie not found on :: " + movieID));
        movie = new Movie(movieDetails);
        final Movie updatedMovie = moviesRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    /**
     * Delete Movie map.
     *
     * @param MovieId the Movie id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/movie/{id}")
    public Map<String, Boolean> deleteMovie(@PathVariable(value = "id") Long MovieId) throws Exception {
        Movie Movie =
                moviesRepository
                        .findById(MovieId)
                        .orElseThrow(() -> new ResourceNotFoundException("Movie not found on :: " + MovieId));

        moviesRepository.delete(Movie);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /*
    firstname and lastname needs to sent as the path variable
    This API searches the movies based on the directoe first and last name
     */
    @GetMapping("/findmovies/{directorfirstname}/{directorlastname}")
    public List<Movie> getMoviesByDirectorName(@PathVariable(value = "directorfirstname")
                                             String directorfirstname, @PathVariable(value = "directorlastname") String directorlastname) throws ResourceNotFoundException {
        int directorID = directorRepository.findDirectorByFirstNameAndLastName(directorfirstname,directorlastname);
        List<Movie> movies =  moviesRepository.findMoviesByDirectorName(directorID);
        if(movies == null)
        {
            throw new ResourceNotFoundException("No Movies Found");
        }
        return movies;
    }

    @GetMapping("/findmovies/{rating}")
    public List<Movie> getMoviesByRatings(@PathVariable(value = "rating") double rating) throws ResourceNotFoundException {
        List<Movie> movies =  moviesRepository.findMoviesByMovieRating(rating);
        if(movies == null)
        {
            throw new ResourceNotFoundException("No Movies Found");
        }
        return movies;
    }


}

