package com.kukri.demo.moviecatalog;

import com.kukri.demo.moviecatalog.dao.DirectorRepository;
import com.kukri.demo.moviecatalog.dao.MoviesRepository;
import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Director;
import com.kukri.demo.moviecatalog.model.Movie;
import com.kukri.demo.moviecatalog.service.MovieService;
import com.kukri.demo.moviecatalog.service.impl.MovieServiceImpl;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private MoviesRepository moviesRepository;

    @InjectMocks
    private MovieService movieService = new MovieServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        // given
        List<Movie> mockMovies = getSampleMovies();
        when(moviesRepository.findAll()).thenReturn(mockMovies);

        // when
        List<Movie> actualMovies = movieService.getMovies();

        // then
        assertEquals(mockMovies.size(), actualMovies.size());
    }

    @Test
    public void testCreateMovie() {
        // given
        Movie movie = createMovie(1L, "Movie 1");
        when(moviesRepository.save(Mockito.any())).thenReturn(movie);

        // when
        Movie actualMovie = movieService.createMovie(movie);

        // then
        assertNotNull(actualMovie);
    }

    @Test
    public void getMovieByIdSuccess() throws ResourceNotFoundException {
        // given
        Long id = 1L;
        Movie movie = createMovie(id, "Movie 1");
        when(moviesRepository.findById(id)).thenReturn(Optional.of(movie));

        // when
        Movie actualMovie = movieService.getMovieById(id);

        // then
        assertNotNull(actualMovie);
    }

    @Test (expected = ResourceNotFoundException.class)
    public void getMovieByIdFailure() throws ResourceNotFoundException {
        // given
        Long id = 1L;

        // when
        movieService.getMovieById(id);
    }

    @Test (expected = ResourceNotFoundException.class)
    public void deleteMovieByIdFailure() throws ResourceNotFoundException {
        // given
        Long id = 1L;

        // when
        movieService.deleteMovie(id);
    }

    @Test
    public void deleteMovieByIdSuccess() throws ResourceNotFoundException {
        // given
        Long id = 1L;
        Movie movie = createMovie(id, "Movie 1");
        doNothing().when(moviesRepository).delete(movie);
        when(moviesRepository.findById(id)).thenReturn(Optional.of(movie));

        // when
        Map<String, Boolean> output = movieService.deleteMovie(id);

        // then
        assertTrue(output.get("deleted"));
    }

    @Test (expected = ResourceNotFoundException.class)
    public void findMoviesByInvalidDirectorName() throws ResourceNotFoundException {
        // given
        Long id = 1L;
        String firstName = "First Name";
        String lastName = "Last Name";

        when(directorRepository.findDirectorByFirstNameAndLastName(Optional.of(firstName), Optional.of(lastName))).thenThrow(NullPointerException.class);

        // when
        movieService.findMoviesByDirector(Optional.of(firstName), Optional.of(lastName));
    }

    @Test (expected = ResourceNotFoundException.class)
    public void findMoviesByValidDirectorNameWithoutAnyMovies() throws ResourceNotFoundException {
        // given
        Long id = 1L;
        String firstName = "First Name";
        String lastName = "Last Name";

        when(directorRepository.findDirectorByFirstNameAndLastName(Optional.of(firstName), Optional.of(lastName))).thenReturn(id);
        when(moviesRepository.findMoviesByDirectorName(id)).thenReturn(Collections.emptyList());

        // when
        movieService.findMoviesByDirector(Optional.of(firstName), Optional.of(lastName));
    }

    @Test
    public void findMoviesByValidDirectorNameWithMovies() throws ResourceNotFoundException {
        // given
        Long id = 1L;
        String firstName = "First Name";
        String lastName = "Last Name";

        when(directorRepository.findDirectorByFirstNameAndLastName(Optional.of(firstName), Optional.of(lastName))).thenReturn(id);
        when(moviesRepository.findMoviesByDirectorName(id)).thenReturn(getSampleMovies());

        // when
        List<Movie> actualMovies = movieService.findMoviesByDirector(Optional.of(firstName), Optional.of(lastName));

        // then
        assertEquals(getSampleMovies().size(), actualMovies.size());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void findMoviesByRatingFailure() throws ResourceNotFoundException {
        // given
        Double rating = 1.0;
        when(moviesRepository.findMoviesByMovieRating(Optional.of(rating))).thenReturn(Collections.emptyList());

        // when
        movieService.findMoviesByRating(Optional.of(rating));
    }

    @Test
    public void findMoviesByRatingSuccess() throws ResourceNotFoundException {
        // given
        Double rating = 1.0;
        when(moviesRepository.findMoviesByMovieRating(Optional.of(rating))).thenReturn(getSampleMovies());

        // when
        List<Movie> actualMovies = movieService.findMoviesByRating(Optional.of(rating));

        // then
        assertEquals(getSampleMovies().size(), actualMovies.size());
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

    private Director createDirector(Long id, String firstName, String lastName) {
        Director director = new Director();
        director.setId(id);
        director.setFirstName(firstName);
        director.setLastName(lastName);
        return director;
    }
}
