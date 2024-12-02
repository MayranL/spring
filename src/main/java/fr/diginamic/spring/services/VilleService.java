package fr.diginamic.spring.services;

import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.repository.VilleRepository;
import fr.diginamic.spring.rest.RestResponseEntityExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    public Iterable<Ville> getAllVilles(){
        return villeRepository.findAll();
    }

    // Recherche d'une ville par son nom
    public Ville getVilleByNom(String nom) {
        return villeRepository.findByNom(nom);
    }

    // Recherche des villes dont le nom commence par une chaîne donnée
    public List<Ville> getVillesStartingWith(String prefix) {
        return villeRepository.findByNomStartingWith(prefix);
    }

    // Recherche des villes ayant une population supérieure à un minimum
    public List<Ville> getVillesWithPopulationGreaterThan(int minPopulation) {
        return villeRepository.findAllByNbHabitantsGreaterThan(minPopulation);
    }

    // Recherche des villes dont la population est comprise entre deux valeurs
    public List<Ville> getVillesWithPopulationBetween(int minPopulation, int maxPopulation) {
        return villeRepository.findAllByNbHabitantsBetween(minPopulation, maxPopulation);
    }

    // Recherche des n villes les plus peuplées d'un département
    public Page<Ville> getTopNVillesByDepartement(String codeDepartement, int n, Pageable pageable) {
        return villeRepository.findTopNVillesByDepartementCodeOrderByNbHabitantsDesc(codeDepartement, pageable);
    }

    // Recherche d'une ville par son ID
    public Optional<Ville> getVilleById(int id) {
        return villeRepository.findById(id);
    }

    // Ajouter une nouvelle ville
    public Ville createVille(Ville ville) throws RestResponseEntityExceptionHandler {
        idDispo(ville);
        valideVille(ville);
        return villeRepository.save(ville);
    }

    // Modifier une ville existante
    public Ville modifyVille(int id, Ville ville) throws RestResponseEntityExceptionHandler {
        valideVille(ville);
        Optional<Ville> existingVille = villeRepository.findById(id);
        if (existingVille.isPresent()) {
            Ville updatedVille = existingVille.get();
            updatedVille.setNom(ville.getNom());
            updatedVille.setNbHabitants(ville.getNbHabitants());
            updatedVille.setDepartement(ville.getDepartement());
            return villeRepository.save(updatedVille);
        } else {
            return null; // Ou lancer une exception selon la logique
        }
    }

    // Supprimer une ville
    public void deleteVille(int id) {
        villeRepository.deleteById(id);
    }

    public void valideVille(Ville ville) throws RestResponseEntityExceptionHandler {
        if (ville.getNbHabitants() < 2) {
            throw new RestResponseEntityExceptionHandler("Pas assez d'habitants");
        } else if (ville.getNom().length() < 2) {
            throw new RestResponseEntityExceptionHandler("Nom pas assez long");
        }
    }

    public void idDispo(Ville ville) throws RestResponseEntityExceptionHandler {
        if (villeRepository.findById(ville.getId()).isPresent()) {
            throw new RestResponseEntityExceptionHandler("Id déjà présente");
        }
    }

    public List<Ville> getVillesByDepartementCode(String codeDepartement) {
        return villeRepository.findVillesByDepartement_Code(codeDepartement);
    }
}
