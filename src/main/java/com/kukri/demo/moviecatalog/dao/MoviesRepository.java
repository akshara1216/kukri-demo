package com.kukri.demo.moviecatalog.dao;

import com.kukri.demo.moviecatalog.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Shruthi Gowda
 * implements the DB reposutory functionality for the model Movie.
 */
@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {
    @Query( value="SELECT * from Movie m where m.director_id = ?1",nativeQuery = true)
    List<Movie> findMoviesByDirectorName(int directorId) ;

    @Query( value="SELECT * from Movie m where m.movie_rating > ''+?1",nativeQuery = true)
    List<Movie> findMoviesByMovieRating(double movieRating);
}
