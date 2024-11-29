package fr.diginamic.spring.rest;

import fr.diginamic.spring.dto.VilleDto;
import fr.diginamic.spring.mappers.VilleMapper;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.services.DepartementService;
import fr.diginamic.spring.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class VilleControleur {

    @Autowired
    private VilleService villeService;

    @Autowired
    private DepartementService departementService;

    @Autowired
    private VilleMapper villeMapper;

    @GetMapping
    public Iterable<VilleDto> getVilles(){

        return villeMapper.toDtos(villeService.getAllVilles());
    }

    // Recherche d'une ville par nom
    @GetMapping("/ville/{nom}")
    public VilleDto getVilleByNom(@PathVariable String nom) throws RestResponseEntityExceptionHandler {
        return villeMapper.toDto(villeService.getVilleByNom(nom));
    }

    // Recherche de villes dont le nom commence par une chaîne donnée
    @GetMapping("/villes/start-with")
    public Iterable<VilleDto> getVillesStartingWith(@RequestParam String prefix) {
        return villeMapper.toDtos(villeService.getVillesStartingWith(prefix));
    }

    // Recherche de villes ayant une population supérieure à un minimum
    @GetMapping("/villes/greater-than-population")
    public Iterable<VilleDto> getVillesWithPopulationGreaterThan(@RequestParam int minPopulation) {
        return villeMapper.toDtos(villeService.getVillesWithPopulationGreaterThan(minPopulation));
    }

    // Recherche de villes avec une population entre deux valeurs
    @GetMapping("/villes/population-range")
    public Iterable<VilleDto> getVillesWithPopulationBetween(@RequestParam int minPopulation, @RequestParam int maxPopulation) {
        return villeMapper.toDtos(villeService.getVillesWithPopulationBetween(minPopulation, maxPopulation));
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
    public VilleDto getVilleById(@PathVariable int id) {
        return villeMapper.toDto(villeService.getVilleById(id).get());
    }

    // Ajouter une ville
    @PostMapping("/ville")
    public ResponseEntity<String> createVille(@RequestBody VilleDto villeDto) throws RestResponseEntityExceptionHandler {
        Ville ville = villeMapper.toBean(villeDto);
        Departement departement = departementService.getDepartementByCode(villeDto.getCodeDepartement());
        if (departement == null) {
            return new ResponseEntity<>("Impossible de créer la ville, pas de département "+villeDto.getCodeDepartement(),HttpStatus.BAD_REQUEST);
        }
        ville.setDepartement(departement);
        return new ResponseEntity<>("Succès !", HttpStatus.OK);
    }

    // Modifier une ville existante
    @PutMapping("/ville/{id}")
    public Ville modifyVille(@PathVariable int id, @RequestBody Ville ville) throws RestResponseEntityExceptionHandler {
        return villeService.modifyVille(id, ville);
    }

    // Supprimer une ville
    @DeleteMapping("/ville/{id}")
    public void deleteVille(@PathVariable int id) {
        villeService.deleteVille(id);
    }
}
