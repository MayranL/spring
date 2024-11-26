package fr.diginamic.spring.rest;

import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("departements")
public class DepartementControleur {

    // Liste simulée des départements
    private List<Departement> departements = new ArrayList<>();

    // Méthode pour ajouter un département
    @PostMapping
    public String addDepartement(@RequestBody Departement departement) {
        departement.setId(departements.size() + 1);  // Simuler un ID unique
        departements.add(departement);
        return "Département ajouté avec succès";
    }

    // Méthode pour obtenir tous les départements
    @GetMapping
    public List<Departement> getAllDepartements() {
        return departements;
    }

    // Méthode pour obtenir un département par son ID
    @GetMapping("/{id}")
    public String getDepartementById(@PathVariable int id) {
        for (Departement departement : departements) {
            if (departement.getId() == id) {
                return "Département trouvé : " + departement.getNom();
            }
        }
        return "Département non trouvé";
    }

    // Méthode pour mettre à jour un département
    @PutMapping("/{id}")
    public String updateDepartement(@PathVariable int id, @RequestBody Departement departement) {
        for (int i = 0; i < departements.size(); i++) {
            if (departements.get(i).getId() == (id)) {
                departements.get(i).setNom(departement.getNom());
                return "Département mis à jour avec succès";
            }
        }
        return "Département non trouvé";
    }

    // Méthode pour supprimer un département
    @DeleteMapping("/{id}")
    public String deleteDepartement(@PathVariable int id) {
        for (int i = 0; i < departements.size(); i++) {
            if (departements.get(i).getId() == (id)) {
                departements.remove(i);
                return "Département supprimé avec succès";
            }
        }
        return "Département non trouvé";
    }

    @GetMapping("/{id}/grandes-villes/{n}")
    public List<Ville> getTopNCitiesByPopulation(@PathVariable int id, @PathVariable int n) {
        // Rechercher le département correspondant à l'ID
        for (Departement departement : departements) {
            if (departement.getId() == id) {
                // Convertir le Set de villes en une liste mutable
                List<Ville> villesList = new ArrayList<>(departement.getVilles());

                // Trier les villes par population décroissante
                villesList.sort((v1, v2) -> Integer.compare(v2.getNbHabitants(), v1.getNbHabitants()));

                // Retourner les n plus grandes villes
                return villesList.subList(0, Math.min(n, villesList.size()));
            }
        }
        // Retourner une liste vide si le département n'est pas trouvé
        return new ArrayList<>();
    }


    // Méthode pour lister les villes avec une population comprise entre min et max dans un département
    @GetMapping("/{id}/villes-population")
    public List<Ville> getCitiesByPopulationRange(@PathVariable int id, @RequestParam int min, @RequestParam int max) {
        for (Departement departement : departements) {
            if (departement.getId() == (id)) {
                List<Ville> villesInRange = new ArrayList<>();
                for (Ville ville : departement.getVilles()) {
                    if (ville.getNbHabitants() >= min && ville.getNbHabitants() <= max) {
                        villesInRange.add(ville);
                    }
                }
                return villesInRange;
            }
        }
        return new ArrayList<>();  // Retourner une liste vide si le département n'est pas trouvé
    }
}
