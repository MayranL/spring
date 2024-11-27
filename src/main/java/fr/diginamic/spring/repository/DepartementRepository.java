package fr.diginamic.spring.repository;

import fr.diginamic.spring.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    // Recherche d'un département par son code
    Optional<Departement> findByCode(String code);

    // Recherche d'un département par son nom
    Optional<Departement> findByName(String name);

    // Recherche de tous les départements (par défaut fourni par JpaRepository)
    List<Departement> findAll();

    // Recherche de départements ayant un nom similaire
    List<Departement> findByNameContaining(String namePart);

    // Recherche des départements ayant un nombre de villes supérieur à un certain nombre (par exemple 10 villes)
    @Query("SELECT d FROM Departement d WHERE SIZE(d.villes) > :minVilles")
    List<Departement> findDepartementsWithMoreThanNVilles(@Param("minVilles") int minVilles);

    // Recherche d'un département par son code, avec ses villes associées (pour charger la relation)
    @Query("SELECT d FROM Departement d LEFT JOIN FETCH d.villes WHERE d.code = :code")
    Optional<Departement> findByCodeWithVilles(@Param("code") String code);

    // Recherche d'un département par son nom, avec ses villes associées
    @Query("SELECT d FROM Departement d LEFT JOIN FETCH d.villes WHERE d.name = :name")
    Optional<Departement> findByNameWithVilles(@Param("name") String name);

    // Recherche d'un département par son code, trié par le nom des villes (ordre croissant ou décroissant)
    @Query("SELECT d FROM Departement d LEFT JOIN d.villes v WHERE d.code = :code ORDER BY v.nom ASC")
    List<Departement> findDepartementWithSortedCitiesByName(@Param("code") String code);
}
