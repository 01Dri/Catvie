package me.dri.Catvie.domain.utils;

import me.dri.Catvie.domain.models.core.Director;
import me.dri.Catvie.domain.models.core.Film;
import me.dri.Catvie.domain.models.core.Genre;
import me.dri.Catvie.domain.models.core.User;
import me.dri.Catvie.infra.entities.DirectorEntity;
import me.dri.Catvie.infra.entities.FilmEntity;
import me.dri.Catvie.infra.entities.GenreEntity;
import me.dri.Catvie.infra.entities.UserEntity;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FilmBuilder<T> implements BuilderFilm<T> {
    private Long id;
    private String title;
    private Set<Object> genres = new HashSet<>();
    private String originalLanguage;
    private Date releaseDate;
    private Integer runtime;
    private String distributor;
    private String writer;
    private String productionCo;
    private Double averageRatingCritic;
    private Double averageRatingAudience;
    private T director;
    private String posterUrl;
    private T postedByUser;
    private Links links;

    private Boolean isEntity;

    @Override
    public BuilderFilm isEntity(Boolean conditio) {
        this.isEntity = conditio;
        return this;
    }

    @Override
    public BuilderFilm withId(Long id) {
        this.id = id;
        return this;
    }


    @Override
    public BuilderFilm withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public BuilderFilm<T> withGenre(Set<T> genres) {
        this.genres = (Set<Object>) genres;
        return this;
    }


    @Override
    public BuilderFilm withOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    @Override
    public BuilderFilm withReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @Override
    public BuilderFilm withRuntime(Integer runtime) {
        this.runtime = runtime;
        return this;
    }

    @Override
    public BuilderFilm withDistributor(String distributor) {
        this.distributor = distributor;
        return this;
    }

    @Override
    public BuilderFilm withWriter(String writer) {
        this.writer = writer;
        return this;
    }

    @Override
    public BuilderFilm withProductionCo(String productionCo) {
        this.productionCo = productionCo;
        return this;
    }

    @Override
    public BuilderFilm withAverageRatingCritic(Double averageRatingCritic) {
        this.averageRatingCritic = averageRatingCritic;
        return this;
    }

    @Override
    public BuilderFilm withAverageRatingAudience(Double averageRatingAudience) {
        this.averageRatingAudience = averageRatingAudience;
        return this;
    }

    @Override
    public BuilderFilm withDirector( T director) {
        if (this.isEntity) {
            this.director = (T) new DirectorEntity(((Director)director).getId(), ((Director)director).getName());
        }
        this.director = (T) new Director(((DirectorEntity)director).getId(), ((DirectorEntity)director).getName());
        return this;
    }
    @Override
    public BuilderFilm withPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
        return this;
    }

    @Override
    public BuilderFilm withUser(T user) {
        if (this.isEntity) {
            this.postedByUser = (T) new UserEntity((()));
        }
        this.postedByUser = (T) new User();
        return this;
    }

    @Override
    public BuilderFilm withLinks(Links links) {
        this.links = links;
        return this;
    }

    @Override
    public Object build() {
        if (this.isEntity) {
            Set<GenreEntity> genreEntitySet = genres.stream()
                    .map(genre -> new GenreEntity(((Genre) genre).getId(), ((Genre) genre).getGenreName()))
                    .collect(Collectors.toSet());
            FilmEntity film = new FilmEntity(id, title, genreEntitySet, originalLanguage, new DirectorEntity(((Director)this.director).getId(), (((Director)this.director).getName())), writer, releaseDate, runtime, distributor, productionCo, averageRatingCritic, averageRatingAudience, posterUrl, (UserEntity) postedByUser);
            this.setFilmEntityEachLinks(this.links, film); // Setting links hateoas  on each film
            return film;
        } else {
            Set<Genre> genreSet = genres.stream()
                    .map(genre -> new Genre(((GenreEntity) genre).getId(), ((GenreEntity) genre).getGenreName()))
                    .collect(Collectors.toSet());
            Film film = new Film(id, title, genreSet, originalLanguage, (Director) director, writer, releaseDate, runtime, distributor, productionCo, averageRatingCritic, averageRatingAudience, posterUrl, (User) postedByUser);
            this.setFilmEachLinks(this.links, film); // Setting links hateoas  on each film
            return film;
        }
    }
    private void setFilmEachLinks(Links links, Film film) {
        for (Link l : links) {
            film.add(l);
        }
    }
    private void setFilmEntityEachLinks(Links links, FilmEntity film) {
        for (Link l : links) {
            film.add(l);
        }
    }
    private Set<Genre> getGenres() {
        Set<Genre> genreSet = genres.stream()
                .map(genre -> new Genre(((GenreEntity) genre).getId(), ((GenreEntity) genre).getGenreName()))
                .collect(Collectors.toSet());
        return genreSet;
    }

    private Set<GenreEntity> getGenreEntities() {
        Set<GenreEntity> genreEntitySet = genres.stream()
                .map(genre -> new GenreEntity(((Genre) genre).getId(), ((Genre) genre).getGenreName()))
                .collect(Collectors.toSet());
        return genreEntitySet;
    }




}