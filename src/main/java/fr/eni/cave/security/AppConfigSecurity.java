package fr.eni.cave.security;
import javax.sql.DataSource;
import org.apache.commons.logging.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.*;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class AppConfigSecurity {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("select login, password, 1 from cav_user where login=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select login, authority from cav_user where login=?");
		return jdbcUserDetailsManager;
	}

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
	// Use Http Basic Authentication
	http.httpBasic(Customizer.withDefaults());
	// Désactivé Cross Site Request Forgery
	// Non préconisé pour les API REST en stateless.
	// Sauf pour POST, PUT, PATCH et DELETE
	http.csrf(csrf -> {
	csrf.disable();
	});
	return http.build();
	}
}
