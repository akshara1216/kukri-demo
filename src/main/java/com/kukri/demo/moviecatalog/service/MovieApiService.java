package com.kukri.demo.moviecatalog.service;

import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Movie;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieApiService {
    List<Movie> getMovies();
    Movie createMovie(Movie movie);
    Movie getMovieById(Long movieId) throws ResourceNotFoundException;
    Movie updateMovie(Movie movie,Long movieID) throws ResourceNotFoundException;
    Map<String ,Boolean> deleteMovie(Long movieId) throws ResourceNotFoundException;
    List<Movie> findMoviesByDirector(Optional<String> directorFirstName, Optional<String> directorLastname)throws ResourceNotFoundException;
    List<Movie> findMoviesByRating(Optional<Double> rating) throws ResourceNotFoundException;
}
