package me.dri.Catvie.infra.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import me.dri.Catvie.domain.exceptions.*;
import me.dri.Catvie.domain.exceptions.auth.InvalidInformationLogin;
import me.dri.Catvie.domain.exceptions.auth.InvalidJWTException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ContentInformationsFilmMissing.class)
    public ResponseEntity<ExceptionEntity> contentIsMissingException(ContentInformationsFilmMissing e, HttpServletRequest request) {
        String error = "Content Is Missing";
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = request.getRequestURI();
        ExceptionEntity err = new ExceptionEntity(new Date(), error, e.getMessage(), status.value(), path);
        return ResponseEntity.status(status).body(err);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundFilm.class)
    public ResponseEntity<ExceptionEntity> contentIsMissingException(NotFoundFilm e, HttpServletRequest request) {
        String error = "Not found entity";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = request.getRequestURI();
        ExceptionEntity err = new ExceptionEntity(new Date(), error, e.getMessage(), status.value(), path);
        return ResponseEntity.status(status).body(err);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidGenre.class)
    public ResponseEntity<ExceptionEntity> invalidGenre(InvalidGenre e, HttpServletRequest request) {
        String error = "Genre not found";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = request.getRequestURI();
        ExceptionEntity err = new ExceptionEntity(new Date(), error, e.getMessage(), status.value(), path);
        return ResponseEntity.status(status).body(err);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundDirector.class)
    public ResponseEntity<ExceptionEntity> notFoundDirector(NotFoundDirector e, HttpServletRequest request) {
        String error = "Director not found";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = request.getRequestURI();
        ExceptionEntity err = new ExceptionEntity(new Date(), error, e.getMessage(), status.value(), path);
        return ResponseEntity.status(status).body(err);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidInformationLogin.class)
    public ResponseEntity<ExceptionEntity> invalidInformation(InvalidInformationLogin e, HttpServletRequest request) {
        String error = "User invalid!!!";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String path = request.getRequestURI();
        ExceptionEntity err = new ExceptionEntity(new Date(), error, e.getMessage(), status.value(), path);
        return ResponseEntity.status(status).body(err);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidJWTException.class)
    public ResponseEntity<ExceptionEntity> InvalidJWTException(InvalidJWTException e, HttpServletRequest request) {
        String error = "Token invalid!!!!";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String path = request.getRequestURI();
        ExceptionEntity err = new ExceptionEntity(new Date(), error, e.getMessage(), status.value(), path);
        return ResponseEntity.status(status).body(err);
    }

}
