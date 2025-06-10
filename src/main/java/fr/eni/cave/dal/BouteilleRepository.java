package fr.eni.cave.dal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
public interface BouteilleRepository extends JpaRepository<Bouteille, Integer>{
	List<Bouteille> findByRegion(@Param("r") Region r);
	List<Bouteille> findByCouleur(@Param("c") Couleur c);
}