package fr.diginamic.spring.mappers;

import fr.diginamic.spring.dto.VilleDto;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VilleMapper {
    public VilleDto toDto(Ville ville){
        VilleDto dto = new VilleDto();
        dto.setId(ville.getId());
        dto.setNom(ville.getDepartement().getName());
        dto.setCodeDepartement(ville.getDepartement().getCode());
        dto.setNbHabitants(ville.getNbHabitants());
        dto.setNomDepartement(ville.getDepartement().getName());
        return dto;
    }

    public Ville toBean(VilleDto villeDto){
        Ville bean = new Ville();
        bean.setId(villeDto.getId());
        bean.setDepartement(new Departement(villeDto.getNomDepartement(),villeDto.getCodeDepartement()));
        bean.setNom(villeDto.getNom());
        bean.setNbHabitants(villeDto.getNbHabitants());
        return bean;
    }

    public Iterable<VilleDto> toDtos(Iterable<Ville> villes) {
        ArrayList<VilleDto> dtos = new ArrayList<>();
        for (Ville ville : villes) {
            dtos.add(toDto(ville));
        }
        return dtos;
    }
}
