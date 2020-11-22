
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieCatalogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        Movie movie = restTemplate.getForObject(getRootUrl() + "/movie/1", Movie.class);
        Assert.assertNotNull(movie);
    }

    @Test
    public void testCreateMovie() {
        Movie movie = new Movie();
        movie.setMovieRating(6.3);
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
        Movie movie = restTemplate.getForObject(getRootUrl() + "/movies/" + id, Movie.class);
        Assert.assertNotNull(movie);

        restTemplate.delete(getRootUrl() + "/movies/" + id);

        try {
            movie = restTemplate.getForObject(getRootUrl() + "/movies/" + id, Movie.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
    @Test
    public void testEmptyresultSearchbyDirector() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(getRootUrl()).path("/movies")
                .queryParam("directorfirstname", "Shruthi")
                .queryParam("directorlastname","gowda")
                .build().toUri();
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.GET, entity, String.class);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testEmptyresultSearchbyRating() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(getRootUrl()).path("/movies")
                .queryParam("rating", "10.5")
                .build().toUri();
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.GET, entity, String.class);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testSearchbyRating() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(getRootUrl()).path("/movies")
                .queryParam("rating", "5.5")
                .build().toUri();
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.GET, entity, String.class);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testSearchbyDirector() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(getRootUrl()).path("/movies")
                .queryParam("directorfirstname", "david")
                .queryParam("directorlastname","lynch")
                .build().toUri();
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.GET, entity, String.class);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }



}

