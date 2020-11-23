package com.kukri.demo.moviecatalog;

import com.kukri.demo.moviecatalog.dao.DirectorRepository;
import com.kukri.demo.moviecatalog.dao.MoviesRepository;
import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Director;
import com.kukri.demo.moviecatalog.model.Movie;
import com.kukri.demo.moviecatalog.service.DirectorService;
import com.kukri.demo.moviecatalog.service.impl.DirectorServiceImpl;
import com.kukri.demo.moviecatalog.service.impl.MovieServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @InjectMocks
    private DirectorService directorService = new DirectorServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDirectors() {
        // given
        List<Director> directors = getSampleDirectors();
        when(directorRepository.findAll()).thenReturn(directors);

        // when
        List<Director> actualDirectors = directorService.getDirectors();

        // then
        assertEquals(directors.size(), actualDirectors.size());
    }

    @Test
    public void testCreateDirector() {
        // given
        Director director = createDirector(1L, "First 1", "Last 1");
        when(directorRepository.save(Mockito.any())).thenReturn(director);

        // when
        Director actualDirector = directorService.createDirector(director);

        // then
        assertNotNull(actualDirector);
    }

    @Test
    public void getDirectorByIdSuccess() throws ResourceNotFoundException {
        // given
        Long id = 1L;
        Director director = createDirector(1L, "First 1", "Last 1");
        when(directorRepository.findById(id)).thenReturn(Optional.of(director));

        // when
        Director actualDirector = directorService.getDirectorById(id);

        // then
        assertNotNull(actualDirector);
    }

    @Test (expected = ResourceNotFoundException.class)
    public void getDirectorByIdFailure() throws ResourceNotFoundException {
        // given
        Long id = 1L;

        // when
        directorService.getDirectorById(id);
    }

    @Test (expected = ResourceNotFoundException.class)
    public void deleteDirectorByIdFailure() throws ResourceNotFoundException {
        // given
        Long id = 1L;

        // when
        directorService.deleteDirector(id);
    }

    @Test
    public void deleteDirectorByIdSuccess() throws ResourceNotFoundException {
        // given
        Long id = 1L;
        Director director = createDirector(1L, "First 1", "Last 1");
        doNothing().when(directorRepository).delete(director);
        when(directorRepository.findById(id)).thenReturn(Optional.of(director));

        // when
        Map<String, Boolean> output = directorService.deleteDirector(id);

        // then
        assertTrue(output.get("deleted"));
    }


    private List<Director> getSampleDirectors() {
        List<Director> directors = new ArrayList<>();
        directors.add(createDirector(1L, "First 1", "Last 1"));
        directors.add(createDirector(2L, "First 1", "Last 1"));
        return directors;
    }

    private Director createDirector(Long id, String firstName, String lastName) {
        Director director = new Director();
        director.setId(id);
        director.setFirstName(firstName);
        director.setLastName(lastName);
        return director;
    }
}
