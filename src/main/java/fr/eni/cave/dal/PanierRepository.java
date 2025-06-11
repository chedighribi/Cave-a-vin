package fr.eni.cave.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eni.cave.bo.client.Client;
import fr.eni.cave.bo.client.Panier;

public interface PanierRepository extends JpaRepository<Panier, Integer> {
	@Query("SELECT p FROM Panier p WHERE p.client = :client AND p.numCommande = null")
	List<Panier> findPaniersWithJPQL(@Param("client") Client client);
	List<Panier> findByNumCommandeNullAndClient(@Param("client") Client client);
	
	
	@Query(value = "SELECT p.* FROM CAV_SHOPPING_CART p WHERE p.CLIENT_ID = :idClient AND p.ORDER_NUMBER IS NOT NULL", nativeQuery = true)
			List<Panier> findCommandesWithSQL(@Param("idClient")String idClient);
			// Rechercher la liste des commandes d'un client
			List<Panier> findByNumCommandeNotNullAndClient(@Param("client") Client client);
}
