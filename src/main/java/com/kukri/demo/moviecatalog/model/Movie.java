package com.kukri.demo.moviecatalog.model;


import com.kukri.demo.moviecatalog.util.BoardRating;
import com.kukri.demo.moviecatalog.util.FlimGenre;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

/**
 * The type User.
 *
 * @author Shruthi Gowda
 * This the model which holds the data of the movie
 */

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "movie_title", nullable = false)
    private String movieTitle;

    @Column(name = "director_id", nullable = false)
    private int directorID;

    @Column(name = "year_released", nullable = false)
    private String yearReleased;

    @Column(name = "genre")
    private String flimGenre;

    @Column(name = "board_rating")
    private String boardRating;

    @Column(name = "movie_rating")
    private double movieRating;

    public Movie() {
    }

    public Movie(Movie that)
   {
       this.setId(that.id);
       this.setMovieRating(that.getMovieRating());
       this.setBoardRating(that.getBoardRating());
       this.movieTitle = that.getMovieTitle();
       this.yearReleased = that.getYearReleased();
       this.directorID = that.getDirectorID();
       this.flimGenre = that.getFlimGenre();
   }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getDirectorID() {
        return directorID;
    }

    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }

    public String getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(String yearReleased) {
        this.yearReleased = yearReleased;
    }

    public String getFlimGenre() {
        return flimGenre;
    }

    public void setFlimGenre(String flimGenre) {
        this.flimGenre = flimGenre;
    }

    public String getBoardRating() {
        return boardRating;
    }

    public void setBoardRating(String boardRating) {
        this.boardRating = boardRating;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("movieTitle", movieTitle)
                .append("directorID", directorID)
                .append("yearReleased", yearReleased)
                .append("flimGenre", flimGenre)
                .append("boardRating", boardRating)
                .append("movieRating", movieRating)
                .toString();
    }
}
