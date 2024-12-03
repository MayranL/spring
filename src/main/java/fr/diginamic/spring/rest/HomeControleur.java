package fr.diginamic.spring.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControleur {

    // Route pour afficher la page d'accueil
    @GetMapping("/")
    public String showHomePage() {
        return "index";  // Affiche index.html
    }

    // Route pour afficher la page intermédiaire de villes
    @GetMapping("/ville")
    public String showVillePage() {
        return "ville";  // Affiche ville.html, une page avec des actions liées aux villes
    }

    // Route pour afficher la page des départements
    @GetMapping("/departement")
    public String showDepartements() {
        return "departement";  // Affiche departement.html
    }
}
