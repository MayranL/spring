package fr.diginamic.spring.services;

import fr.diginamic.spring.dto.VilleDto;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.exception.FunctionalException;
import fr.diginamic.spring.repository.DepartementRepository;
import fr.diginamic.spring.repository.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Transactional
    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }

    @Transactional
    public Page<Ville> getAllVillesPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeRepository.findAll(pageable);
    }

    @Transactional
    public Ville getVilleParId(int idVille) {
        return villeRepository.findById(idVille).orElse(null);
    }

    @Transactional
    public Ville getVilleParNom(String nom) {
        return villeRepository.findByNom(nom);
    }

    @Transactional
    public void insertVille(Ville ville) {
        Optional<Departement> optionalDepartement = departementRepository.findByCode(ville.getDepartement().getCode());
        if (optionalDepartement.isPresent()) {
            ville.setDepartement(optionalDepartement.get());
            villeRepository.save(ville);
        }

    }

    @Transactional
    public void modifierVille(int idVille, Ville villeModifiee) {
        Ville villeExistante = villeRepository.findById(idVille).orElse(null);
        if (villeExistante != null) {
            villeExistante.setNom(villeModifiee.getNom());
            villeExistante.setNbHabitants(villeModifiee.getNbHabitants());

            Optional<Departement> optionalDepartement = departementRepository.findById(villeModifiee.getDepartement().getId());
            if (optionalDepartement.isPresent()) {
                villeExistante.setDepartement(optionalDepartement.get());
                villeRepository.save(villeExistante);
            }

        }
    }

    @Transactional
    public void supprimerVille(int idVille) {
        villeRepository.deleteById(idVille);
    }

    @Transactional
    public List<Ville> getVillesParPrefixe(String prefix) {
        return villeRepository.findByNomStartingWith(prefix);
    }

    @Transactional
    public List<Ville> getVillesPopulationSup1(int min) {
        return villeRepository.findByNbHabitantsGreaterThan(min);
    }

    @Transactional
    public List<Ville> getVillesPopulationEntre(int min, int max) {
        return villeRepository.findByNbHabitantsBetween(min, max);
    }

    @Transactional
    public List<Ville> getVillesDepartementPopulationSupA(int departementId, int min) {
        return villeRepository.findByDepartementIdAndNbHabitantsGreaterThan(departementId, min);
    }

    @Transactional
    public List<Ville> getVillesDepartementPopulationEntre(int departementId, int min, int max) {
        return villeRepository.findByDepartementIdAndNbHabitantsBetween(departementId, min, max);
    }

    @Transactional
    public List<Ville> getTopNVillesDepartement(int departementId, int n) {
        Pageable pageable = PageRequest.of(0, n);
        return villeRepository.findTopNByDepartementIdOrderByNbHabitantsDesc(departementId, pageable);
    }

    public void validateVille(VilleDto villeDto) throws FunctionalException {
        if (villeDto.getNbHabitants() < 10) {
            throw new FunctionalException("La ville doit avoir au moins 10 habitants.");
        }
        if (villeDto.getNom() == null || villeDto.getNom().length() < 2) {
            throw new FunctionalException("Le nom de la ville doit contenir au moins 2 lettres.");
        }
        if (villeDto.getCodeDepartement() == null || villeDto.getCodeDepartement().length() != 2) {
            throw new FunctionalException("Le code département doit contenir exactement 2 caractères.");
        }
    }
}