package fr.diginamic.spring.rest;

import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class VilleControleur {

    @Autowired
    private VilleService villeService;

    // Recherche d'une ville par nom
    @GetMapping("/ville/{nom}")
    public Optional<Ville> getVilleByNom(@PathVariable String nom) {
        return villeService.getVilleByNom(nom);
    }

    // Recherche de villes dont le nom commence par une chaîne donnée
    @GetMapping("/villes/start-with")
    public List<Ville> getVillesStartingWith(@RequestParam String prefix) {
        return villeService.getVillesStartingWith(prefix);
    }

    // Recherche de villes ayant une population supérieure à un minimum
    @GetMapping("/villes/greater-than-population")
    public List<Ville> getVillesWithPopulationGreaterThan(@RequestParam int minPopulation) {
        return villeService.getVillesWithPopulationGreaterThan(minPopulation);
    }

    // Recherche de villes avec une population entre deux valeurs
    @GetMapping("/villes/population-range")
    public List<Ville> getVillesWithPopulationBetween(@RequestParam int minPopulation, @RequestParam int maxPopulation) {
        return villeService.getVillesWithPopulationBetween(minPopulation, maxPopulation);
    }

    // Recherche des n villes les plus peuplées d'un département
    @GetMapping("/villes/top-n-departement")
    public Page<Ville> getTopNVillesByDepartement(
            @RequestParam String codeDepartement,
            @RequestParam int n,
            Pageable pageable) {
        return villeService.getTopNVillesByDepartement(codeDepartement, n, pageable);
    }

    // Recherche d'une ville par son ID
    @GetMapping("/ville/id/{id}")
    public Optional<Ville> getVilleById(@PathVariable int id) {
        return villeService.getVilleById(id);
    }

    // Ajouter une ville
    @PostMapping("/ville")
    public Ville createVille(@RequestBody Ville ville) {
        return villeService.createVille(ville);
    }

    // Modifier une ville existante
    @PutMapping("/ville/{id}")
    public Ville modifyVille(@PathVariable int id, @RequestBody Ville ville) {
        return villeService.modifyVille(id, ville);
    }

    // Supprimer une ville
    @DeleteMapping("/ville/{id}")
    public void deleteVille(@PathVariable int id) {
        villeService.deleteVille(id);
    }
}
