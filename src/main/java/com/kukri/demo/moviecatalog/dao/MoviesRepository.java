package com.kukri.demo.moviecatalog.dao;

import com.kukri.demo.moviecatalog.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author Shruthi Gowda
 * implements the DB repository functionality for the model Movie.
 */
@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {
    @Query( value="SELECT * from Movie m where m.director_id = ?1",nativeQuery = true)
    List<Movie> findMoviesByDirectorName(Long directorId) ;

    @Query( value="SELECT * from Movie m where m.movie_rating > ''+?1 OR m.movie_rating = ''+?1",nativeQuery = true)
    List<Movie> findMoviesByMovieRating(Optional<Double> movieRating);
}
