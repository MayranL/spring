package fr.diginamic.spring.repository;

import fr.diginamic.spring.dto.VilleDto;
import fr.diginamic.spring.models.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VilleRepository extends JpaRepository<Ville, Integer> {

    // Recherche d'une ville par son nom
    Ville findByNom(String nom);

    List<Ville> findVillesByDepartement_Code(String departementCode);

    // Recherche des villes dont le nom commence par une chaîne donnée
    List<Ville> findByNomStartingWith(String prefix);

    // Recherche des villes ayant une population supérieure à un minimum
    List<Ville> findAllByNbHabitantsGreaterThan(int min);

    // Recherche des villes dont la population est comprise entre deux valeurs
    List<Ville> findAllByNbHabitantsBetween(int min, int max);

    // Recherche des n villes les plus peuplées d'un département donné
    @Query("SELECT v FROM Ville v WHERE v.departement.code = :codeDepartement ORDER BY v.nbHabitants DESC")
    Page<Ville> findTopNVillesByDepartementCodeOrderByNbHabitantsDesc(
            @Param("codeDepartement") String codeDepartement,
            Pageable pageable);
}
