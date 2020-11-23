package com.kukri.demo.moviecatalog.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "director")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
     private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "film_industry")
    private String filmIndustry;

    @Column(name = "country", nullable = false)
    private String country;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFilmIndustry() {
        return filmIndustry;
    }

    public void setFilmIndustry(String filmIndustry) {
        this.filmIndustry = filmIndustry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("filmIndustry", filmIndustry)
                .append("country", country)
                .toString();
    }
}
