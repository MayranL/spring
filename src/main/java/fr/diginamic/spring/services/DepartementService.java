package fr.diginamic.spring.services;

import fr.diginamic.spring.dao.DepartementDao;
import fr.diginamic.spring.dao.VilleDao;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartementService {

    private DepartementDao departementDao;
    private VilleDao villeDao;

    @Autowired
    public DepartementService(DepartementDao departementDao, VilleDao villeDao) {
        this.departementDao = departementDao;
        this.villeDao = villeDao;
    }

    // Méthode pour obtenir un département par ID
    public Departement getDepartementById(int id) {
        return departementDao.getDepartementById(id);
    }

    // Méthode pour obtenir tous les départements
    public List<Departement> getAllDepartements() {
        return departementDao.getAllDepartements();
    }

    // Méthode pour créer un département
    @Transactional
    public void createDepartement(Departement departement) {
        departementDao.createDepartement(departement);
    }

    // Méthode pour mettre à jour un département
    @Transactional
    public Departement updateDepartement(int id, Departement departement) {
        departement.setId(id);  // On assure que l'ID reste inchangé
        return departementDao.updateDepartement(departement);
    }

    // Méthode pour supprimer un département
    @Transactional
    public void deleteDepartement(int id) {
        departementDao.deleteDepartement(id);
    }

    // Méthode pour lister les n plus grandes villes d'un département
    public List<Ville> getTopNCitiesByPopulation(int departementId, int n) {
        List<Ville> villes = villeDao.getVillesByDepartement(departementId);

        // Trier les villes par population décroissante
        villes.sort((v1, v2) -> Integer.compare(v2.getNbHabitants(), v1.getNbHabitants()));

        // Retourner les n premières villes
        return villes.subList(0, Math.min(n, villes.size()));
    }

    // Méthode pour lister les villes ayant une population comprise entre min et max dans un département donné
    public List<Ville> getVillesByPopulationRange(int departementId, int minPopulation, int maxPopulation) {
        List<Ville> villes = villeDao.getVillesByDepartement(departementId);
        List<Ville> filteredVilles = new ArrayList<>();

        for (Ville ville : villes) {
            if (ville.getNbHabitants() >= minPopulation && ville.getNbHabitants() <= maxPopulation) {
                filteredVilles.add(ville);
            }
        }

        return filteredVilles;
    }
}
