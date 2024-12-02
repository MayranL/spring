package fr.diginamic.spring.repository;

import fr.diginamic.spring.models.Ville;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {

    Ville findByNom(String nom);

    List<Ville> findByNomStartingWith(String prefix);

    List<Ville> findByDepartementCode(String code);

    List<Ville> findByNbHabitantsGreaterThan(int min);

    List<Ville> findByNbHabitantsBetween(int min, int max);

    List<Ville> findByDepartementIdAndNbHabitantsGreaterThan(int departementId, int min);

    List<Ville> findByDepartementIdAndNbHabitantsBetween(int departementId, int min, int max);

    List<Ville> findTopNByDepartementIdOrderByNbHabitantsDesc(int departementId, Pageable pageable);
}