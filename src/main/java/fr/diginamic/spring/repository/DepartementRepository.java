package fr.diginamic.spring.repository;

import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    Optional<Departement> findByNom(String nom);

    Optional<Departement> findByCode(String code);

    @Query("SELECT d FROM Departement d")
    List<Departement> findAllDepartements();

    @Query("SELECT v FROM Ville v WHERE v.departement.id = :departementId ORDER BY v.nbHabitants DESC")
    List<Ville> findTopNVillesByDepartement(@Param("departementId") int departementId, Pageable pageable);

    @Query("SELECT v FROM Ville v WHERE v.departement.id = :departementId AND v.nbHabitants BETWEEN :min AND :max")
    List<Ville> findVillesByPopulationRangeAndDepartement(@Param("departementId") int departementId, @Param("min") int min,@Param("max") int max);
}