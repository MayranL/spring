package fr.diginamic.spring;

import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.services.DepartementService;
import fr.diginamic.spring.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    // Injection des services via @Autowired
    @Autowired
    private VilleService villeService;

    @Autowired
    private DepartementService departementService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);  // Démarre l'application Spring Boot
    }

    @Override
    public void run(String... args) throws Exception {
        // Création d'un département
        Departement departement = new Departement("Ile-de-France");
        Departement departement2 = new Departement("Isere");

        // Sauvegarde du département via le service
        departementService.createDepartement(departement);
        departementService.createDepartement(departement2);
        System.out.println("Département créé : " + departement.getNom());

        // Création d'une ville et affectation à un département
        Ville paris = new Ville("Paris", 2165000, departement);  // Ville associée au département
        Ville grenoble = new Ville("Grenoble", 1600, departement2);  // Ville associée au département
        Ville crolles = new Ville("Crolles", 43000, departement2);  // Ville associée au département
        villeService.createVille(paris);  // Sauvegarde de la ville
        villeService.createVille(grenoble);
        villeService.createVille(crolles);

        // Ajouter la ville au département
        departement.getVilles().add(paris);
        departement2.getVilles().add(grenoble);
        departement2.getVilles().add(crolles);
        departementService.updateDepartement(departement.getId(), departement);  // Mettre à jour le département avec la ville associée
        departementService.updateDepartement(departement2.getId(), departement2);

        System.out.println("Ville créée : " + paris.getNom() + " dans le département " + departement.getNom());
    }
}
