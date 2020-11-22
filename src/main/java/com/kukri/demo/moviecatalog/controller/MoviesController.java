
package com.kukri.demo.moviecatalog.controller;

import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Movie;
import com.kukri.demo.moviecatalog.service.MovieApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * movie controller  is a rest controller which
 * exposes the restAPI for ADD/Delete/Update and lists movies
 *
 * @author Shruthi Gowda
 */

@RestController
@RequestMapping("/v1")
public class MoviesController {

    @Autowired
    MovieApiService movieApiService;

    /*
     * Get  movies list.
     *
     * @return the list of movies
     */
    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieApiService.getMovies();

    }

    /*
     * Gets movies by id.
     *
     * @param moviesID the movie id
     * @return the movie  by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<Object> getMovieById(@PathVariable(value = "id") Long movieID)
            throws ResourceNotFoundException {
        Movie movie = movieApiService.getMovieById(movieID);
        return ResponseEntity.ok().body(movie);
    }

    /*
     * Create Movie Movie.
     * @param movie the Movie
     * @return the Movie
     */
    @PostMapping("/movies")
    public Movie createMovie(@Valid @RequestBody Movie movie) {
       return movieApiService.createMovie(movie);
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

        Movie updatedMovie = movieApiService.updateMovie(movieDetails,movieID);
        return ResponseEntity.ok(updatedMovie);
    }

    /**
     * Delete Movie map.
     *
     * @param movieId the Movie id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/movies/{id}")
    public Map<String, Boolean> deleteMovie(@PathVariable(value = "id") Long movieId) throws Exception {

        return movieApiService.deleteMovie(movieId);
    }

    /*
    firstname and lastname needs to sent as the path variable
    This API searches the movies based on the directoe first and last name
     */
    @RequestMapping(value = "/movies", params = {"directorfirstname","directorlastname"})
    public List<Movie> getMoviesByDirectorName(@RequestParam Optional<String>  directorfirstname,
                                               @RequestParam Optional<String>  directorlastname)throws ResourceNotFoundException
    {

            return movieApiService.findMoviesByDirector(directorfirstname, directorlastname);
    }

    @RequestMapping(value = "/movies", params = "rating")
    public List<Movie> getMoviesByRatings(@RequestParam(value = "rating") Optional<Double> rating) throws ResourceNotFoundException
    {
      return movieApiService.findMoviesByRating(rating);
    }


}

