package fr.diginamic.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("villes")
public class VilleControleur {

    @GetMapping
    public List<Ville> getVilles() {
        List<Ville> villes = new ArrayList<>();
        villes.add(new Ville("Paris", "2 165 000"));
        villes.add(new Ville("Lyon", "515 695"));
        villes.add(new Ville("Marseille", "861 635"));
        villes.add(new Ville("Toulouse", "493 465"));
        villes.add(new Ville("Nice", "343 000"));
        villes.add(new Ville("Nantes", "314 138"));
        villes.add(new Ville("Strasbourg", "280 966"));
        villes.add(new Ville("Bordeaux", "257 068"));
        villes.add(new Ville("Lille", "233 000"));
        villes.add(new Ville("Montpellier", "290 000"));
        return villes;
    }
}
