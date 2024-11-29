package fr.diginamic.spring.rest;

import fr.diginamic.spring.dto.DepartementDto;
import fr.diginamic.spring.mappers.DepartementMapper;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartementControleur {

    @Autowired
    private DepartementService departementService;

    private final DepartementMapper departementMapper = new DepartementMapper();

    // Recherche d'un département par code
    @GetMapping("/departement/code")
    public DepartementDto getDepartementByCode(@RequestParam String code) {
        return departementMapper.toDto(departementService.getDepartementByCode(code));
    }

    // Recherche des départements avec plus de n villes
    @GetMapping("/departements/villes")
    public List<Departement> getDepartementsWithMoreThanNVilles(@RequestParam int minVilles) {
        return departementService.getDepartementsWithMoreThanNVilles(minVilles);
    }

    // Recherche d'un département par nom
    @GetMapping("/departement/name")
    public List<DepartementDto> getDepartementByName(@RequestParam String name) {
        return departementService.getDepartementByName(name).stream().map(d->departementMapper.toDto(d)).toList();
    }

    // Recherche d'un département avec ses villes par code
    @GetMapping("/departement/code-villes")
    public Optional<Departement> getDepartementWithVilles(@RequestParam String code) {
        return departementService.getDepartementWithVilles(code);
    }

    // Recherche d'un département par son nom avec villes associées
    @GetMapping("/departement/name-villes")
    public Optional<Departement> getDepartementByNameWithVilles(@RequestParam String name) {
        return departementService.getDepartementByNameWithVilles(name);
    }

    // Recherche d'un département par son code, trié par le nom des villes
    @GetMapping("/departement/code-villes-sorted")
    public List<Departement> getDepartementWithSortedCitiesByName(@RequestParam String code) {
        return departementService.getDepartementWithSortedCitiesByName(code);
    }
}
