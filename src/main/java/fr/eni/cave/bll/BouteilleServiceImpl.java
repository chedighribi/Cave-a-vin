package fr.eni.cave.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import fr.eni.cave.dal.BouteilleRepository;
import fr.eni.cave.dal.CouleurRepository;
import fr.eni.cave.dal.RegionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BouteilleServiceImpl implements BouteilleService {
	private BouteilleRepository bRepository;
	private RegionRepository rRepository;
	private CouleurRepository cRepository;

	@Override
	public List<Bouteille> chargerToutesBouteilles() {
		return bRepository.findAll();
	}

	@Override
	public Bouteille chargerBouteilleParId(int idBouteille) {
		// Validation de l'identifiant
		if (idBouteille <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Bouteille> opt = bRepository.findById(idBouteille);
		if (opt.isPresent()) {
			return opt.get();
		}
		// Identifiant correspond à aucun enregistrement en base
		throw new RuntimeException("Aucune bouteille ne correspond");
	}

	@Override
	public List<Bouteille> chargerBouteillesParRegion(int idRegion) {
		final Region rDB = validerRegion(idRegion);

		final List<Bouteille> listeDB = bRepository.findByRegion(rDB);
		if (listeDB == null || listeDB.isEmpty()) {
			throw new RuntimeException("Aucune bouteille ne correspond");
		}
		return listeDB;		
	}

	private Region validerRegion(int idRegion) {
		// Valider la Region
		if (idRegion <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Region> opt = rRepository.findById(idRegion);
		if (opt.isPresent()) {
			return opt.get();
		}
		// Identifiant correspond à aucun enregistrement en base
		throw new RuntimeException("Aucune région ne correspond");
	}

	@Override
	public List<Bouteille> chargerBouteillesParCouleur(int idCouleur) {
		final Couleur cDB = validerCouleur(idCouleur);

		final List<Bouteille> listeDB = bRepository.findByCouleur(cDB);
		if (listeDB == null || listeDB.isEmpty()) {
			throw new RuntimeException("Aucune bouteille ne correspond");
		}
		return listeDB;		
	}

	private Couleur validerCouleur(int idCouleur) {
		// Valider la Couleur
		if (idCouleur <= 0) {
			throw new RuntimeException("Identifiant n'existe pas");
		}

		final Optional<Couleur> opt = cRepository.findById(idCouleur);
		if (opt.isPresent()) {
			return opt.get();
		}
		// Identifiant correspond à aucun enregistrement en base
		throw new RuntimeException("Aucune couleur de vin ne correspond");
	}
	
	public Bouteille ajouterBouteille(Bouteille bouteille) {
		try {
			return bRepository.save(bouteille);
			} catch (RuntimeException e) {
			throw new RuntimeException("Impossible de sauver - " + bouteille.toString());
			}		
	}
	private void  validerBouteille(Bouteille bouteille) {
		if (bouteille == null) {
			throw new RuntimeException("Bouteille est obligatoire");
			}
			if (bouteille.getCouleur() == null) {
			throw new RuntimeException("Couleur est obligatoire");
			}
			if (bouteille.getRegion() == null) {
			throw new RuntimeException("Région est obligatoire");
			}
			Integer idC = bouteille.getCouleur().getId();
			if (idC == null) {
			throw new RuntimeException("L'identifiant de la couleur est obligatoire");
			}
			Integer idR = bouteille.getRegion().getId();
			if (idR == null) {
			throw new RuntimeException("L'identifiant de la région est obligatoire");
			}
			Couleur cDB = validerCouleur(idC);
			Region rDB = validerRegion(idR);
			validerChaineNonNulle(bouteille.getNom(), "Le nom n'a pas été renseigné");
			if (bouteille.getQuantite() <= 0) {
			throw new RuntimeException("Le nombre de bouteilles doit être positif");
			}
			if (bouteille.getPrix() <= 0) {
				throw new RuntimeException("Le prix doit être positif");
				}
				bouteille.setCouleur(cDB);
				bouteille.setRegion(rDB);
	}
	private void validerChaineNonNulle(String chaine, String msgErreur) {
		if (chaine == null || chaine.isBlank())
		throw new RuntimeException(msgErreur);
		}
	
	@Override
	public void supprimer(int idBouteille) {
	// Validation des données avant suppression
	if (idBouteille <= 0) {
	throw new RuntimeException("Identifiant inconnu");
	}
	try {
	bRepository.deleteById(idBouteille);
	} catch (RuntimeException e) {
	throw new RuntimeException("Impossible de supprimer la bouteille (id : " + idBouteille + ")");
	}
	}
}
