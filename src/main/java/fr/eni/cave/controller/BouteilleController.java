package fr.eni.cave.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.eni.cave.bll.BouteilleService;
import fr.eni.cave.bo.vin.Bouteille;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/caveavin/bouteilles")

public class BouteilleController {
	private BouteilleService bService;
	
	@GetMapping
	public ResponseEntity<?> rechercherTousBouteilles() {
		final List<Bouteille> bouteilles = bService.chargerToutesBouteilles();
		
		if (bouteilles == null || bouteilles.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(bouteilles);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> rechercherBouteilleParId(@PathVariable("id") String idInPath) {
		try {
			int id = Integer.parseInt(idInPath);
			final Bouteille bot = bService.chargerBouteilleParId(id);
			return ResponseEntity.ok(bot);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/region/{id}")
	public ResponseEntity<?> rechercherBouteillesParRegion(@PathVariable("id") String idInPath) {
	try {
	final int idRegion = Integer.parseInt(idInPath);
	final List<Bouteille> bouteilles = bService.chargerBouteillesParRegion(idRegion);
	return ResponseEntity.ok(bouteilles);
	} catch (NumberFormatException e) {
	// Statut 406 : No Acceptable
	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier");
	} catch (RuntimeException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	}
	@GetMapping("/couleur/{id}")
	public ResponseEntity<?> rechercherBouteillesParCouleur(@PathVariable("id") String idInPath) {
	try {
	final int idCouleur = Integer.parseInt(idInPath);
	final List<Bouteille> bouteilles = bService.chargerBouteillesParCouleur(idCouleur);
	return ResponseEntity.ok(bouteilles);
	} catch (NumberFormatException e) {
	// Statut 406 : No Acceptable
	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Votre identifiant n'est pas un entier");
	} catch (RuntimeException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	}
	

}
