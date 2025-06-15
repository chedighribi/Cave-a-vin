package fr.eni.cave.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.*;
import fr.eni.cave.dal.UtilisateurRepository;

public class JwtAppConfig {
	@Autowired
	private UtilisateurRepository uRepository;
	@Bean
	UserDetailsService userDetailsService() {
	return username -> uRepository.findById(username)
	.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	@Bean
	AuthenticationProvider authenticationProvider() {
	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	authProvider.setUserDetailsService(userDetailsService());
	return authProvider;
	}
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	return config.getAuthenticationManager();
	}

}
