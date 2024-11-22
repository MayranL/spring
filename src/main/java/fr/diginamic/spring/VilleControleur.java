package fr.diginamic.spring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("villes")
public class VilleControleur {
    List<Ville> villes = new ArrayList<>(Arrays.asList(
            new Ville(1, "Paris", 2165000),
            new Ville(2, "Lyon", 515695),
            new Ville(3, "Marseille", 861635),
            new Ville(4, "Toulouse", 493465),
            new Ville(5, "Nice", 343000),
            new Ville(6, "Nantes", 314138),
            new Ville(7, "Strasbourg", 280966),
            new Ville(8, "Bordeaux", 257068),
            new Ville(9, "Lille", 233000),
            new Ville(10, "Montpellier", 290000)
    ));

    /**
     * Méthode qui permet de récupérer la liste de villes
     * @return List<Ville>
     */
    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    @GetMapping("/ville/{id}")
    public Ville getVille(@PathVariable int id) {
        for(Ville v : villes){
            if(v.getId() == id){
                return v;
            }
        }
        return null;
    }

    /**
     * Méthode qui permet d'ajouter une ville à la liste villes
     * @param ville
     * @return ResponseEntity<String>
     */
    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody Ville ville) {
        // Validation des données
        if (ville.getNom() == null || ville.getNom().length() < 2) {
            return new ResponseEntity<>("Le nom de la ville doit avoir au moins 2 caractères", HttpStatus.BAD_REQUEST);
        }

        try {
            if (ville.getNbHabitants() < 1) {
                return new ResponseEntity<>("Le nombre d'habitants doit être supérieur ou égal à 1", HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Le nombre d'habitants est invalide", HttpStatus.BAD_REQUEST);
        }

        // Vérifier si la ville et l'id existent déjà
        for (Ville v : villes) {
            if(v.getId() == ville.getId()) {
                return new ResponseEntity<>("L'id existe déjà", HttpStatus.BAD_REQUEST);
            }
            if (v.getNom().equals(ville.getNom())) {
                return new ResponseEntity<>("La ville existe déjà", HttpStatus.BAD_REQUEST);
            }
        }

        villes.add(ville);
        return new ResponseEntity<>("Ville insérée avec succès", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateVille(@RequestBody Ville ville) {
        if (ville.getNom() == null || ville.getNom().length() < 2) {
            return new ResponseEntity<>("Le nom doit faire au moins 2 lettrs", HttpStatus.BAD_REQUEST);
        }
        for (Ville v : villes) {
            if(ville.getNom().equals(v.getNom())){
                return new ResponseEntity<>("Nom de ville déjà utilisé", HttpStatus.BAD_REQUEST);
            }
            if(v.getId() == ville.getId() && ville.getNbHabitants()>=1) {
                v.setNom(ville.getNom());
                v.setNbHabitants(ville.getNbHabitants());
                return new ResponseEntity<>("Ville mise à jour correctement !", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Probleme dans la création", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/ville/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        for(Ville v : villes) {
            if(v.getId() == id) {
                villes.remove(v);
                return new ResponseEntity<>("Ville bien supprimé", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Aucune ville trouvé avec son id", HttpStatus.BAD_REQUEST);
    }
}
