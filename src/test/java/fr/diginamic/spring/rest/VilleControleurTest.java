package fr.diginamic.spring.rest;

import fr.diginamic.spring.dto.VilleDto;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.mappers.VilleMapper;
import fr.diginamic.spring.services.VilleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VilleControleurTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VilleService villeService;

    @MockitoBean
    private VilleMapper villeMapper;

    @Test
    public void testAjouterVille_Succes() throws Exception {

        Ville ville = new Ville(13323, "Toulouse", new Departement(1, "31", "Haute-Garonne"), 475438);

        Mockito.when(villeMapper.toBean(any(VilleDto.class))).thenReturn(ville);
        Mockito.when(villeService.getVilleParNom(Mockito.eq("Toulouse"))).thenReturn(null);

        mockMvc.perform(post("/villes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nomVille": "Toulouse",
                                  "nbHabitants": 475438,
                                  "codeDepartement": "31",
                                  "nomDepartement": "Haute-Garonne"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Ville ajoutée avec succès."));

        Mockito.verify(villeMapper).toBean(any(VilleDto.class));
        Mockito.verify(villeService).insertVille(ville);
    }

    @Test
    public void testAjouterVille_VilleExiste() throws Exception {

        Ville ville = new Ville(13323, "Toulouse", new Departement(1, "31", "Haute-Garonne"), 475438);


        Mockito.when(villeMapper.toBean(Mockito.any(VilleDto.class))).thenReturn(ville);
        Mockito.when(villeService.getVilleParNom("Toulouse")).thenReturn(ville);

        mockMvc.perform(post("/villes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nomVille": "Toulouse",
                                  "nbHabitants": 475438,
                                  "codeDepartement": "31",
                                  "nomDepartement": "Haute-Garonne"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("* FunctionalException : La ville existe déjà."));

        Mockito.verify(villeMapper).toBean(any(VilleDto.class));
        Mockito.verify(villeService, never()).insertVille(any(Ville.class));
    }
}
