package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eni.cave.bo.client.Panier;

public interface LignePanierRepository extends JpaRepository<Panier, Integer> {

}
