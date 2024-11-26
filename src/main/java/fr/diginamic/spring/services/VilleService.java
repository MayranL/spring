package fr.diginamic.spring.services;

import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.dao.VilleDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VilleService {

    @Autowired
    private VilleDao villeDao;

    // Extraire toutes les villes
    public List<Ville> extractVilles() {
        return villeDao.findAll();
    }

    // Extraire une ville par son ID
    public Ville extractVille(int idVille) {
        return villeDao.findById(idVille);
    }

    // Extraire une ville par son nom
    public Ville extractVille(String nom) {
        return villeDao.findByNom(nom);
    }

    // Création d'une nouvelle ville
    @Transactional
    public List<Ville> createVille(Ville ville) {
        villeDao.save(ville);
        return villeDao.findAll(); // Retourne la liste mise à jour des villes
    }

    // Modifier une ville existante
    @Transactional
    public List<Ville> modifierVille(int idVille, Ville villeModifiee) {
        Ville updatedVille = villeDao.update(idVille, villeModifiee);
        return updatedVille != null ? villeDao.findAll() : null; // Retourne la liste mise à jour des villes
    }

    // Supprimer une ville
    @Transactional
    public List<Ville> supprimerVille(int idVille) {
        villeDao.delete(idVille);
        return villeDao.findAll(); // Retourne la liste mise à jour des villes
    }
}
