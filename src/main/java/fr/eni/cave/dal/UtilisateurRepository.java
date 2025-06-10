package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import fr.eni.cave.bo.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String>{
	Utilisateur findByPseudo(@Param("pseudo") String pseudo);
	Utilisateur findByPseudoAndPassword(@Param("pseudo") String pseudo, @Param("password") String password);
}