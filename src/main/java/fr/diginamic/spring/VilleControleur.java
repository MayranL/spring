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
    List<Ville> villes = new ArrayList<>(
            Arrays.asList(
                    new Ville("Paris", "2 165 000"),
                    new Ville("Lyon", "515 695"),
                    new Ville("Marseille", "861 635"),
                    new Ville("Toulouse", "493 465"),
                    new Ville("Nice", "343 000"),
                    new Ville("Nantes", "314 138"),
                    new Ville("Strasbourg", "280 966"),
                    new Ville("Bordeaux", "257 068"),
                    new Ville("Lille", "233 000"),
                    new Ville("Montpellier", "290 000")));

    /**
     * Méthode qui permet de récupérer la liste de ville
     * @return List<Ville>
     */
    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    /**
     * Méthode qui permet d'ajouter une ville à la liste villes
     * @param ville
     * @return ResponseEntity<String>
     */
    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody Ville ville) {
        for(Ville v : villes){
            if(v.getNom().equals(ville.getNom())){
                return new ResponseEntity<>("La ville existe déjà", HttpStatus.BAD_REQUEST); // Code 400
            }
        }
        villes.add(ville);
        return new ResponseEntity<>("Ville insérée avec succès", HttpStatus.OK);
    }
}
