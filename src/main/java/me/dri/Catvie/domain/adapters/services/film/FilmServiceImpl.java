package me.dri.Catvie.domain.adapters.services.film;

import me.dri.Catvie.domain.exceptions.NotFoundFilm;
import me.dri.Catvie.domain.exceptions.film.*;
import me.dri.Catvie.domain.models.dto.film.FilmDTO;
import me.dri.Catvie.domain.models.dto.film.FilmResponseDTO;
import me.dri.Catvie.domain.models.entities.Film;
import me.dri.Catvie.domain.ports.interfaces.director.DirectorServicePort;
import me.dri.Catvie.domain.ports.interfaces.film.FilmServicePort;
import me.dri.Catvie.domain.ports.interfaces.film.MapperEntitiesPort;
import me.dri.Catvie.domain.ports.interfaces.genre.GenreServicesPort;
import me.dri.Catvie.domain.ports.repositories.FilmRepositoryPort;

import java.util.List;

public class FilmServiceImpl  implements FilmServicePort {

    private final FilmRepositoryPort filmRepositoryPort;

    private final MapperEntitiesPort mapperEntitiesPort;

    private final GenreServicesPort genreServicesPort;

    private final DirectorServicePort directorServicePort;


    public FilmServiceImpl(FilmRepositoryPort filmRepositoryPort, MapperEntitiesPort mapperEntitiesPort, GenreServicesPort genreServicesPort, DirectorServicePort directorServicePort) {
        this.filmRepositoryPort = filmRepositoryPort;
        this.mapperEntitiesPort = mapperEntitiesPort;
        this.genreServicesPort = genreServicesPort;
        this.directorServicePort = directorServicePort;
    }

    @Override
    public FilmDTO findById(Long id) {
        Film film = this.filmRepositoryPort.findById(id);
        if (film == null ) {
            throw new NotFoundFilm("Film not exists");
        }
        return this.mapperEntitiesPort.convertFilmToDto(film);
    }

    @Override
    public List<FilmDTO> findAll() {
        List<Film> films = this.filmRepositoryPort.findAllFilmEntity();
        return this.mapperEntitiesPort.convertListFilmToListDto(films);
    }

    @Override
    public FilmDTO findByTitle(String title) {
        Film film = this.filmRepositoryPort.findByTitle(title);
        if (film == null) {
            throw new NotFoundFilm("Film with name " + title + " Not exists!");
        }
        return this.mapperEntitiesPort.convertFilmToDto(film);

    }

    @Override
    public FilmResponseDTO create(FilmDTO filmDto) {
        this.filmIsValid(filmDto);
        var genres = this.genreServicesPort.verifyExistingGenres(filmDto.genres());
        var director = this.directorServicePort.findByName(filmDto.director().name());
        Film film = this.mapperEntitiesPort.convertFilmDtoToFilm(filmDto, genres, director);
        this.filmRepositoryPort.create(film);
        return new FilmResponseDTO(filmDto.title(), filmDto.genres(), filmDto.original_language(),
                filmDto.release_date(), filmDto.runtime(), filmDto.distributor(), filmDto.production_co(),
                filmDto.average_rating_critic(), filmDto.average_rating_audience(), filmDto.url());
    }

    @Override
    public void save(FilmDTO film) {
        this.filmIsValid(film);
        var genres = this.genreServicesPort.verifyExistingGenres(film.genres());
        var director = this.directorServicePort.findByName(film.title());
        Film filmToSave = this.mapperEntitiesPort.convertFilmDtoToFilm(film, genres, director);
        this.filmRepositoryPort.save(filmToSave);
    }

    @Override
    public void deleteById(Long id) {
        Film film = this.filmRepositoryPort.findById(id);
        if (film == null) {
            throw new NotFoundFilm("Entity not found");
        }
        this.filmRepositoryPort.delete(film);
    }


    public void filmIsValid(FilmDTO filmDto) {
        try {
            if (filmDto.title().isEmpty() || filmDto.title().isBlank()) {
                throw new InvalidTitleFilmException("Content 'title' is empty");
            }
        } catch (NullPointerException e) {
            throw new InvalidTitleFilmException("Content 'title' is null");
        }

        try {
            if(filmDto.original_language().isEmpty() || filmDto.original_language().isBlank()) {
                throw new InvalidLanguageFilmException("Content 'original_language' is empty");
            }
        } catch (NullPointerException e) {
            throw new InvalidLanguageFilmException("Content 'original_language' is null");
        }
        try {
            if(filmDto.writer().isEmpty() || filmDto.writer().isBlank()) {
                throw new InvalidWriterFilmException("Content 'writer' is empty");
            }
        } catch (NullPointerException e) {
            throw new InvalidWriterFilmException("Content 'writer' is null");
        }

        try {
            if(filmDto.distributor().isEmpty() || filmDto.distributor().isBlank()) {
                throw new InvalidDistributorFilmException("Content 'distributor' is empty");
            }
        } catch (NullPointerException e) {
            throw new InvalidDistributorFilmException("Content 'distributor' is null");
        }

        try {
            if(filmDto.production_co().isEmpty() || filmDto.production_co().isBlank()) {
                throw new InvalidProdutionFilmException("Content 'prodution' is empty");
            }
        } catch (NullPointerException e) {
            throw new InvalidProdutionFilmException("Content 'prodution' is null");
        }

        try {
            if(filmDto.url().isEmpty() || filmDto.url().isBlank()) {
                throw new InvalidUrlImageFilmException("Content 'url' is empty");
            }
        } catch (NullPointerException e) {
            throw new InvalidUrlImageFilmException("Content 'url' is null");
        }

        if (filmDto.runtime() == null) {
            throw new InvalidRuntimeFilmException("Content 'runtime' is null");
        }

        if (filmDto.release_date() == null) {
            throw new InvalidReleaseDateFilmException("Content 'release_date' is null");
        }

        if (filmDto.average_rating_critic() == null) {
            throw new InvalidAverageCriticFilmException("Content 'average_rating_critic' is null");
        }
        if (filmDto.average_rating_audience() == null) {
            throw new InvalidAverageAudienceFilmException("Content 'average_rating_audience' is null");
        }
    }

}
