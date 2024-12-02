package fr.diginamic.spring.services;

import fr.diginamic.spring.dto.DepartementDto;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.exception.FunctionalException;
import fr.diginamic.spring.repository.DepartementRepository;
import fr.diginamic.spring.repository.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private RestClient restClient;

    @Transactional
    public List<Departement> extractAllDepartements() {
        return departementRepository.findAllDepartements();
    }

    @Transactional
    public Departement extractDepartementParId(int id) {
        return departementRepository.findById(id).orElse(null);
    }

    @Transactional
    public Departement extractDepartementParNom(String nom) {
        return departementRepository.findByNom(nom).orElse(null);
    }

    @Transactional
    public void insertDepartement(Departement departement) {
        departementRepository.save(departement);
    }

    @Transactional
    public void modifierDepartement(int id, Departement departementModifie) {
        Departement departement = departementRepository.findById(id).orElse(null);
        if (departement != null) {
            departement.setCode(departementModifie.getCode());
            departement.setNom(departementModifie.getNom());
            departementRepository.save(departement);
        }
    }

    @Transactional
    public void supprimerDepartement(int id) {
        departementRepository.deleteById(id);
    }

    public List<Ville> extractVillesParDepartement(String codeDepartement) {
        return villeRepository.findByDepartementCode(codeDepartement);
    }

    @Transactional
    public List<Ville> extractTopNVillesParDepartement(int departementId, int n) {
        Pageable pageable = PageRequest.of(0, n);
        return departementRepository.findTopNVillesByDepartement(departementId, pageable);
    }

    @Transactional
    public List<Ville> extractVillesEntreParDepartement(int departementId, int min, int max) {
        return departementRepository.findVillesByPopulationRangeAndDepartement(departementId, min, max);
    }

    public void validateDepartement(DepartementDto departementDto) throws FunctionalException {
        if (departementDto.getCode() == null || departementDto.getCode().length() < 2 || departementDto.getCode().length() > 3) {
            throw new FunctionalException("Le code département doit contenir entre 2 et 3 caractères.");
        }
        if (departementDto.getNom() == null || departementDto.getNom().length() < 3) {
            throw new FunctionalException("Le nom du département est obligatoire et doit contenir au moins 3 lettres.");
        }
        Optional<Departement> existingDepartement = departementRepository.findByCode(departementDto.getCode());
        if (existingDepartement.isPresent()) {
            throw new FunctionalException("Un département avec ce code existe déjà.");
        }
    }

    public String getNomDepartement(String codeDepartement) {
        String url = "https://geo.api.gouv.fr/departements/" + codeDepartement + "?fields=nom,code,codeRegion";
        Map<String, Object> response = restClient.get().uri(url).retrieve().body(Map.class);
        return response != null && response.containsKey("nom") ? (String) response.get("nom") : "Inconnu";
    }
}
