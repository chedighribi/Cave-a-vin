package fr.eni.cave.security;

import org.apache.commons.logging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.Filter;

@Configuration
public class AppConfigSecurity {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private Filter jwtAuthenticationFilter;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(auth -> {
	auth
	// URL autorisé à tout le monde
	.requestMatchers(HttpMethod.GET,"/caveavin/bouteilles").permitAll()
	// Accès détail bouteille pour CLIENT et OWNER
	.requestMatchers(HttpMethod.GET, "/caveavin/bouteilles/*")
	.hasAnyRole("CLIENT","OWNER")
	.requestMatchers(HttpMethod.GET, "/caveavin/bouteilles/**")
	.hasRole("OWNER")
	// propriétaire peut Ajouter des bouteilles, des régions ou des couleurs
	//Modifier la quantité des bouteilles
	//Il peut consulter les régions et couleurs
	.requestMatchers(HttpMethod.POST, "/caveavin/bouteilles")
	.hasRole("OWNER")
	.requestMatchers(HttpMethod.PUT, "/caveavin/bouteilles")
	.hasRole("OWNER")
	.requestMatchers(HttpMethod.DELETE, "/caveavin/bouteilles/*")
	.hasRole("OWNER")
	.requestMatchers(HttpMethod.POST, "/caveavin/bouteilles/region")
	.hasRole("OWNER")
	.requestMatchers(HttpMethod.POST, "/caveavin/bouteilles/couleur")
	.hasRole("OWNER")
	.requestMatchers(HttpMethod.GET, "/caveavin/paniers/**")
	.hasAnyRole("CLIENT", "OWNER")
	.requestMatchers(HttpMethod.POST, "/caveavin/paniers")
	.hasRole("CLIENT")
	.requestMatchers(HttpMethod.PUT, "/caveavin/paniers")
	.hasRole("CLIENT")
	.requestMatchers(HttpMethod.POST, "/caveavin/paniers/client/commandes")
	.hasRole("CLIENT")
	// Tout autre interdit
	.anyRequest()
	.denyAll();
	});
	//Connexion de l'utilisateur
	http.authenticationProvider(authenticationProvider);
	//Activer le filtre JWT et l'authentication de l'utilisateur
	http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	// Session Stateless
	http.sessionManagement(session -> {
	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	});	http.csrf(csrf -> {
	csrf.disable();
	});
	return http.build();
	}
}
