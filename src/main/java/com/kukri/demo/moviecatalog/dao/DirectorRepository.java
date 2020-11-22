package com.kukri.demo.moviecatalog.dao;

import com.kukri.demo.moviecatalog.exception.ResourceNotFoundException;
import com.kukri.demo.moviecatalog.model.Director;
import com.kukri.demo.moviecatalog.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

/**
 * @Author Shruthi Gowda
 * implements the DB repository functionality for the model Director.
 */
@Repository
public interface DirectorRepository  extends JpaRepository<Director,Long> {
    @Query( value="SELECT id from director d where d.first_name = ?1 && last_name = ?2",nativeQuery = true)
    Long findDirectorByFirstNameAndLastName(Optional<String> firstName, Optional<String> lastName)throws ResourceNotFoundException;

}
