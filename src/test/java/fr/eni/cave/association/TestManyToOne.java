package fr.eni.cave.association;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import fr.eni.cave.bo.vin.*;
import fr.eni.cave.dal.*;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@DataJpaTest
public class TestManyToOne {
@Autowired
private TestEntityManager entityManager;
@Autowired
BouteilleRepository bouteilleRepository;
@Autowired
CouleurRepository couleurRepository;
@Autowired
RegionRepository regionRepository;

Couleur rouge;
Couleur blanc;
Couleur rose;

Region grandEst;
Region paysDeLaLoire;
Region nouvelleAquitaine;

@BeforeEach
public void initDB() {
	rouge = Couleur
			.builder()
			.nom("Rouge")
			.build();
	blanc = Couleur
			.builder()
			.nom("Blanc")
			.build();
	rose = Couleur
			.builder()
			.nom("Rosé")
			.build();

	couleurRepository.save(rouge);
	couleurRepository.save(blanc);
	couleurRepository.save(rose);
	
	grandEst = Region
			.builder()
			.nom("Grand Est")
			.build();
	paysDeLaLoire = Region
			.builder()
			.nom("Pays de la loire")
			.build();
	nouvelleAquitaine = Region
			.builder()
			.nom("Nouvelle Aquitaine")
			.build();
	
	regionRepository.save(grandEst);
	regionRepository.save(paysDeLaLoire);
	regionRepository.save(nouvelleAquitaine);
	

}

@Test
public void test_save() {
final Bouteille bouteille = Bouteille
.builder()
.nom("Blanc du DOMAINE ENI Ecole")
.millesime("2022")
.prix(23.95f)
.quantite(1298)
.build();
// Association ManyToOne
bouteille.setRegion(paysDeLaLoire);
bouteille.setCouleur(blanc);
// Appel du comportement
final Bouteille bouteilleDB = bouteilleRepository.save(bouteille);
System.out.println("---------------------");
System.out.println(bouteilleDB);
// Vérification de l'identifiant
assertThat(bouteilleDB.getId()).isGreaterThan(0);
// Vérification des associations
assertThat(bouteilleDB.getCouleur()).isNotNull();
assertThat(bouteilleDB.getCouleur()).isEqualTo(blanc);
assertThat(bouteilleDB.getRegion()).isNotNull();
assertThat(bouteilleDB.getRegion()).isEqualTo(paysDeLaLoire);
log.info(bouteilleDB.toString());
}
/*
 * @Test public void test_delete() { final Bouteille bouteille = Bouteille
 * .builder() .nom("Blanc du DOMAINE ENI Ecole") .millesime("2022")
 * .prix(23.95f) .quantite(1298) .build(); //Association ManyToOne
 * bouteille.setRegion(paysDeLaLoire); bouteille.setCouleur(blanc); //Appel du
 * comportement final Bouteille bouteilleDB =entityManager.persist(bouteille);
 * entityManager.flush(); //Vérification de l'identifiant
 * assertThat(bouteilleDB.getId()).isGreaterThan(0);
 * assertThat(bouteilleDB.getCouleur()).isNotNull();
 * assertThat(bouteilleDB.getCouleur()).isEqualTo(blanc);
 * assertThat(bouteilleDB.getRegion()).isNotNull();
 * assertThat(bouteilleDB.getRegion()).isEqualTo(paysDeLaLoire); //Appel du
 * comportement bouteilleRepository.delete(bouteilleDB); //Vérification que
 * l'entité a été supprimée final Bouteille bouteilleDB2 =
 * entityManager.find(Bouteille.class, bouteilleDB.getId());
 * assertNull(bouteilleDB2); //Vérifier que les éléments associés sont toujours
 * présents - PAS de cascade final List<Region> regions =
 * regionRepository.findAll(); assertThat(regions).isNotNull();
 * assertThat(regions).isNotEmpty(); assertThat(regions.size()).isEqualTo(3);
 * final List<Couleur> couleurs = couleurRepository.findAll();
 * assertThat(couleurs).isNotNull(); assertThat(couleurs).isNotEmpty();
 * assertThat(couleurs.size()).isEqualTo(3); }
 */
}