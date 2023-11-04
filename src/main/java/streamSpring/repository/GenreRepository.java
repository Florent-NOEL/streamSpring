package streamSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streamSpring.entities.GenreEntitie;

public interface GenreRepository extends JpaRepository<GenreEntitie, String> {
}
