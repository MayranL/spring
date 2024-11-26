package fr.diginamic.spring.dao;

import fr.diginamic.spring.models.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleDao {

    @PersistenceContext
    private EntityManager entityManager;

    // Extraire toutes les villes
    public List<Ville> findAll() {
        return entityManager.createQuery("SELECT v FROM Ville v", Ville.class).getResultList();
    }

    // Extraire une ville par son ID
    public Ville findById(int id) {
        return entityManager.find(Ville.class, id);
    }

    // Extraire une ville par son nom
    public Ville findByNom(String nom) {
        try {
            return entityManager.createQuery("SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class)
                    .setParameter("nom", nom)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Si la ville n'est pas trouvée, retourne null
        }
    }

    // Insérer une nouvelle ville
    @Transactional
    public void save(Ville ville) {
        entityManager.persist(ville);
    }

    // Modifier une ville existante
    @Transactional
    public Ville update(int id, Ville villeModifiee) {
        Ville villeExistante = entityManager.find(Ville.class, id);
        if (villeExistante != null) {
            villeExistante.setNom(villeModifiee.getNom());
            villeExistante.setNbHabitants(villeModifiee.getNbHabitants());
            return entityManager.merge(villeExistante);
        }
        return null; // Si la ville n'existe pas, retourne null
    }

    // Supprimer une ville
    @Transactional
    public void delete(int id) {
        Ville ville = entityManager.find(Ville.class, id);
        if (ville != null) {
            entityManager.remove(ville);
        }
    }

    // Méthode pour récupérer les villes par département
    public List<Ville> getVillesByDepartement(int departementId) {
        return entityManager.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :departementId", Ville.class)
                .setParameter("departementId", departementId)
                .getResultList();
    }
}
