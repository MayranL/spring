package fr.diginamic.spring.rest;

import fr.diginamic.spring.dto.VilleDto;
import fr.diginamic.spring.models.Ville;
import fr.diginamic.spring.exception.ControlerAdvice;
import fr.diginamic.spring.exception.FunctionalException;
import fr.diginamic.spring.mappers.VilleMapper;
import fr.diginamic.spring.services.DepartementService;
import fr.diginamic.spring.services.VilleService;
/*import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;*/
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ville")
public class VilleControleur extends ControlerAdvice {

    @Autowired
    private VilleService villeService;

    @Autowired
    private DepartementService departementService;

    @Autowired
    private VilleMapper villeMapper;

    @GetMapping("/all")
    public ResponseEntity<List<VilleDto>> getVilles() {
        List<Ville> villes = villeService.getAllVilles();
        List<VilleDto> villesDto = villes.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(villesDto);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<VilleDto>> getAllVilles(@RequestParam int page, @RequestParam int size) {
        Page<Ville> villes = villeService.getAllVillesPage(page, size);
        Page<VilleDto> villesDto = villes.map(villeMapper::toDto);
        return ResponseEntity.ok(villesDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VilleDto> getVilleParId(@PathVariable int id) {
        Ville ville = villeService.getVilleParId(id);
        if (ville == null) {
            return ResponseEntity.notFound().build();
        }
        VilleDto villeDto = villeMapper.toDto(ville);
        return ResponseEntity.ok(villeDto);
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<VilleDto> getVilleParNom(@PathVariable String nom) {
        Ville ville = villeService.getVilleParNom(nom);
        if (ville == null) {
            return ResponseEntity.notFound().build();
        }
        VilleDto villeDto = villeMapper.toDto(ville);
        return ResponseEntity.ok(villeDto);
    }

    @PostMapping
    public ResponseEntity<String> ajouterVille(@Valid @RequestBody VilleDto  nouvelleVilleDto, BindingResult result) throws FunctionalException {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
            throw new FunctionalException("Erreur de validation : " + errorMessage);
        }

        villeService.validateVille(nouvelleVilleDto);

        Ville nouvelleVille = villeMapper.toBean(nouvelleVilleDto);
        if (villeService.getVilleParNom(nouvelleVille.getNom()) != null) {
            throw new FunctionalException("La ville existe déjà.");
        }
        villeService.insertVille(nouvelleVille);
        return ResponseEntity.ok("Ville ajoutée avec succès.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @Valid @RequestBody VilleDto villeModifieeDto, BindingResult result) throws FunctionalException {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
            throw new FunctionalException("Erreur de validation : " + errorMessage);
        }

        villeService.validateVille(villeModifieeDto);

        Ville villeExistante = villeService.getVilleParId(id);
        if (villeExistante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville non trouvée.");
        }
        Ville villeModifiee = villeMapper.toBean(villeModifieeDto);
        villeService.modifierVille(id, villeModifiee);
        return ResponseEntity.ok("Ville modifiée avec succès.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVille(@PathVariable int id) {
        villeService.supprimerVille(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/prefix")
    public ResponseEntity<List<VilleDto>> getParPrefixe(@RequestParam String prefix) throws FunctionalException {
        List<Ville> villes = villeService.getVillesParPrefixe(prefix);
        if (villes.isEmpty()) {
            throw new FunctionalException("Aucune ville dont le nom commence par " + prefix + " n’a été trouvée.");
        }
        List<VilleDto> villesDto = villes.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(villesDto);
    }

    @GetMapping("/population/supA")
    public ResponseEntity<List<VilleDto>> getPopulationSuperieureA(@RequestParam int min) throws FunctionalException {
        List<Ville> villes = villeService.getVillesPopulationSup1(min);
        if (villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population supérieure à " + min + ".");
        }
        List<VilleDto> villesDto = villes.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(villesDto);
    }

    @GetMapping("/exportCsv/superieur")
    public ResponseEntity<String> getPopulationSuperieureAExport(@RequestParam int min, HttpServletResponse response) throws FunctionalException, IOException {
        List<Ville> villes = villeService.getVillesPopulationSup1(min);
        if (villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population supérieure à " + min + ".");
        }
        List<VilleDto> villesDto = villes.stream().map(villeMapper::toDto).toList();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"villes.csv\"");

        PrintWriter writer = response.getWriter();

        for (VilleDto villeDto : villesDto) {
            String codeDept = villeDto.getCodeDepartement();
            String nomDept = departementService.getNomDepartement(codeDept);
            String csvLine = String.format("%s,%d,%s,%s",
                    villeDto.getNom(),
                    villeDto.getNbHabitants(),
                    codeDept,
                    nomDept);

            writer.println(csvLine);
        }

        writer.flush();

        return ResponseEntity.ok("CVS créer.");
    }

    @GetMapping("/population/entre")
    public ResponseEntity<List<VilleDto>> getPopulationEntre(@RequestParam int min, @RequestParam int max) throws FunctionalException {
        List<Ville> villes = villeService.getVillesPopulationEntre(min, max);
        if (villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population comprise entre " + min + " et " + max + ".");
        }
        List<VilleDto> villesDto = villes.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(villesDto);
    }

    @GetMapping("/departement/{id}/population/supA")
    public ResponseEntity<List<VilleDto>> getPopulationSupAParDepartement(@PathVariable int id, @RequestParam int min) throws FunctionalException {
        List<Ville> villes = villeService.getVillesDepartementPopulationSupA(id, min);
        if (villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population supérieure à " + min + ".");
        }
        List<VilleDto> villesDto = villes.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(villesDto);
    }

    @GetMapping("/departement/{id}/population/entre")
    public ResponseEntity<List<VilleDto>> getPopulationEntreParDepartement(@PathVariable int id, @RequestParam int min, @RequestParam int max) throws FunctionalException {
        List<Ville> villes = villeService.getVillesDepartementPopulationEntre(id, min, max);
        if (villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population comprise entre " + min + " et " + max + ".");
        }
        List<VilleDto> villesDto = villes.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(villesDto);
    }

    @GetMapping("/departement/{id}/top")
    public ResponseEntity<List<VilleDto>> getTopNVilles(@PathVariable int id, @RequestParam int n) throws FunctionalException {
        List<Ville> villes = villeService.getTopNVillesDepartement(id, n);
        if (villes.isEmpty()) {
            throw new FunctionalException("Aucune ville trouvée pour le département.");
        }
        List<VilleDto> villesDto = villes.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(villesDto);
    }
}