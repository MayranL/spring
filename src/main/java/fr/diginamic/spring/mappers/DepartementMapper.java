package fr.diginamic.spring.mappers;

import fr.diginamic.spring.dto.DepartementDto;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class DepartementMapper {
    public DepartementDto toDto(Departement departement) {
        DepartementDto departementDto = new DepartementDto();
        departementDto.setId(departement.getId());
        departementDto.setNom(departement.getName());
        departementDto.setCode(departement.getCode());
        int nbHabitants = departement.getVilles().stream().mapToInt(Ville::getNbHabitants).sum();
        departementDto.setNbHabitants(nbHabitants);
        return departementDto;
    }

    public Departement toBean(DepartementDto departementDto){
        Departement departement = new Departement();
        departement.setId(departementDto.getId());
        departement.setName(departementDto.getNom());
        departement.setCode(departementDto.getCode());
        HashSet<Ville> villesSet = new HashSet<>(departement.getVilles());
        departement.setVilles(villesSet);
        return departement;
    }
}
