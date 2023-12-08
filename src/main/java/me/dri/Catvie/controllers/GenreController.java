package me.dri.Catvie.controllers;


import me.dri.Catvie.domain.models.dto.genre.GenreResponseDTO;
import me.dri.Catvie.domain.ports.interfaces.genre.GenreServicesPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/genre/v1")
@CrossOrigin
public class GenreController {

    private final GenreServicesPort servicesPort;

    public GenreController(GenreServicesPort servicesPort) {
        this.servicesPort = servicesPort;
    }

    @GetMapping(value = "/findGenreByName/{name}")
    ResponseEntity<GenreResponseDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok().body(this.servicesPort.findByName(name));
    }
    @GetMapping(value = "/findAll")
    ResponseEntity<Set<GenreResponseDTO>> findByName() {
        return ResponseEntity.ok().body(this.servicesPort.findAll());
    }
}
