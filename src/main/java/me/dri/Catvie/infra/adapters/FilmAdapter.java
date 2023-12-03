package me.dri.Catvie.infra.adapters;

import me.dri.Catvie.controllers.FilmController;
import me.dri.Catvie.domain.exceptions.InvalidGenre;
import me.dri.Catvie.domain.exceptions.NotFoundDirector;
import me.dri.Catvie.domain.exceptions.NotFoundFilm;
import me.dri.Catvie.domain.models.entities.Film;
import me.dri.Catvie.domain.models.entities.Genre;
import me.dri.Catvie.domain.ports.repositories.FilmRepositoryPort;
import me.dri.Catvie.infra.entities.FilmEntity;
import me.dri.Catvie.infra.entities.GenreEntity;
import me.dri.Catvie.infra.jpa.DirectorRepositoryJPA;
import me.dri.Catvie.infra.jpa.FilmRepositoryJPA;
import me.dri.Catvie.infra.jpa.GenreRepositoryJPA;
import me.dri.Catvie.infra.jpa.NotesAudiencesRepositoryJPA;
import me.dri.Catvie.infra.ports.mappers.MapperFilmInfraPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmAdapter implements FilmRepositoryPort {

    private final FilmRepositoryJPA filmRepositoryJPA;

    private final MapperFilmInfraPort mapperFilm;

    private final GenreRepositoryJPA genreRepositoryPort;

    private final DirectorRepositoryJPA directorRepositoryJPA;

    private final NotesAudiencesRepositoryJPA audiencesRepositoryJPA;
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);


    @Autowired
    public FilmAdapter(FilmRepositoryJPA filmRepositoryJPA, MapperFilmInfraPort mapperFilm, GenreRepositoryJPA genreRepositoryPort, DirectorRepositoryJPA directorRepositoryJPA, NotesAudiencesRepositoryJPA audiencesRepositoryJPA) {
        this.filmRepositoryJPA = filmRepositoryJPA;
        this.mapperFilm = mapperFilm;
        this.genreRepositoryPort = genreRepositoryPort;
        this.directorRepositoryJPA = directorRepositoryJPA;
        this.audiencesRepositoryJPA = audiencesRepositoryJPA;
    }

    @Override
    public Film findById(Long id) {
        FilmEntity film = this.filmRepositoryJPA.findFilmById(id).orElseThrow(() -> new NotFoundFilm("Film with this id: " + id + " does not exist!"));
        return this.mapperFilm.convertyFilmEntityToFilm(film);
    }

    @Override
    public List<Film> findAllFilmEntity() {
        logger.info("Endpoints films accessed");
        List<FilmEntity> films = this.filmRepositoryJPA.findAllFilms().orElseThrow(() -> new NotFoundFilm("Empty"));
        return this.mapperFilm.convertyListFilmsEntityToListFilm(films);
    }

    @Override
    public Film findByTitle(String title) {
        FilmEntity film = this.filmRepositoryJPA.findFilmByTitle(title).orElseThrow(() -> new NotFoundFilm("Film with this title: " + title + " Does not exist!"));
        return this.mapperFilm.convertyFilmEntityToFilm(film);
    }


    @Override
    public void create(Film film) {
        var genresEntity = this.getGenresToSetGenreEntity(film.getGenres());
        var directorEntity = this.directorRepositoryJPA.findByName(film.getDirector().getName()).orElseThrow(() -> new NotFoundDirector("Director not found"));
        FilmEntity filmEntity = this.mapperFilm.convertyFilmToFilmEntity(film);
        filmEntity.setGenres(genresEntity);
        filmEntity.setDirector(directorEntity);
        this.filmRepositoryJPA.save(filmEntity);
    }


    @Override
    public void delete(Film film) {
        FilmEntity filmEntity = this.mapperFilm.convertyFilmToFilmEntity(film);
        this.filmRepositoryJPA.delete(filmEntity);
    }

    @Override
    public Film update(Film film) {
        FilmEntity filmByService = this.mapperFilm.convertyFilmToFilmEntity(film);
        this.filmRepositoryJPA.save(filmByService);
        return film;
    }

    @Override
    public Long deleteById(Long id) {
        FilmEntity filmEntity = this.filmRepositoryJPA.findFilmById(id).orElseThrow(
                () -> new NotFoundFilm("The film was not found")); // Why this doesn't work
        this.audiencesRepositoryJPA.deleteByFilmId(filmEntity.getId());
        this.filmRepositoryJPA.delete(filmEntity);
        return id;
    }

    public Set<GenreEntity> getGenresToSetGenreEntity(Set<Genre> genres) {
        return genres.stream().map(g -> this.genreRepositoryPort.findBygenreName(g.getGenreName()).orElseThrow(() -> new InvalidGenre("The genre was not found")))
                .collect(Collectors.toSet());
    }
}


