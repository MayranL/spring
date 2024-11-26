package fr.diginamic.spring.rest;

import fr.diginamic.spring.models.Ville;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("villes")
public class VilleControleur {

    // Liste de villes simulée (en attendant la base de données)
    List<Ville> villes = new ArrayList<>(Arrays.asList(
            new Ville("Paris", 2165000),
            new Ville("Lyon", 515695),
            new Ville("Marseille", 861635),
            new Ville("Toulouse", 493465),
            new Ville("Nice", 343000),
            new Ville("Nantes", 314138),
            new Ville("Strasbourg", 280966),
            new Ville("Bordeaux", 257068),
            new Ville("Lille", 233000),
            new Ville("Montpellier", 290000)
    ));

    /**
     * Méthode qui permet de récupérer la liste complète de toutes les villes.
     *
     * @return List<Ville> La liste des villes.
     */
    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    /**
     * Méthode qui permet de récupérer une ville spécifique en fonction de son identifiant (id).
     *
     * @param id L'identifiant de la ville à récupérer.
     * @return Ville L'objet Ville correspondant à l'identifiant ou une réponse HTTP 404 si non trouvée.
     */
    @GetMapping("/ville/{id}")
    public ResponseEntity<Ville> getVille(@PathVariable int id) {
        for (Ville v : villes) {
            if (v.getId() == id) {
                return new ResponseEntity<>(v, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourner 404 si la ville n'est pas trouvée
    }

    /**
     * Méthode qui permet de récupérer une ville spécifique en fonction de son nom.
     *
     * @param nom Le nom de la ville à récupérer.
     * @return Ville L'objet Ville correspondant au nom ou une réponse HTTP 404 si non trouvée.
     */
    @GetMapping("/ville/nom/{nom}")
    public ResponseEntity<Ville> getVilleByName(@PathVariable String nom) {
        for (Ville v : villes) {
            if (v.getNom().equalsIgnoreCase(nom)) {
                return new ResponseEntity<>(v, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourner 404 si la ville n'est pas trouvée
    }

    /**
     * Méthode qui permet d'ajouter une nouvelle ville à la liste des villes.
     *
     * @param ville L'objet Ville à ajouter à la liste.
     * @return ResponseEntity<String> Un message et un code de statut HTTP correspondant.
     */
    @PostMapping
    public ResponseEntity<String> addVille(@Valid @RequestBody Ville ville, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }

        // Vérifier si la ville existe déjà (même nom ou id)
        for (Ville v : villes) {
            if (v.getNom().equals(ville.getNom())) {
                return new ResponseEntity<>("La ville existe déjà", HttpStatus.BAD_REQUEST);
            }
        }

        // Ajouter la ville à la liste (avec un ID généré automatiquement)
        ville.setId(villes.size() + 1);  // Simuler l'attribution d'un ID unique
        villes.add(ville);
        return new ResponseEntity<>("Ville insérée avec succès", HttpStatus.CREATED);
    }

    /**
     * Méthode qui permet de mettre à jour une ville existante.
     *
     * @param ville L'objet Ville avec les informations mises à jour.
     * @return ResponseEntity<String> Un message et un code de statut HTTP correspondant.
     */
    @PutMapping("/ville/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @Valid @RequestBody Ville ville, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new Exception(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }

        for (Ville v : villes) {
            if (v.getId() == id) {
                v.setNom(ville.getNom());
                v.setNbHabitants(ville.getNbHabitants());
                return new ResponseEntity<>("Ville mise à jour correctement", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Ville non trouvée", HttpStatus.NOT_FOUND);
    }

    /**
     * Méthode qui permet de supprimer une ville en fonction de son identifiant (id).
     *
     * @param id L'identifiant de la ville à supprimer.
     * @return ResponseEntity<String> Un message et un code de statut HTTP correspondant.
     */
    @DeleteMapping("/ville/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        for (Ville v : villes) {
            if (v.getId() == id) {
                villes.remove(v);
                return new ResponseEntity<>("Ville supprimée avec succès", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Ville non trouvée", HttpStatus.NOT_FOUND);
    }
}
