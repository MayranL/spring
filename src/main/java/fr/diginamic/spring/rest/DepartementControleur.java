package fr.diginamic.spring.rest;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.spring.dto.DepartementDto;
import fr.diginamic.spring.dto.VilleDto;
import fr.diginamic.spring.mappers.DepartementMapper;
import fr.diginamic.spring.models.Departement;
import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.services.DepartementService;
import fr.diginamic.spring.services.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class DepartementControleur {

    @Autowired
    private DepartementService departementService;

    @Autowired
    private VilleService villeService;

    private final DepartementMapper departementMapper = new DepartementMapper();

    @GetMapping("/departements")
    public List<DepartementDto> findAll(){

        return departementService.findAll().stream().map(d->departementMapper.toDto(d)).toList();
    }

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
        return departementService.getDepartementByName(name).stream().map(d -> departementMapper.toDto(d)).toList();
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

    @GetMapping("/departement/pdf")
    public void exportPdf(@RequestParam String code, HttpServletResponse response) throws IOException, DocumentException, RestResponseEntityExceptionHandler {
        response.setHeader("Content-Disposition", "attachment; filename=villes.pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        DepartementDto departementDto = getDepartementByCode(code);

        List<Ville> villes = villeService.getVillesByDepartementCode(code);

        document.open();
        document.addTitle(departementDto.getNom());


        document.newPage();
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        Phrase pFirst = new Phrase("Code : " + departementDto.getCode() + "\n");
        document.add(pFirst);
        for (Ville v : villes) {
            Phrase p = new Phrase("Ville : " + v.getNom() + " / Population : " + v.getNbHabitants() + "\n");
            document.add(p);
        }
        Phrase pLast = new Phrase("Population totale : " + departementDto.getNbHabitants() + "\n");
        document.add(pLast);
        document.close();

        response.flushBuffer();
    }
}
