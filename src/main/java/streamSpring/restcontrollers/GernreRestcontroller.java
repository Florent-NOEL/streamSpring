package streamSpring.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import streamSpring.entities.GenreEntitie;
import streamSpring.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/genre")
public class GernreRestcontroller {
    @Autowired
    GenreService genreService;
    @GetMapping("/all")
    public List<String> getGenres(){
        return genreService.findAll().stream().map(GenreEntitie::getName).collect(Collectors.toList());
    }

}
