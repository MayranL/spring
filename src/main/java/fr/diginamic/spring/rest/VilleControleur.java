package fr.diginamic.spring.rest;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.spring.dto.VilleDto;
import fr.diginamic.spring.mappers.VilleMapper;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.services.DepartementService;
import fr.diginamic.spring.services.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/villes")
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

    // Recherche ville avec un code departement donné
    @GetMapping("/villes/code")
    public Iterable<VilleDto> getVillesByCode(@RequestParam String code) {
        return villeMapper.toDtos(villeService.getVillesByDepartementCode(code));
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

    @GetMapping("/villes/pdf")
    public void exportPdf(@RequestParam int min, HttpServletResponse response) throws IOException, DocumentException, RestResponseEntityExceptionHandler {
        response.setHeader("Content-Disposition", "attachment; filename=villes.pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        Iterable<VilleDto> villes = getVillesWithPopulationGreaterThan(min);

        document.open();
        document.addTitle("Villes");
        document.newPage();
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        for (VilleDto ville : villes) {
            Phrase p = new Phrase("Nom : "+ville.getNom() + " / Nb habitants : "+ville.getNbHabitants() + " / Code département : "+ville.getCodeDepartement() + " / Nom département : "+ ville.getNomDepartement() + "\n");
            document.add(p);
        }
        //Phrase p1 = new Phrase("COUCOU", new Font(baseFont, 32.0f, 1, new BaseColor(0, 51, 80)));
        //document.add(p1);
        document.close();

        response.flushBuffer();
    }

    @GetMapping("/villes/csv")
    public String exportVillesCSV(int min) {
        StringBuilder sb = new StringBuilder();
        String HEADER = "Nom;Nb habitants;Nom département;Numéro département\n";
        sb.append(HEADER);
        List<VilleDto> dtos = villeService.getVillesWithPopulationGreaterThan(min).stream().map(v -> villeMapper.toDto(v)).toList();
        return dtos.stream().map(v -> v.getNom() + ";" + v.getNbHabitants() + ";" + v.getNomDepartement() + ";" + v.getCodeDepartement() + "\n").collect(() -> sb, StringBuilder::append, StringBuilder::append).toString();
    }
}
