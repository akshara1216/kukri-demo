
package com.kukri.demo.moviecatalog;

import com.kukri.demo.moviecatalog.model.Movie;
import com.kukri.demo.moviecatalog.util.BoardRating;
import com.kukri.demo.moviecatalog.util.FlimGenre;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoviecatalogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieRestApiTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testgetMovies() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/movies",
                HttpMethod.GET, entity, String.class);

        Assert.assertNotNull(response.getBody());
    }
    @Test
    public void testgetMovieByID() {
        Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/1", Movie.class);
        Assert.assertNotNull(movie);
    }

    @Test
    public void testCreateMovie() {
        Movie movie = new Movie();
        movie.setMovieRating(6.3);
        movie.setDirectorID(2);
        movie.setFlimGenre("COMEDY");
        movie.setMovieTitle("Hangover");
        movie.setYearReleased("2000");
        movie.setBoardRating("15");
        ResponseEntity<Movie> postResponse = restTemplate.postForEntity(getRootUrl() + "/movies", movie, Movie.class);
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }

   @Test
    public void testUpdatePost() {
        int id = 1;
        Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/" + id, Movie.class);
        movie.setMovieRating(4.1);
        restTemplate.put(getRootUrl() + "/movies/" + id, movie);

        Movie updatedMovie = restTemplate.getForObject(getRootUrl() + "/movies/" + id, Movie.class);
        Assert.assertNotNull(updatedMovie);
    }

    @org.junit.Test
    public void testDeletePost() {
        int id = 2;
        Movie movie = restTemplate.getForObject(getRootUrl() + "/users/" + id, Movie.class);
        Assert.assertNotNull(movie);

        restTemplate.delete(getRootUrl() + "/movies/" + id);

        try {
            movie = restTemplate.getForObject(getRootUrl() + "/movies/" + id, Movie.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }


}

