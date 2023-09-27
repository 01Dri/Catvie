package me.dri.Catvie.entity.models;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ditributors")
public class Distributor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "distributor")
    private List<Film> filmList = new ArrayList<>();


    public Distributor() {

    }

    public Distributor(Long id, String name, List<Film> filmList) {
        this.id = id;
        this.name = name;
        this.filmList = filmList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distributor that = (Distributor) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(filmList, that.filmList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, filmList);
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", filmList=" + filmList +
                '}';
    }
}