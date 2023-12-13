package me.dri.Catvie.domain.ports.repositories;

import me.dri.Catvie.domain.models.core.Director;

import java.util.List;

public interface DirectorRepositoryPort {

    Director findById(Long id);

    List<Director> findAll();

    Director findByName(String name);

    void create(Director FilmRequestDTO);

    void save(Director film);

    void delete(Director film);

    Director update(Director film);


}
