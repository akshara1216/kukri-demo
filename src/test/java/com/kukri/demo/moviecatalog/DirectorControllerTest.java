package com.kukri.demo.moviecatalog;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kukri.demo.moviecatalog.controller.DirectorController;
import com.kukri.demo.moviecatalog.model.Director;
import com.kukri.demo.moviecatalog.service.DirectorService;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;



@RunWith(SpringRunner.class)
@WebMvcTest(value = DirectorController.class)
@EnableWebMvc
public class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    private String expectedJson = "[{\"id\":1,\"firstName\":\"First 1\",\"lastName\":\"Last 1\",\"filmIndustry\":null,\"country\":null},{\"id\":2,\"firstName\":\"First 1\",\"lastName\":\"Last 1\",\"filmIndustry\":null,\"country\":null}]";

    @Test
    public void retrivedetailsofDirectors() throws Exception {

        Mockito.when(directorService.getDirectors()).thenReturn(getSampleDirectors());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/directors").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //System.out.println(result.getResponse());

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getdirectorById() throws Exception {
        Director director = createDirector( 12L,"first","last");

        Mockito.when(directorService.getDirectorById(12L)).thenReturn(director);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/directors/12").accept(MediaType.APPLICATION_JSON);

      mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is(director.getFirstName())));

    }

    @Test
    public void createDirector() throws Exception {
        Director newDirector = createDirector(14L,"First 1","Last 1");
        newDirector.setCountry("India");
        newDirector.setFilmIndustry("Bollywood");
        Mockito.when(directorService.createDirector(newDirector)).thenReturn(newDirector);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/directors")
                                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(newDirector));
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void updateDirector() throws Exception {
        Director newDirector = createDirector(12L,"UpdatedFirst","Last 1");
        newDirector.setCountry("India");
        newDirector.setFilmIndustry("Bollywood");
        Mockito.when(directorService.updateDirector(newDirector,12L)).thenReturn(newDirector);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/directors/1")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(newDirector));
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void delete_movie_OK() throws Exception {
        Map response = new HashMap();
        response.put("deleted",true);
        Mockito.when(directorService.deleteDirector(1L)).thenReturn(response);
        mockMvc.perform(delete("/v1/directors/1"))
                /*.andDo(print())*/
                .andExpect(status().isOk());

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
