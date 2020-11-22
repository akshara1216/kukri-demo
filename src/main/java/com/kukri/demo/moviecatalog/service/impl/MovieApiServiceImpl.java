package com.kukri.demo.moviecatalog.service.impl;

import com.kukri.demo.moviecatalog.dao.DirectorRepository;
import com.kukri.demo.moviecatalog.dao.MoviesRepository;
import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Movie;
import com.kukri.demo.moviecatalog.service.MovieApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MovieApiServiceImpl implements MovieApiService {

    @Autowired
    MoviesRepository moviesRepository;

    @Autowired
    DirectorRepository directorRepository;
    @Override
    public List<Movie> getMovies() {
        return moviesRepository.findAll();
    }

    @Override
    public Movie createMovie(Movie movie) {
        return moviesRepository.save(movie);
    }

    @Override
    public Movie getMovieById(Long movieId) throws ResourceNotFoundException {
        Movie movie =
                moviesRepository
                        .findById(movieId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + movieId));
        return movie;
    }

    @Override
    public Movie updateMovie(Movie movieDetails,Long movieId) throws ResourceNotFoundException {
        Movie movie =
                moviesRepository
                        .findById(movieId)
                        .orElseThrow(() -> new ResourceNotFoundException("Movie not found on :: " + movieId));
        movie = new Movie(movieDetails);
        final Movie updatedMovie = moviesRepository.save(movie);
        return updatedMovie;
    }

    @Override
    public Map<String, Boolean> deleteMovie(Long MovieId) throws ResourceNotFoundException {
        Movie Movie =
                moviesRepository
                        .findById(MovieId)
                        .orElseThrow(() -> new ResourceNotFoundException("Movie not found on :: " + MovieId));

        moviesRepository.delete(Movie);
        Map<String, Boolean> response = new HashMap<>();
         response.put("deleted", Boolean.TRUE);
         return response;
    }

    @Override
    public List<Movie> findMoviesByDirector(Optional<String> directorFirstName, Optional<String> directorLastname) throws ResourceNotFoundException {

        Long directorID;
        try {
             directorID = directorRepository.findDirectorByFirstNameAndLastName(directorFirstName, directorLastname);
        }catch (Exception e)
        {
            throw new ResourceNotFoundException("No movies found " + directorFirstName + directorLastname);
        }
        List<Movie> movies;

             movies = moviesRepository.findMoviesByDirectorName(directorID);
             if(movies.isEmpty())
             {
                 throw new ResourceNotFoundException("No movies found " + directorFirstName + directorLastname);
             }

        return movies;
    }

    @Override
    public List<Movie> findMoviesByRating(Optional<Double> rating) throws ResourceNotFoundException {
        List<Movie> movies =  moviesRepository.findMoviesByMovieRating(rating);
        if(movies.isEmpty())
        {
            throw new ResourceNotFoundException("No Movies Found");
        }
        return movies;
    }
}
