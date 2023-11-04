package streamSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streamSpring.entities.VideoEntitie;


import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntitie,String> {
    // List<VideoEntitie> findByNomContaining(String nom);
}
