package fr.diginamic.spring.dao;

import fr.diginamic.spring.models.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartementDao {

    @PersistenceContext
    private EntityManager entityManager;

    // Méthode pour récupérer un département par son ID
    public Departement getDepartementById(int id) {
        return entityManager.find(Departement.class, id);
    }

    // Méthode pour récupérer tous les départements
    public List<Departement> getAllDepartements() {
        return entityManager.createQuery("FROM Departement", Departement.class).getResultList();
    }

    // Méthode pour créer un département
    public void createDepartement(Departement departement) {
        entityManager.persist(departement);
    }

    // Méthode pour mettre à jour un département
    public Departement updateDepartement(Departement departement) {
        return entityManager.merge(departement);
    }

    // Méthode pour supprimer un département
    public void deleteDepartement(int id) {
        Departement departement = getDepartementById(id);
        if (departement != null) {
            entityManager.remove(departement);
        }
    }
}
