package com.kukri.demo.moviecatalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kukri.demo.moviecatalog.controller.MovieController;
import com.kukri.demo.moviecatalog.model.Director;
import com.kukri.demo.moviecatalog.model.Movie;
import com.kukri.demo.moviecatalog.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    private String expectedJson = "[{\"id\":1,\"movieTitle\":\"Movie 1\",\"directorID\":0,\"yearReleased\":null,\"flimGenre\":null,\"boardRating\":null,\"movieRating\":0.0},{\"id\":2,\"movieTitle\":\"Movie 2\",\"directorID\":0,\"yearReleased\":null,\"flimGenre\":null,\"boardRating\":null,\"movieRating\":0.0}]";

    @Test
    public void retrieveDetailsForMovies() throws Exception {

        Mockito.when(movieService.getMovies()).thenReturn(getSampleMovies());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/movies").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getMovieById() throws Exception {
        Movie movie = createMovie( 12L,"America America");

        Mockito.when(movieService.getMovieById(12L)).thenReturn(movie);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/movies/12").accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("movieTitle", is(movie.getMovieTitle())));

    }

    @Test
    public void createMovie() throws Exception {
        Movie newMovie = createMovie(14L,"Kirik Party");

        Mockito.when(movieService.createMovie(newMovie)).thenReturn(newMovie);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/movies")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(newMovie));
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void updateMovie() throws Exception {
       Movie movie =  createMovie(12L,"Jhony English");
        movie.setBoardRating("U");
        movie.setYearReleased("2000");
        movie.setMovieRating(7.5);
        Mockito.when(movieService.updateMovie(movie,12L)).thenReturn(movie);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/movies/12")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(movie));
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void delete_movie_OK() throws Exception {
        Map response = new HashMap();
        response.put("deleted",true);
        Mockito.when(movieService.deleteMovie(1L)).thenReturn(response);
        mockMvc.perform(delete("/v1/movies/1"))
                .andExpect(status().isOk());

    }

    @Test
    public void searchByDirector() throws Exception {
        Mockito.when(movieService.findMoviesByDirector(Optional.of("firstname"),Optional.of("lastname"))).thenReturn(getSampleMovies());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/movies").accept(MediaType.APPLICATION_JSON)
                .param("directorfirstname", "firstname")
                .param("directorlastname", "lastname");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), false);

    }

    @Test
    public void searchByRating() throws Exception {
        Mockito.when(movieService.findMoviesByRating(java.util.Optional.of(5.5))).thenReturn(getSampleMovies());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/movies").accept(MediaType.APPLICATION_JSON)
                .param("rating", "5.5");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), false);

    }

    private List<Movie> getSampleMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(createMovie(1L, "Movie 1"));
        movies.add(createMovie(2L, "Movie 2"));
        return movies;
    }

    private Movie createMovie(Long id, String title) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setMovieTitle(title);
        return movie;
    }
}
