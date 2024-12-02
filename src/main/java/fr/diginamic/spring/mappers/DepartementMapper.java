package fr.diginamic.spring.mappers;

import fr.diginamic.spring.dto.DepartementDto;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {

    public DepartementDto toDto(Departement departement) {
        if (departement == null) {
            return null;
        }

        int totalHabitants = departement.getVilles() != null ? departement.getVilles().stream().mapToInt(Ville::getNbHabitants).sum() : 0;

        return new DepartementDto(departement.getCode(),departement.getNom(),totalHabitants);
    }

    public Departement toBean(DepartementDto departementDto) {
        Departement departement = new Departement();
        departement.setCode(departementDto.getCode());
        departement.setNom(departementDto.getNom());
        return departement;
    }
}