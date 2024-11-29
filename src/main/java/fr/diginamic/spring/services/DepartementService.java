package fr.diginamic.spring.services;

import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    // Recherche d'un département par son code
    public Departement getDepartementByCode(String code) {
        return departementRepository.findByCode(code);
    }

    // Recherche d'un département par son nom
    public Optional<Departement> getDepartementByName(String name) {
        return departementRepository.findByName(name);
    }

    // Recherche de départements ayant un nom contenant une certaine chaîne
    public List<Departement> getDepartementsByNameContaining(String namePart) {
        return departementRepository.findByNameContaining(namePart);
    }

    // Recherche des départements ayant plus de n villes
    public List<Departement> getDepartementsWithMoreThanNVilles(int minVilles) {
        return departementRepository.findDepartementsWithMoreThanNVilles(minVilles);
    }

    // Recherche d'un département par son code, avec ses villes associées
    public Optional<Departement> getDepartementWithVilles(String code) {
        return departementRepository.findByCodeWithVilles(code);
    }

    // Recherche d'un département par son nom, avec ses villes associées
    public Optional<Departement> getDepartementByNameWithVilles(String name) {
        return departementRepository.findByNameWithVilles(name);
    }

    // Recherche d'un département par son code, trié par le nom des villes
    public List<Departement> getDepartementWithSortedCitiesByName(String code) {
        return departementRepository.findDepartementWithSortedCitiesByName(code);
    }
}
